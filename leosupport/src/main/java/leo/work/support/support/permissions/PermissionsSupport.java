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
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
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
    public static void startAppSettings(Activity activity, int permissionsReturnCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivity(intent);
    }

}
