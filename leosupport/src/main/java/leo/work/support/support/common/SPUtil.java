package leo.work.support.support.common;

import android.content.Context;
import android.content.SharedPreferences;

import leo.work.support.util.BaseUtil;

public class SPUtil extends BaseUtil {

    public static String PREFERENCE_NAME = "leowork_support";

    public static boolean putString(String key, String value) {
        Context context = getContext();
        if (context == null) {
            return false;
        }
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getString(String key) {
        return getString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        Context context = getContext();
        if (context == null) {
            return defaultValue;
        }
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getString(key, defaultValue);
    }

    public static boolean putInt(String key, int value) {
        Context context = getContext();
        if (context == null) {
            return false;
        }
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static int getInt(String key) {
        return getInt(key, -1);
    }

    public static int getInt(String key, int defaultValue) {
        Context context = getContext();
        if (context == null) {
            return defaultValue;
        }
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getInt(key, defaultValue);
    }

    public static boolean putLong(String key, long value) {
        Context context = getContext();
        if (context == null) {
            return false;
        }
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static long getLong(String key) {
        return getLong(key, -1);
    }

    public static long getLong(String key, long defaultValue) {
        Context context = getContext();
        if (context == null) {
            return defaultValue;
        }
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getLong(key, defaultValue);
    }

    public static boolean putFloat(String key, float value) {
        Context context = getContext();
        if (context == null) {
            return false;
        }
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public static float getFloat(String key) {
        return getFloat(key, -1);
    }

    public static float getFloat(String key, float defaultValue) {
        Context context = getContext();
        if (context == null) {
            return defaultValue;
        }
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getFloat(key, defaultValue);
    }

    public static boolean putBoolean(String key, boolean value) {
        Context context = getContext();
        if (context == null) {
            return false;
        }
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        Context context = getContext();
        if (context == null) {
            return defaultValue;
        }
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getBoolean(key, defaultValue);
    }

    public static boolean remove(String key) {
        Context context = getContext();
        if (context == null) {
            return false;
        }
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().remove(key).commit();
    }

    public static boolean clear() {
        Context context = getContext();
        if (context == null) {
            return false;
        }
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().clear().commit();
    }

    public static boolean contains(String key) {
        Context context = getContext();
        if (context == null) {
            return false;
        }
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).contains(key);
    }
}
