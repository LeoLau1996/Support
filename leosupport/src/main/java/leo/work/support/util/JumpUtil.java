package leo.work.support.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;

import androidx.core.app.ActivityOptionsCompat;

import java.util.List;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2020/4/21 21:13
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class JumpUtil {


    //不带参数  不返回
    public static void go(Activity activity, Class mClass) {
        go(activity, mClass, null, null, false);
    }


    //不带参数  返  回
    public static void go(Activity activity, Class mClass, Integer requestCode) {
        go(activity, mClass, null, requestCode, false);
    }

    //带参数  不返回
    public static void go(Activity activity, Class mClass, Bundle bundle) {
        go(activity, mClass, bundle, null, false);
    }

    //带参数  返回
    public static void go(Activity activity, Class mClass, Bundle bundle, Integer requestCode) {
        go(activity, mClass, bundle, requestCode, false);
    }


    //不带参数  不返回
    public static void go(Activity activity, Class mClass, boolean isAnimation) {
        go(activity, mClass, null, null, isAnimation);
    }


    //不带参数  返  回
    public static void go(Activity activity, Class mClass, Integer requestCode, boolean isAnimation) {
        go(activity, mClass, null, requestCode, isAnimation);
    }

    //带参数  不返回
    public static void go(Activity activity, Class mClass, Bundle bundle, boolean isAnimation) {
        go(activity, mClass, bundle, null, isAnimation);
    }

    //带参数  返回
    public static void go(Activity activity, Class mClass, Bundle bundle, Integer requestCode, boolean isAnimation) {
        Intent intent = new Intent(activity, mClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (requestCode != null) {
            if (isAnimation) {
                activity.startActivityForResult(intent, requestCode, ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
            } else {
                activity.startActivityForResult(intent, requestCode);
            }

        } else {
            if (isAnimation) {
                activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
            } else {
                activity.startActivity(intent);
            }
        }
    }

    /**
     * Only
     */
    //不带参数  不返回
    public static void goByOnly(Activity activity, Class mClass) {
        goByOnly(activity, mClass, null, null, false);
    }


    //不带参数  返  回
    public static void goByOnly(Activity activity, Class mClass, Integer requestCode) {
        goByOnly(activity, mClass, null, requestCode, false);
    }

    //带参数  不返回
    public static void goByOnly(Activity activity, Class mClass, Bundle bundle) {
        goByOnly(activity, mClass, bundle, null, false);
    }

    //带参数  返回
    public static void goByOnly(Activity activity, Class mClass, Bundle bundle, Integer requestCode) {
        goByOnly(activity, mClass, bundle, requestCode, false);
    }


    //不带参数  不返回
    public static void goByOnly(Activity activity, Class mClass, boolean isAnimation) {
        goByOnly(activity, mClass, null, null, isAnimation);
    }


    //不带参数  返  回
    public static void goByOnly(Activity activity, Class mClass, Integer requestCode, boolean isAnimation) {
        goByOnly(activity, mClass, null, requestCode, isAnimation);
    }

    //带参数  不返回
    public static void goByOnly(Activity activity, Class mClass, Bundle bundle, boolean isAnimation) {
        goByOnly(activity, mClass, bundle, null, isAnimation);
    }

    //带参数  返回
    public static void goByOnly(Activity activity, Class mClass, Bundle bundle, Integer requestCode, boolean isAnimation) {
        Intent intent = new Intent(activity, mClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (requestCode != null) {
            if (isAnimation) {
                activity.startActivityForResult(intent, requestCode, ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
            } else {
                activity.startActivityForResult(intent, requestCode);
            }

        } else {
            if (isAnimation) {
                activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
            } else {
                activity.startActivity(intent);
            }
        }
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     * @param slotId   电话号码       0:卡1  1:卡2
     */
    @SuppressLint("MissingPermission")
    public static void callPhone(Activity activity, String phoneNum, int slotId) {
        if (!PermissionsUtil.hasPermissions(Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE)) {
            callPhone(activity, phoneNum);
            return;
        }
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            callPhone(activity, phoneNum);
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse(String.format("tel:%s", phoneNum)));
            String[] dualSimTypes = {"subscription", "Subscription", "simSlotIndex", "com.android.phone.extra.slot", "phone", "com.android.phone.DialingMode", "simId", "simnum", "phone_type", "simSlot"};
            for (String dualSimType : dualSimTypes) {
                intent.putExtra(dualSimType, slotId);
            }
            TelecomManager telecomManager = (TelecomManager) activity.getSystemService(Context.TELECOM_SERVICE);
            List<PhoneAccountHandle> phoneAccountHandleList = telecomManager.getCallCapablePhoneAccounts();
            PhoneAccountHandle phoneAccountHandle = phoneAccountHandleList.get(slotId);
            intent.putExtra(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE, phoneAccountHandle);
            activity.startActivity(intent);
        } catch (Exception e) {
            callPhone(activity, phoneNum);
        }
    }

    //拨打电话（直接拨打电话）
    public static void callPhone(Activity activity, String phoneNum) {
        if (!PermissionsUtil.hasPermissions( Manifest.permission.CALL_PHONE)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        activity.startActivity(intent);
    }

    //打开网页
    public static void openURL(Activity activity, String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            activity.startActivity(intent);
        } catch (Exception e) {

        }
    }

    //进入手机设置---权限页面
    public static void startAppSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivity(intent);
    }

}
