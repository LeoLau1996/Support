package com.surgery.scalpel.util;

import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

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
public class JumpFromFragmentUtil {


    //不带参数  不返回
    public static void go(Fragment fragment, Class mClass) {
        go(fragment, mClass, null, null, false);
    }


    //不带参数  返  回
    public static void go(Fragment fragment, Class mClass, Integer requestCode) {
        go(fragment, mClass, null, requestCode, false);
    }

    //带参数  不返回
    public static void go(Fragment fragment, Class mClass, Bundle bundle) {
        go(fragment, mClass, bundle, null, false);
    }

    //带参数  返回
    public static void go(Fragment fragment, Class mClass, Bundle bundle, Integer requestCode) {
        go(fragment, mClass, bundle, requestCode, false);
    }


    //不带参数  不返回
    public static void go(Fragment fragment, Class mClass, boolean isAnimation) {
        go(fragment, mClass, null, null, isAnimation);
    }


    //不带参数  返  回
    public static void go(Fragment fragment, Class mClass, Integer requestCode, boolean isAnimation) {
        go(fragment, mClass, null, requestCode, isAnimation);
    }

    //带参数  不返回
    public static void go(Fragment fragment, Class mClass, Bundle bundle, boolean isAnimation) {
        go(fragment, mClass, bundle, null, isAnimation);
    }

    //带参数  返回
    public static void go(Fragment fragment, Class mClass, Bundle bundle, Integer requestCode, boolean isAnimation) {
        Intent intent = new Intent(fragment.getContext(), mClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (requestCode != null) {
            if (isAnimation) {
                fragment.startActivityForResult(intent, requestCode, ActivityOptionsCompat.makeSceneTransitionAnimation(fragment.getActivity()).toBundle());
            } else {
                fragment.startActivityForResult(intent, requestCode);
            }

        } else {
            if (isAnimation) {
                fragment.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(fragment.getActivity()).toBundle());
            } else {
                fragment.startActivity(intent);
            }
        }
    }

    /**
     * Only
     */
    //不带参数  不返回
    public static void goByOnly(Fragment fragment, Class mClass) {
        goByOnly(fragment, mClass, null, null, false);
    }


    //不带参数  返  回
    public static void goByOnly(Fragment fragment, Class mClass, Integer requestCode) {
        goByOnly(fragment, mClass, null, requestCode, false);
    }

    //带参数  不返回
    public static void goByOnly(Fragment fragment, Class mClass, Bundle bundle) {
        goByOnly(fragment, mClass, bundle, null, false);
    }

    //带参数  返回
    public static void goByOnly(Fragment fragment, Class mClass, Bundle bundle, Integer requestCode) {
        goByOnly(fragment, mClass, bundle, requestCode, false);
    }


    //不带参数  不返回
    public static void goByOnly(Fragment fragment, Class mClass, boolean isAnimation) {
        goByOnly(fragment, mClass, null, null, isAnimation);
    }


    //不带参数  返  回
    public static void goByOnly(Fragment fragment, Class mClass, Integer requestCode, boolean isAnimation) {
        goByOnly(fragment, mClass, null, requestCode, isAnimation);
    }

    //带参数  不返回
    public static void goByOnly(Fragment fragment, Class mClass, Bundle bundle, boolean isAnimation) {
        goByOnly(fragment, mClass, bundle, null, isAnimation);
    }

    //带参数  返回
    public static void goByOnly(Fragment fragment, Class mClass, Bundle bundle, Integer requestCode, boolean isAnimation) {
        Intent intent = new Intent(fragment.getContext(), mClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (requestCode != null) {
            if (isAnimation) {
                fragment.startActivityForResult(intent, requestCode, ActivityOptionsCompat.makeSceneTransitionAnimation(fragment.getActivity()).toBundle());
            } else {
                fragment.startActivityForResult(intent, requestCode);
            }

        } else {
            if (isAnimation) {
                fragment.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(fragment.getActivity()).toBundle());
            } else {
                fragment.startActivity(intent);
            }
        }
    }


}
