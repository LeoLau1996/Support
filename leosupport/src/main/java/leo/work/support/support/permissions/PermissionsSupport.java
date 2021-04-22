package leo.work.support.support.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;


/**
 * 权限支持
 * 1.检查权限
 * 2.动态申请权限
 * Created by Best100_Android on 17/11/14.
 */

public class PermissionsSupport {

    //拥有权限
    public static final int PERMISSION_RESULT_SUCCESS = PackageManager.PERMISSION_GRANTED;
    //缺乏权限
    public static final int PERMISSION_RESULT_FAIL = PackageManager.PERMISSION_DENIED;


    //读取外部存储
    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    //写外部存储
    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    //录音
    public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    //拍照
    public static final String CAMERA = Manifest.permission.CAMERA;
    //打电话
    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
    //粗略定位
    public static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    //精准定位
    public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    //判断权限集合
    public static boolean hasPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (!hasPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    //判断是否拥有某一权限
    private static boolean hasPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PERMISSION_RESULT_SUCCESS;
    }

    //请求权限（兼容低版本）
    public static void getPermissions(Activity activity, int requestCode, String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    //请求权限（兼容低版本）
    public static void getPermissions(Fragment fragment, int requestCode, String... permissions) {
        fragment.requestPermissions(permissions, requestCode);
    }

    //进入手机设置---权限页面
    public static void startAppSettings(Activity activity, int PermissionsReturnCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivity(intent);
    }

}
