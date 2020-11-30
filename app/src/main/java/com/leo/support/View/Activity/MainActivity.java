package com.leo.support.View.Activity;

import android.support.annotation.NonNull;
import android.view.View;

import com.leo.support.View.Fragment.AFragment;
import com.leo.support.View.Fragment.BFragment;
import com.leo.support.View.Fragment.CFragment;
import com.leo.support.View.Fragment.DFragment;
import com.leo.support.View.Listener.MainListener;
import com.leo.support.View.MyActivity;
import com.leo.support.View.Presenter.MainPresenter;
import com.leo.support.R;
import com.leo.support.View.View.MainView;

import leo.work.support.Base.Util.JumpUtil;
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

        selectFragmentAnimation(R.id.mFrameLayout, a, 1);
    }

    @Override
    protected void loadData(boolean isShowState, boolean isSaveLastData) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv1) {
            presenter.select1();
            JumpUtil.go(activity, MyActivity.class);
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
        selectFragmentAnimation(R.id.mFrameLayout, a, 1);
        Talk.showToast("a");
    }

    @Override
    public void onSelectB() {
        selectFragmentAnimation(R.id.mFrameLayout, b, 2);
        Talk.showToast("b");
    }

    @Override
    public void onSelectC() {
        selectFragmentAnimation(R.id.mFrameLayout, c, 3);
        Talk.showToast("c");
    }

    @Override
    public void onSelectD() {
        selectFragmentAnimation(R.id.mFrameLayout, d, 4);
        Talk.showToast("d");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
