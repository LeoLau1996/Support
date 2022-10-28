package leo.work.support.support.file;

import static java.lang.System.in;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import leo.work.support.util.Get;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2019/5/16
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class FileSupport {


    /**
     * 检测文件是否存在
     */
    public static boolean hasFile(String strFile) {
        boolean b = true;
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                b = false;
            }

        } catch (Exception e) {
            b = false;
        }

        return b;
    }

    /**
     * 判断下载目录是否存在
     */
    public static String isExistDir(String saveDir) {
        // 下载位置
        File downloadFile = new File(saveDir);

        if (!downloadFile.exists()) {
            boolean b = downloadFile.mkdirs();
        }

        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * 保存文本
     */
    public static void saveFile(String content, String path) {
        try {
            FileOutputStream outStream = new FileOutputStream(path, true);
            OutputStreamWriter writer = new OutputStreamWriter(outStream, "utf-8");
            writer.write(content);
            writer.flush();
            writer.close();//记得关闭
            outStream.close();
        } catch (Exception e) {

        }
    }

    // 写入数据到指定路径
    public static void writeBytes(String path, boolean append, byte[] data) {
        FileOutputStream fileOutputStream = null;
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            fileOutputStream = new FileOutputStream(path, append);
            fileOutputStream.write(data);
        } catch (IOException e) {
            Log.e("TAG", "1    异常：" + e.getMessage());
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                Log.e("TAG", "2    异常：" + e.getMessage());
            }
        }
    }

    public static byte[] readFileToByteArray(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            long inSize = fileInputStream.getChannel().size();//判断FileInputStream中是否有内容
            if (inSize == 0) {
                return null;
            }

            byte[] buffer = new byte[fileInputStream.available()];//in.available() 表示要读取的文件中的数据长度
            int x = fileInputStream.read(buffer);  //将文件中的数据读到buffer中
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                return null;
            }
            //或IoUtils.closeQuietly(in);
        }
    }

    /**
     * 获取app目录
     */
    public static String getPrjFileDir() {
        return getPrjFileDir("");
    }

    public static String getPrjFileDir(String dirName) {
        if (!checkSdState()) {
            return "";
        }
        String path = getSd() + File.separator + Get.getAppName() + File.separator + dirName;
        File projectDir = new File(path);
        if (!projectDir.exists()) {
            if (!projectDir.mkdirs()) {
                return "";
            }
        }
        return path;
    }

    /**
     * 判断是否有sd卡
     */
    public static boolean checkSdState() {
        String status = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(status);
    }

    /**
     * 获取Sd卡路径
     */
    public static String getSd() {
        if (!checkSdState()) {
            return "";
        }
        return Environment.getExternalStorageDirectory().getPath();
    }


    /**
     * 获取相册路径
     */
    public static String getDCIM() {
        if (!checkSdState()) {
            return "";
        }
        String path = getSd() + "/dcim/";
        if (new File(path).exists()) {
            return path;
        }
        path = getSd() + "/DCIM/";
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return "";
            }
        }
        return path;
    }

    /**
     * 获取DCIM目录下的Camera目录
     */
    public static String getDCIMCamera() {
        if (!checkSdState()) {
            return "";
        }
        String path = getDCIM() + "/Camera/";
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return "";
            }
        }
        return path;
    }
}
