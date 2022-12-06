package com.surgery.scalpel.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.surgery.scalpel.model.MethodResult;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 常用的工具类
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2021/12/31
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 1613-3
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class CommonUtil {

    public static final String TAG = CommonUtil.class.getSimpleName();

    //复制
    public static void copy(Activity activity, String content) {
        copy(activity, "Label", content);
    }

    //复制
    public static void copy(Activity activity, String label, String content) {
        //获取剪贴板管理器：
        ClipboardManager clipboardManager = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager == null) {
            return;
        }
        // 将ClipData内容放到系统剪贴板里。
        clipboardManager.setPrimaryClip(ClipData.newPlainText(label, content));
    }

    //显示虚拟键盘
    public static void showKeyboard(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    //隐藏虚拟键盘(只适用于Activity，不适用于Fragment)
    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) {
            return;
        }
        hideKeyboard(activity, view);
    }

    //隐藏虚拟键盘
    public static void hideKeyboard(Activity activity, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //隐藏虚拟键盘(只适用于Dialog)
    public static void hideKeyboard(Dialog dialog) {
        View view = dialog.getCurrentFocus();
        if (view == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) dialog.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //隐藏软键盘(可用于Activity，Fragment)
    public static void hideSoftKeyboard(Context context, List<View> viewList) {
        if (viewList == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        for (View view : viewList) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 获取字段
     *
     * @param mClass    目标类
     * @param targetObj 目标对象
     * @param fieldName 字段名
     * @return
     */
    public static Object getField(Class mClass, Object targetObj, String fieldName) {
        Object value = null;
        try {
            // 获取声明的字段
            Field field = mClass.getDeclaredField(fieldName);
            // 设置为可访问
            field.setAccessible(true);
            // 获取值
            value = field.get(targetObj);
        } catch (IllegalAccessException exception) {
            Log.e(TAG, String.format("getField    IllegalAccessException = %s", exception.getMessage()));
        } catch (NoSuchFieldException exception) {
            Log.e(TAG, String.format("getField    NoSuchFieldException = %s", exception.getMessage()));
        }
        return value;
    }


    /**
     * 设置字段
     *
     * @param mClass    目标类
     * @param targetObj 目标对象
     * @param fieldName 字段名
     * @param newValue  值
     * @return
     */
    public static boolean setField(Class mClass, Object targetObj, String fieldName, Object newValue) {
        try {
            // 获取声明的字段
            Field field = mClass.getDeclaredField(fieldName);
            // 设置为可访问
            field.setAccessible(true);
            // 设置值
            field.set(targetObj, newValue);
            return true;
        } catch (IllegalAccessException exception) {
            Log.e(TAG, String.format("getField    IllegalAccessException = %s", exception.getMessage()));
        } catch (NoSuchFieldException exception) {
            Log.e(TAG, String.format("getField    NoSuchFieldException = %s", exception.getMessage()));
        }
        return false;
    }

    // 调用方法
    public static MethodResult invokeMethod(Class mClass, Object targetObj, String methodName, Object... args) {
        try {
            Class<?>[] parameterTypes = null;
            if (args != null && args.length > 0) {
                parameterTypes = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    parameterTypes[i] = args[i].getClass();
                }
            }
            // 获取方法
            Method method = mClass.getDeclaredMethod(methodName, parameterTypes);
            // 设置为可访问
            method.setAccessible(true);
            // 调用
            Object returnResult = method.invoke(targetObj, args);
            return new MethodResult(true, returnResult);
        } catch (NoSuchMethodException exception) {
            Log.e(TAG, String.format("invokeMethod    NoSuchMethodException = %s", exception.getMessage()));
        } catch (IllegalAccessException exception) {
            Log.e(TAG, String.format("invokeMethod    IllegalAccessException = %s", exception.getMessage()));
        } catch (InvocationTargetException exception) {
            Log.e(TAG, String.format("invokeMethod    InvocationTargetException = %s", exception.getMessage()));
        }
        return new MethodResult(false, null);
    }

}
