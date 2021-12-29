package leo.work.support.biz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import leo.work.support.base.activity.CommonActivity;
import leo.work.support.base.biz.CommonLifeBiz;
import leo.work.support.support.common.Is;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2021/12/29
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 1613-3
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class SelectFragmentBiz extends CommonLifeBiz {

    public FragmentManager fragmentManager;
    public Fragment fragment = null;

    public SelectFragmentBiz(CommonActivity commonActivity) {
        super(commonActivity);
        fragmentManager = commonActivity.getSupportFragmentManager();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState == null || fragment == null) {
            return;
        }
        //隐藏当前的fragment,避免重叠
        fragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
        fragment = null;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState == null || fragment == null) {
            return;
        }
        //隐藏当前的fragment,避免重叠
        fragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
        fragment = null;
    }

    /**
     * fragment切换逻辑
     *
     * @param fragment
     * @param index
     */
    public void selectFragment(int id, Fragment fragment, int index) {
        //如果相同
        if (this.fragment == fragment) {
            return;
        }
        //隐藏当前的fragment
        if (this.fragment != null) {
            fragmentManager.beginTransaction().hide(this.fragment).commitAllowingStateLoss();
        }
        //没有被添加  没有显示   没有删除 ---->   添加新的Fragment
        if (!fragment.isAdded() && !fragment.isVisible() && !fragment.isRemoving()) {
            //添加fragment到Activity
            fragmentManager.beginTransaction().add(id, fragment, String.valueOf(index)).commitAllowingStateLoss();
        }
        //显示fragment
        else {
            fragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
        }
        this.fragment = fragment;
    }
}
