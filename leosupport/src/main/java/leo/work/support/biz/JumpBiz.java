package leo.work.support.biz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import leo.work.support.base.LifeControlInterface;
import leo.work.support.base.biz.CommonLifeBiz;
import leo.work.support.util.JumpFromFragmentUtil;
import leo.work.support.util.JumpUtil;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 跳转到某个页面并带回参数业务
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/1/25
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 1613-3
 * ---------------------------------------------------------------------------------------------
 * 代码备注: 更优雅跳转与返回数据
 * ---------------------------------------------------------------------------------------------
 **/
public class JumpBiz extends CommonLifeBiz {

    private Map<Integer, OnJumpBizCallBack> map;

    public JumpBiz(LifeControlInterface lifeControlInterface) {
        super(lifeControlInterface);
    }

    //Activity跳转
    public void jumpForResult(Activity activity, Class<?> mClass, Bundle bundle, int requestCode, OnJumpBizCallBack callBack) {
        addCallBack(requestCode, callBack);
        JumpUtil.go(activity, mClass, bundle, requestCode);
    }

    //Fragment跳转
    public void jumpForResult(Fragment fragment, Class<?> mClass, Bundle bundle, int requestCode, OnJumpBizCallBack callBack) {
        addCallBack(requestCode, callBack);
        JumpFromFragmentUtil.go(fragment, mClass, bundle, requestCode);
    }

    //添加回调
    private void addCallBack(int requestCode, OnJumpBizCallBack callBack) {
        if (map == null) {
            map = new HashMap<>();
        }
        map.put(requestCode, callBack);
    }

    //删除回调
    private void removeCallBack(int requestCode) {
        if (map == null) {
            return;
        }
        map.remove(requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (map == null) {
            return;
        }
        OnJumpBizCallBack callBack = map.get(requestCode);
        if (callBack == null) {
            return;
        }
        callBack.onActivityResult(requestCode, resultCode, data);
        removeCallBack(requestCode);
    }

    public interface OnJumpBizCallBack {

        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

}
