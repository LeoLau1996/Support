package com.auto.apt_processor;

import com.google.auto.service.AutoService;

import org.w3c.dom.Node;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 布局中的View分组 生成 布局名 + 组名 + ViewGroup类
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/12/12
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class LayoutViewsGroupProcessor extends AbstractProcessor {

    private String packageName;
    private String[] layoutPaths;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        // 注解处理器提供的API输出
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Halo APT");
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add(LayoutViewsGroupConfig.class.getCanonicalName());
        return hashSet;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    // 处理
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //拿到所有添加Print注解的成员变量
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(LayoutViewsGroupConfig.class);
        for (Element element : elements) {
            LayoutViewsGroupConfig config = element.getAnnotation(LayoutViewsGroupConfig.class);
            packageName = config.packageName();
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "packageName = " + packageName);
            layoutPaths = config.layoutPaths();
            break;
        }
        todo();
        return true;
    }

    private void todo() {
        long currentTimeMillis = System.currentTimeMillis();
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "todo start " + currentTimeMillis + "    packageName = " + packageName);

        try {
            String layoutDirectoryPath = getLayoutPath();
            System.out.println("layoutDirectoryPath = " + layoutDirectoryPath);
            File directoryPath = new File(layoutDirectoryPath);
            for (File file : Objects.requireNonNull(directoryPath.listFiles())) {
                if (file.isDirectory() || !file.getName().contains(".xml")) {
                    continue;
                }
                // xml内容
                String xmlContent = Utils.getFileContent(file.getAbsolutePath());
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, String.format("path = %s    xmlContent = %s", file.getAbsolutePath(), xmlContent));
                // 布局名
                String layoutName = getLayoutName(file);

                Map<String, List<Node>> nodeMap = Utils.parseXml(xmlContent);
                for (Map.Entry<String, List<Node>> entry : nodeMap.entrySet()) {
                    // 群名
                    String groupName = entry.getKey();
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "groupName = " + groupName);
                    // 布局名 + 组名 + ViewGroup
                    String className = layoutName + groupName + "ViewGroup";
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, String.format("layoutName = %s    groupName = %s    className = %s", layoutName, groupName, className));
                    // 节点
                    List<Node> nodeList = entry.getValue();
                    // 写文件
                    writeJavaFile(className, getCodeStr(className, nodeList));
                }
            }

        } catch (Exception exception) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "process    Exception = " + exception.getMessage());
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "todo end " + currentTimeMillis);
    }

    private String getLayoutPath() {
        if (layoutPaths != null) {
            for (String path : layoutPaths) {
                if (new File(path).exists()) {
                    return path;
                }
            }
        }
        return null;
    }


    // 获取布局规范名称（示例：activity_main ----> ActivityMain）
    private String getLayoutName(File file) {
        String layoutName = file.getName().replace(".xml", "");
        if (layoutName.contains("_")) {
            String name = "";
            for (String str : layoutName.split("_")) {
                if (str.length() == 1) {
                    str = str.toUpperCase();
                } else if (str.length() > 1) {
                    str = str.substring(0, 1).toUpperCase() + str.substring(1);
                }
                name += str;
            }
            layoutName = name;
        }
        return layoutName;
    }

    // 获取代码文本
    public String getCodeStr(String className, List<Node> nodeList) {
        StringBuilder codeStr = new StringBuilder();
        codeStr.append(String.format("package %s;\n", packageName));
        codeStr.append("import android.view.View;\n");
        codeStr.append("import android.widget.*;\n");
        codeStr.append("import android.util.Log;\n");
        codeStr.append("import androidx.lifecycle.Lifecycle;\n");
        codeStr.append("import androidx.lifecycle.LifecycleObserver;\n");
        codeStr.append("import androidx.lifecycle.LifecycleOwner;\n");
        codeStr.append("import androidx.lifecycle.OnLifecycleEvent;\n");
        codeStr.append(String.format("import %s.R;\n", packageName));
        codeStr.append(String.format("public class %s implements LifecycleObserver {\n", className));
        {
            for (Node node : nodeList) {
                String id = Utils.getAttributeValue(node, "id");
                if (id == null) {
                    continue;
                }
                id = id.replace("@+id/", "");
                codeStr.append(String.format("public %s %s;\n", node.getNodeName(), id));
            }

            {
                codeStr.append(String.format("public %s(Object owner, View rootView) {\n", className));
                {
                    codeStr.append("if (owner instanceof LifecycleOwner)\n");
                    codeStr.append("((LifecycleOwner) owner).getLifecycle().addObserver(this);\n");

                    for (Node node : nodeList) {
                        String id = Utils.getAttributeValue(node, "id");
                        if (id == null) {
                            continue;
                        }
                        id = id.replace("@+id/", "");
                        codeStr.append(String.format("%s = rootView.findViewById(R.id.%s);\n", id, id));
                    }
                }
                codeStr.append("}\n");


                codeStr.append("@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)\n");
                codeStr.append("private void destroy() {\n");
                {
                    for (Node node : nodeList) {
                        String id = Utils.getAttributeValue(node, "id");
                        if (id == null) {
                            continue;
                        }
                        id = id.replace("@+id/", "");
                        codeStr.append(String.format("%s = null;\n", id));
                    }
                }
                codeStr.append("}\n");
            }
        }
        codeStr.append("}\n");
        return codeStr.toString();
    }

    // 写文件
    private void writeJavaFile(String className, String codeStr) {
        //processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, codeStr);
        BufferedWriter writer = null;
        try {

            JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(className);

            writer = new BufferedWriter(javaFileObject.openWriter());
            writer.write(codeStr);
        } catch (Exception exception) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "writeJavaFile    Exception = " + exception.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "writeJavaFile    IOException = " + e.getMessage());
                }
            }
        }
    }


}
