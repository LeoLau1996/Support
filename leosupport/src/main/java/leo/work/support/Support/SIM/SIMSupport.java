package leo.work.support.Support.SIM;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;

import java.util.List;

import leo.work.support.Support.Permissions.PermissionsSupport;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2019-07-05
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class SIMSupport {
    private static final String[] dualSimTypes = {"subscription", "Subscription", "simSlotIndex", "com.android.phone.extra.slot", "phone", "com.android.phone.DialingMode", "simId", "simnum", "phone_type", "simSlot"};

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     * @param slotId   电话号码       0:卡1  1:卡2
     */
    public static void callPhone(Activity activity, String phoneNum, int slotId) {
        if (!PermissionsSupport.hasPermissions(activity, Manifest.permission.CALL_PHONE)) {
            PermissionsSupport.getPermissions(activity, 100, Manifest.permission.CALL_PHONE);
            return;
        }
        try {
            TelecomManager telecomManager = (TelecomManager) activity.getSystemService(Context.TELECOM_SERVICE);
            if (telecomManager == null) {
                callPhone(activity, phoneNum);
                return;
            }

            /***/
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse(String.format("tel:%s", phoneNum)));
            for (int i = 0; i < dualSimTypes.length; i++) {
                intent.putExtra(dualSimTypes[i], slotId);
            }
            List<PhoneAccountHandle> phoneAccountHandleList = telecomManager.getCallCapablePhoneAccounts();
            intent.putExtra(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE, phoneAccountHandleList.get(slotId));
            activity.startActivity(intent);
        } catch (Exception e) {
            callPhone(activity, phoneNum);
        }
    }


    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    @SuppressLint("MissingPermission")
    public static void callPhone(Activity activity, String phoneNum) {
        if (!PermissionsSupport.hasPermissions(activity, Manifest.permission.CALL_PHONE)) {
            PermissionsSupport.getPermissions(activity, 100, Manifest.permission.CALL_PHONE);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        activity.startActivity(intent);
    }

}
