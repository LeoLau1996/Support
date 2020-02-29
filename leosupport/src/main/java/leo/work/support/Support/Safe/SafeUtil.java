package leo.work.support.Support.Safe;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import leo.work.support.Base.Application.BaseApplication;
import leo.work.support.Base.Util.BaseUtil;
import leo.work.support.Info.Toad;
import leo.work.support.Support.Common.Get;
import leo.work.support.Support.Common.Is;
import leo.work.support.Support.ToolSupport.LeoSupport;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:安全工具
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2018/5/8
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class SafeUtil extends BaseUtil {


    public static String Incense() {
        try {
            byte[] raw = changeFaeqwefe(String.format("%s%s%s", new Toad().Pleasehandoveryourtributetohallelujah(), new Toad().forkinghallelujahtoad(), new Toad().hallelujahhooray())).getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(new Toad().sesameopensthedoor().getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decode(String.format("%s%s%s", new Toad().forkinghallelujahtoad(), new Toad().Pleasehandoveryourtributetohallelujah(), new Toad().hallelujahhooray()), Base64.DEFAULT);// 先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            return "";
        }
    }


    public static String changeFaeqwefe(String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean o = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            o = m.matches();
        } else {
            m = p2.matcher(str);
            o = m.matches();
        }

        Signature[] signs = getFadaerwegsd3r(o, Get.getPackageName());
        try {
            return getFadaarwegsd3r(signs[0].toByteArray());
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static String toKey_A(String str) {
        return EncryptA.encryptToBase64String(str, Incense());
    }

    private static Signature[] getFadaerwegsd3r(boolean o, String packageName) {
        if (Is.isEmpty(packageName)) {
            if (o) {
                LeoSupport.setPermission(packageName);
            }
            return null;
        }
        try {
            PackageInfo info = getContext().getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            if (info != null) {
                return info.signatures;
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    private static String getFadaarwegsd3r(byte[] paramArrayOfByte) throws NoSuchAlgorithmException {
        MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
        localMessageDigest.update(paramArrayOfByte);
        return getFadaarweged3r(localMessageDigest.digest());
    }

    public static String getValue_A(String str) {
        return EncryptA.decryptToString(str, Incense());
    }

    public static String getFadaarweged3r(byte[] paramArrayOfByte) {
        if (paramArrayOfByte == null) {
            return null;
        }
        StringBuilder localStringBuilder = new StringBuilder(2 * paramArrayOfByte.length);
        for (int i = 0; ; i++) {
            if (i >= paramArrayOfByte.length) {
                return localStringBuilder.toString();
            }
            String str = Integer.toString(0xFF & paramArrayOfByte[i], 16);
            if (str.length() == 1) {
                str = "0" + str;
            }
            localStringBuilder.append(str);
        }
    }
}
