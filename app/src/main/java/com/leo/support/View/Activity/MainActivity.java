package com.leo.support.View.Activity;

import android.support.annotation.NonNull;
import android.view.View;

import com.leo.support.View.Fragment.AFragment;
import com.leo.support.View.Fragment.BFragment;
import com.leo.support.View.Fragment.CFragment;
import com.leo.support.View.Fragment.DFragment;
import com.leo.support.View.Listener.MainListener;
import com.leo.support.View.Presenter.MainPresenter;
import com.leo.support.R;
import com.leo.support.View.View.MainView;

import leo.work.support.MVP.BaseMVPFragmentActivity;
import leo.work.support.Support.Common.LogUtil;
import leo.work.support.Support.Common.Talk;
import leo.work.support.Support.File.FileSupport;
import leo.work.support.Support.Permissions.PermissionsSupport;
import leo.work.support.Support.Thread.LeoRunnable;
import leo.work.support.Support.Thread.TaskSupport;
import leo.work.support.Support.Thread.ThreadSupport;

public class MainActivity extends BaseMVPFragmentActivity<MainView> implements MainListener, View.OnClickListener {
    private MainPresenter presenter;

    private AFragment a;
    private BFragment b;
    private CFragment c;
    private DFragment d;

    @Override
    protected void initData() {
        presenter = new MainPresenter(this);
        a = new AFragment();
        b = new BFragment();
        c = new CFragment();
        d = new DFragment();

        selectFragment(R.id.mFrameLayout, a, "a");
    }

    @Override
    protected void loadData(boolean isShowState, boolean isSaveLastData) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv1) {
            if (PermissionsSupport.hasPermissions(context, PermissionsSupport.READ_EXTERNAL_STORAGE, PermissionsSupport.WRITE_EXTERNAL_STORAGE)) {
                LogUtil.e("liu1209", FileSupport.getPrjFileDir());
            } else {
                LogUtil.e("liu1209", "缺少权限，请申请");
                PermissionsSupport.getPermissions(activity, 1001, PermissionsSupport.READ_EXTERNAL_STORAGE, PermissionsSupport.WRITE_EXTERNAL_STORAGE);
            }
        } else if (v.getId() == R.id.tv2) {
            presenter.select2();
        } else if (v.getId() == R.id.tv3) {
            presenter.select3();
        } else if (v.getId() == R.id.tv4) {
            presenter.select4();
        }
    }


    @Override
    public void onSelectA() {
        selectFragment(R.id.mFrameLayout, a, "a");
        Talk.showToast("a");
    }

    @Override
    public void onSelectB() {
        selectFragment(R.id.mFrameLayout, b, "b");
        Talk.showToast("b");
    }

    @Override
    public void onSelectC() {
        selectFragment(R.id.mFrameLayout, c, "c");
        Talk.showToast("c");
    }

    @Override
    public void onSelectD() {
        selectFragment(R.id.mFrameLayout, d, "d");
        Talk.showToast("d");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
