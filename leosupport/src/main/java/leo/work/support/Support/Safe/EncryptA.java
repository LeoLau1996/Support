package leo.work.support.Support.Safe;

import android.util.Base64;

import java.nio.charset.Charset;

public class EncryptA {
    //加密
    public static String encryptToBase64String(String text, String key) {
        if (text == null || text.isEmpty())
            return null;

        byte[] encrypted = encryptToBytes(text.getBytes(), key);
        if (encrypted == null || encrypted.length <= 0)
            return null;

        return Base64.encodeToString(encrypted, Base64.NO_WRAP);
    }

    public static byte[] encryptToBytes(byte[] data, String key) {
        if (key == null || key.isEmpty())
            return null;

        byte[] keyBytes;
        try {
            keyBytes = Base64.decode(key, Base64.NO_WRAP);
        } catch (Exception e) {
            return null;
        }

        byte[] encrypted = encryptToBytes(data, keyBytes);
        if (encrypted == null || encrypted.length <= 0)
            return null;

        return encrypted;
    }

    public static byte[] encryptToBytes(byte[] data, byte[] key) {
        if (data == null || data.length <= 0)
            return null;
        if (key == null || key.length <= 0)
            return null;

        //生成临时key并写到密文头部
        byte[] tempKey = createKeyToBytes(key.length);
        byte[] encrypted = new byte[data.length + tempKey.length];
        byte ks = 0;
        for (int i = 0; i < key.length; i++) {
            byte b = tempKey[i];
            byte k = key[i];
            ks = (byte) (ks + k);
            encrypted[i] = (byte) ((b + ks) ^ k);
        }

        //使用临时key加密
        int keyPoint = 0;
        ks = 0;
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            byte k = tempKey[keyPoint];
            ks = (byte) (ks + k);
            encrypted[i + key.length] = (byte) ((b + ks) ^ k);
            keyPoint = (keyPoint + 1) % key.length;
        }
        return encrypted;
    }

    //解密
    public static String decryptToString(String base64String, String key) {
        return decryptToString(base64String, key, null);
    }

    public static String decryptToString(String base64String, String key, Charset outputCharset) {
        if (base64String == null || base64String.isEmpty())
            return null;

        byte[] encrypted;
        try {
            encrypted = Base64.decode(base64String, Base64.NO_WRAP);
        } catch (Exception e) {
            return null;
        }

        byte[] decrypted = decryptToBytes(encrypted, key);
        if (decrypted == null || decrypted.length <= 0)
            return null;

        if (outputCharset == null) {
            outputCharset = Charset.forName("utf-8");
        }

        try {
            return new String(decrypted, outputCharset);
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] decryptToBytes(byte[] encrypted, String key) {
        if (key == null || key.isEmpty())
            return null;

        byte[] keyBytes;
        try {
            keyBytes = Base64.decode(key, Base64.NO_WRAP);
        } catch (IllegalArgumentException e) {
            return null;
        }

        byte[] decrypted = decryptToBytes(encrypted, keyBytes);
        if (decrypted == null || decrypted.length <= 0)
            return null;

        return decrypted;
    }

    public static byte[] decryptToBytes(byte[] encrypted, byte[] key) {
        if (encrypted == null || encrypted.length <= 0)
            return null;
        if (key == null || key.length <= 0)
            return null;
        if (encrypted.length <= key.length)
            return null;

        //取出临时key
        byte[] tempKey = new byte[key.length];
        byte ks = 0;
        for (int i = 0; i < key.length; i++) {
            byte b = encrypted[i];
            byte k = key[i];
            ks = (byte) (ks + k);
            tempKey[i] = (byte) ((b ^ k) - ks);
        }

        //使用临时key解密
        int keyPoint = 0;
        ks = 0;
        byte[] decrypted = new byte[encrypted.length - key.length];
        for (int i = key.length; i < encrypted.length; i++) {
            byte b = encrypted[i];
            byte k = tempKey[keyPoint];
            ks = (byte) (ks + k);
            decrypted[i - key.length] = (byte) ((b ^ k) - ks);
            keyPoint = (keyPoint + 1) % key.length;
        }
        return decrypted;
    }

    //创建Key
    public static String createKeyToBase64String(int len) {
        return Base64.encodeToString(createKeyToBytes(len), Base64.NO_WRAP);
    }

    public static byte[] createKeyToBytes(int len) {
        byte[] key = new byte[len];
        for (int i = 0; i < len; i++) {
            key[i] = (byte) (Math.random() * 255);
        }
        return key;
    }

    public static byte[] createKeyToBytes() {
        return createKeyToBytes(16);
    }
}
