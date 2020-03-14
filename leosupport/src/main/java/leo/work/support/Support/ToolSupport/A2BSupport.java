package leo.work.support.Support.ToolSupport;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import leo.work.support.Base.Util.BaseUtil;
import sun.misc.BASE64Encoder;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2018/5/2.
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class A2BSupport extends BaseUtil {


    /**
     * 字符串与数字之间转换
     */
    public static int String2int(String A) {
        int result;
        try {
            result = Integer.valueOf(A);
        } catch (Exception e) {
            result = 0;
        }
        return result;
    }

    public static String int2String(int A) {
        String B;
        try {
            B = String.valueOf(A);
        } catch (Exception e) {
            B = "";
        }
        return B;
    }

    public static String long2String(long A) {
        String B;
        try {
            B = String.valueOf(A);
        } catch (Exception e) {
            B = "";
        }
        return B;
    }

    public static long String2Long(String A) {
        long result;
        try {
            result = Long.valueOf(A);
        } catch (Exception e) {
            result = 0;
        }
        return result;
    }


    public static float String2float(String A) {
        float result;
        try {
            result = Float.valueOf(A);
        } catch (Exception e) {
            result = 0f;
        }
        return result;
    }

    public static String float2String(float A) {
        String B;
        try {
            B = String.valueOf(A);
        } catch (Exception e) {
            B = "";
        }
        return B;
    }


    /**
     * 尺寸转换
     */
    //根据手机的分辨率从 px(像素) 的单位 转成为 dp
    public static int px2dp(float pxValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //根据手机的分辨率从 dp 的单位 转成为 px(像素)
    public static int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 图像转换
     */
    public static Bitmap Drawable2Bitmap(Activity activity, int drawable) {
        return BitmapFactory.decodeResource(activity.getResources(), drawable);
    }

    //Bitmap转byte
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    //byte转Bitmap
    public static Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    //Bitmap转Base64
    public static String Bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bit.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * 文件转换
     */
    //File转byte[]
    public static byte[] File2Bytes(String filePath) throws IOException {

        InputStream in = new FileInputStream(filePath);
        byte[] data = toByteArray(in);
        in.close();

        return data;
    }

    private static byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }


    /**
     * 字节数组转字符串
     */
    public static String bytes2HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex;
        }

        return ret;
    }

    /**
     * 16进制转10进制
     */
    public static int hex2Ten(String hex) {
        if (TextUtils.isEmpty(hex)) {
            return 0;
        }
        return Integer.valueOf(hex, 16);
    }

    /**
     * 10进制转16进制
     */
    public static String ten2Hex(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return Integer.toHexString(Integer.parseInt(str));
    }

    /**
     * 16进制字符串转字节数组
     */
    public static byte[] hex2Bytes(String hex) {
        if (hex.length() % 2 != 0) {
            hex = "0" + hex;
        }
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        try {
            for (int i = 0; i < hex.length(); i += 2) {
                char c1 = hex.charAt(i);
                if ((i + 1) >= hex.length()) {
                    throw new IllegalArgumentException("hexUtil.odd");
                }
                char c2 = hex.charAt(i + 1);
                byte b = 0;
                if ((c1 >= '0') && (c1 <= '9'))
                    b += ((c1 - '0') * 16);
                else if ((c1 >= 'a') && (c1 <= 'f'))
                    b += ((c1 - 'a' + 10) * 16);
                else if ((c1 >= 'A') && (c1 <= 'F'))
                    b += ((c1 - 'A' + 10) * 16);
                else
                    throw new IllegalArgumentException("hexUtil.bad");

                if ((c2 >= '0') && (c2 <= '9'))
                    b += (c2 - '0');
                else if ((c2 >= 'a') && (c2 <= 'f'))
                    b += (c2 - 'a' + 10);
                else if ((c2 >= 'A') && (c2 <= 'F'))
                    b += (c2 - 'A' + 10);
                else
                    throw new IllegalArgumentException("hexUtil.bad");
                bao.write(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return (bao.toByteArray());
    }

    /**
     * 字节转位
     * 请注意这是倒序
     *
     * @param by
     * @return
     */
    public static int[] getBit(byte by) {
        int[] bit = {
            (by >> 7) & 0x1,
            (by >> 6) & 0x1,
            (by >> 5) & 0x1,
            (by >> 4) & 0x1,
            (by >> 3) & 0x1,
            (by >> 2) & 0x1,
            (by >> 1) & 0x1,
            (by >> 0) & 0x1};
        return bit;
    }

    /**
     * 二进制转10进制
     *
     * @param bit
     * @return
     */
    public static int bitToByte(int[] bit) {
        String str = "";
        for (int i = 0; i < bit.length; i++) {
            str += "" + bit[i];
        }
        return Integer.parseInt(str, 2);
    }

    //字节转K、M、G
    public static String B2MB_GB(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    // drawable 转换成bitmap
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();// 取drawable的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;// 取drawable的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);// 建立对应bitmap
        Canvas canvas = new Canvas(bitmap);// 建立对应bitmap的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);// 把drawable内容画到画布中
        return bitmap;
    }

    //字节转Base64
    public static String Byte2Base64String(byte[] b) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encodeBuffer(b);//"data:image;base64,"
    }


    /**
     * 保留两位小数
     *
     * @param price
     * @return
     */
    public static String SaveTwoNumber(float price) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(price);//format 返回的是字符串
        return p;
    }

    //将中文转网页编码
    public static String toUrlEncoder(String content) {
        String result = "";
        try {
            result = URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}

