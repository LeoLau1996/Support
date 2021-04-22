package leo.work.support.support.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 压缩
 * ---------------------------------------------------------------------------------------------
 * 时　　间:  2020/12/29
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class GZipSupport {

    private final static String TAG = "GZipSupport";
    public static final String GZIP_ENCODE_UTF_8 = "UTF-8";

    public static final String GZIP_ENCODE_ISO_8859_1 = "ISO-8859-1";

    /**
     * 字符串压缩为GZIP字节数组
     *
     * @param str
     * @param encoding
     * @return
     */
    public static byte[] compress(String str, String encoding) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes(encoding));
            gzip.close();
        } catch (Exception e) {
            LogUtil.e(TAG, "gzip compress error = " + e.getMessage());
        }
        return out.toByteArray();
    }

    /**
     * Gzip  byte[] 解压成字符串
     *
     * @param bytes
     * @return
     */
    public static String uncompressToString(byte[] bytes) {
        return uncompressToString(bytes, GZIP_ENCODE_UTF_8);
    }


    /**
     * Gzip  byte[] 解压成字符串
     *
     * @param bytes
     * @param encoding
     * @return
     */
    public static String uncompressToString(byte[] bytes, String encoding) {
        String content = null;
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            content = out.toString(encoding);
        } catch (Exception e) {
            LogUtil.e(TAG, "gzip compress error = " + e.getMessage());
        } finally {
            try {
                in.close();
                out.close();
            } catch (Exception e) {
                LogUtil.e(TAG, "gzip compress error = " + e.getMessage());
            }
        }
        return content;
    }

    /**
     * 判断byte[]是否是Gzip格式
     *
     * @param data
     * @return
     */
    public static boolean isGzip(byte[] data) {
        int header = (int) ((data[0] << 8) | data[1] & 0xFF);
        return header == 0x1f8b;
    }
}
