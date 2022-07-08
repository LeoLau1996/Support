package leo.work.support.biz;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import leo.work.support.base.LifeControlInterface;
import leo.work.support.base.biz.CommonLifeBiz;
import leo.work.support.util.PermissionsUtil;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 权限获取业务
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/1/11
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class PermissionBiz extends CommonLifeBiz {

    private Map<Integer, OnPermissionBizCallBack> callBackMap;


    public PermissionBiz(LifeControlInterface lifeControlInterface) {
        super(lifeControlInterface);
    }

    //是否存在某些权限
    public boolean hasPermissions(String[] permissions) {
        return PermissionsUtil.hasPermissions(permissions);
    }

    //检查权限
    public void checkPermission(String[] permissions, int requestCode, OnPermissionBizCallBack callBack) {
        //只接受Activity、Fragment
        if (!isActivity() && !isFragment()) {
            return;
        }
        //如果没有权限
        if (!hasPermissions(permissions)) {
            addCallBack(requestCode, callBack);
            //获取权限
            if (isActivity()) {
                PermissionsUtil.getPermissions(getActivity(), requestCode, permissions);
            } else if (isFragment()) {
                PermissionsUtil.getPermissions(getFragment(), requestCode, permissions);
            }
            return;
        }
        callBack.onPermissionSuccess(requestCode, permissions);
    }

    //
    private void addCallBack(int requestCode, OnPermissionBizCallBack callBack) {
        if (callBackMap == null) {
            callBackMap = new HashMap<>();
        }
        callBackMap.put(requestCode, callBack);
    }

    //权限返回
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (callBackMap == null) {
            return;
        }
        OnPermissionBizCallBack callBack = callBackMap.get(requestCode);
        if (callBack == null) {
            return;
        }

        List<String> successPermissionList = new ArrayList<>();
        List<String> failPermissionList = new ArrayList<>();


        boolean success = true;
        for (int i = 0, size = grantResults.length; i < size; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                successPermissionList.add(permissions[i]);
            } else {
                failPermissionList.add(permissions[i]);
                success = false;
            }
        }

        if (success) {
            callBack.onPermissionSuccess(requestCode, successPermissionList.toArray(new String[0]));
        } else {
            callBack.onPermissionFail(requestCode, successPermissionList.toArray(new String[0]), failPermissionList.toArray(new String[0]));
        }
        callBackMap.remove(requestCode);
    }

    public interface OnPermissionBizCallBack {

        void onPermissionSuccess(int requestCode, String[] permissions);

        void onPermissionFail(int requestCode, String[] successPermissions, String[] failPermissions);
    }


}
