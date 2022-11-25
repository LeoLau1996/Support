package com.surgery.scalpel.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class SPUtil extends BaseUtil {

    private static List<SPUtil> instanceList;

    public static SPUtil getInstance(String name) {
        if (Is.isEquals(name)) {
            throw new NullPointerException();
        }
        if (instanceList == null) {
            instanceList = new ArrayList<>();
        }
        SPUtil instance = null;
        for (SPUtil spUtil : instanceList) {
            if (Is.isEquals(spUtil.getName(), name)) {
                instance = spUtil;
            }
        }
        if (instance == null) {
            instance = new SPUtil(name);
            instanceList.add(instance);
        }
        return instance;
    }

    private String name;
    private SharedPreferences sharedPreferences;

    private void sharedPreferencesCheckNull(Context context) {
        if (sharedPreferences != null) {
            return;
        }
        sharedPreferences = context.getSharedPreferences(getName(), Context.MODE_PRIVATE);
    }

    public SPUtil(String name) {
        this.name = name;
    }

    public boolean putString(String key, String value) {
        Context context = getContext();
        if (context == null) {
            return false;
        }
        sharedPreferencesCheckNull(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String defaultValue) {
        Context context = getContext();
        if (context == null) {
            return defaultValue;
        }
        return context.getSharedPreferences(getName(), Context.MODE_PRIVATE).getString(key, defaultValue);
    }

    public boolean putInt(String key, int value) {
        Context context = getContext();
        if (context == null) {
            return false;
        }
        sharedPreferencesCheckNull(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public int getInt(String key) {
        return getInt(key, -1);
    }

    public int getInt(String key, int defaultValue) {
        Context context = getContext();
        if (context == null) {
            return defaultValue;
        }
        return context.getSharedPreferences(getName(), Context.MODE_PRIVATE).getInt(key, defaultValue);
    }

    public boolean putLong(String key, long value) {
        Context context = getContext();
        if (context == null) {
            return false;
        }
        sharedPreferencesCheckNull(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public long getLong(String key) {
        return getLong(key, -1);
    }

    public long getLong(String key, long defaultValue) {
        Context context = getContext();
        if (context == null) {
            return defaultValue;
        }
        return context.getSharedPreferences(getName(), Context.MODE_PRIVATE).getLong(key, defaultValue);
    }

    public boolean putFloat(String key, float value) {
        Context context = getContext();
        if (context == null) {
            return false;
        }
        sharedPreferencesCheckNull(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public float getFloat(String key) {
        return getFloat(key, -1);
    }

    public float getFloat(String key, float defaultValue) {
        Context context = getContext();
        if (context == null) {
            return defaultValue;
        }
        return context.getSharedPreferences(getName(), Context.MODE_PRIVATE).getFloat(key, defaultValue);
    }

    public boolean putBoolean(String key, boolean value) {
        Context context = getContext();
        if (context == null) {
            return false;
        }
        sharedPreferencesCheckNull(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        Context context = getContext();
        if (context == null) {
            return defaultValue;
        }
        return context.getSharedPreferences(getName(), Context.MODE_PRIVATE).getBoolean(key, defaultValue);
    }

    public boolean remove(String key) {
        Context context = getContext();
        if (context == null) {
            return false;
        }
        return context.getSharedPreferences(getName(), Context.MODE_PRIVATE).edit().remove(key).commit();
    }

    public boolean clear() {
        Context context = getContext();
        if (context == null) {
            return false;
        }
        return context.getSharedPreferences(getName(), Context.MODE_PRIVATE).edit().clear().commit();
    }

    public boolean contains(String key) {
        Context context = getContext();
        if (context == null) {
            return false;
        }
        return context.getSharedPreferences(getName(), Context.MODE_PRIVATE).contains(key);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
