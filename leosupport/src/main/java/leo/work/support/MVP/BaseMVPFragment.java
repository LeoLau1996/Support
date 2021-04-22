package leo.work.support.MVP;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import leo.work.support.Support.MVPSupport;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2019-08-05
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public abstract class BaseMVPFragment<T extends BaseView> extends Fragment implements BaseMVPListener {
    public View view;
    protected T mView;
    public Context context;
    public Activity activity;
    //数据
    public boolean isLoading = false;//是否正在加载
    private boolean hasResume = false;//是否前台（不保证正在显示该页面）
    public boolean hasFront = false;//当前页面是否在前台
    public boolean hidden = false;//Fragment显示/隐藏状态

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        activity = getActivity();
        try {
            mView = getViewClass().newInstance();
        } catch (Exception e) {
            mView = null;
            throw new RuntimeException(e.toString());
        }
        view = mView.create(getLayoutInflater(), container, this);
        initData();
        mView.initData();
        mView.initView();
        mView.initListener();
        loadData(true, false);
        return view;
    }

    protected abstract void initData();

    protected abstract void loadData(final boolean isShowState, final boolean isSaveLastData);

    public Class<T> getViewClass() {
        return MVPSupport.getViewClass(getClass());
    }

    /**
     * 使用时应该写在这上面
     * ....
     * super.onResume();
     * 不应该写在super下面
     */
    @Override
    public void onResume() {
        super.onResume();
        hasResume = true;
        hasFront = !hidden;
    }

    @Override
    public void onPause() {
        super.onPause();
        hasResume = false;
        hasFront = false;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (hasResume)
            this.hasFront = !hidden;
        else
            this.hasFront = false;
    }

}
