package com.auto.apt_processor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/12/12
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class Utils {

    // 获取文件路径
    public synchronized static String getFileContent(String path) {
        StringBuilder stringBuffer = new StringBuilder();
        try {

            //path = "/Users/leolau/Documents/LeoWork/AptDemo/app/src/main/res/layout";
            //System.out.println("getFileContent    path = " + path);
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String mimeTypeLine;
            while ((mimeTypeLine = bufferedReader.readLine()) != null) {
                stringBuffer.append(mimeTypeLine);
                stringBuffer.append("\n");
            }
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
        } catch (Exception exception) {

        }
        return stringBuffer.toString();
    }

    // 解析XML
    public synchronized static Map<String, List<Node>> parseXml(String xmlContent) {

        List<Node> nodeList = new ArrayList<>();
        try {
            // xml解析
            InputStream inputStream = new ByteArrayInputStream(xmlContent.getBytes("UTF-8"));
            //
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
            // 根元素
            Element rootElement = document.getDocumentElement();
            String groupName = getAttributeValue(rootElement, "groupName");
            if (groupName != null && !groupName.isEmpty()) {
                nodeList.add(rootElement);
            }
            parseXmlNode(nodeList, rootElement.getChildNodes());
            inputStream.close();
        } catch (Exception exception) {
            System.out.printf("parseXml    exception = %s%n", exception.getMessage());
        }
        Map<String, List<Node>> map = new HashMap<>();
        for (Node node : nodeList) {
            String groupNameValue = getAttributeValue(node, "groupName");
            if (groupNameValue == null) {
                continue;
            }
            String[] groupNames = groupNameValue.split("\\|");
            for (String groupName : groupNames) {
                List<Node> list = map.get(groupName);
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(node);
                //System.out.println("groupName = " + groupName);
                map.put(groupName, list);
            }
        }
        return map;
    }

    // 解析XML节点
    public static void parseXmlNode(List<Node> nodeList, NodeList children) {
        try {
            for (int i = 0; children != null && i < children.getLength(); i++) {
                Node node = children.item(i);
                if (node.getNodeName().equals("#text")) {
                    continue;
                }
                String groupName = getAttributeValue(node, "groupName");
                if (groupName != null && !groupName.isEmpty()) {
                    nodeList.add(node);
                }
                parseXmlNode(nodeList, node.getChildNodes());
            }
        } catch (Exception exception) {
            System.out.printf("parseXmlNode    exception = %s%n", exception.getMessage());
        }

    }

    // 获取群名 属性
    public static String getAttributeValue(Node node, String attributeName) {
        if (!node.hasAttributes()) {
            return null;
        }
        NamedNodeMap map = node.getAttributes();
        for (int i = 0; i < map.getLength(); i++) {
            Node attributesNode = map.item(i);
            String name = attributesNode.getNodeName();
            if (!name.contains(":")) {
                continue;
            }
            name = name.substring(name.indexOf(":") + 1);
            if (name.equals(attributeName)) {
                return attributesNode.getNodeValue();
            }
        }
        return null;
    }
}
