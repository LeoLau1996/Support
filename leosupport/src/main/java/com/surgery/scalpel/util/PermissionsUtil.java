package com.surgery.scalpel.util;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;


/**
 * 权限支持
 * 1.检查权限
 * 2.动态申请权限
 * Created by Best100_Android on 17/11/14.
 */

public class PermissionsUtil extends BaseUtil {

    //判断权限集合
    public static boolean hasPermissions(String... permissions) {
        for (String permission : permissions) {
            if (!hasPermission(permission)) {
                return false;
            }
        }
        return true;
    }

    //判断是否拥有某一权限
    private static boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    //请求权限（兼容低版本）
    public static void getPermissions(Activity activity, int requestCode, String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    //请求权限（兼容低版本）
    public static void getPermissions(Fragment fragment, int requestCode, String... permissions) {
        fragment.requestPermissions(permissions, requestCode);
    }

}
