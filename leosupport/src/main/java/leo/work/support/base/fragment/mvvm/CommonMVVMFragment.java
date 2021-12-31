package leo.work.support.base.fragment.mvvm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import leo.work.support.base.fragment.CommonFragment;
import leo.work.support.util.LogUtil;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间:  2021/4/29
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public abstract class CommonMVVMFragment<T extends ViewDataBinding> extends CommonFragment {

    public View rootView;
    public Context context;
    public Activity activity;

    private boolean hasResume = false;//是否前台（不保证正在显示该页面）
    public boolean hasFront = false;//当前页面是否在前台
    public boolean hidden = false;//Fragment显示/隐藏状态

    public T binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e("=======================>" + this.getClass().getName());
        rootView = inflater.inflate(setLayout(), container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        activity = getActivity();
        binding = DataBindingUtil.setContentView(activity, setLayout());
        initData(savedInstanceState);
        initViews(savedInstanceState);
        loadData();
        initListener();
    }

    protected abstract int setLayout();

    /**
     * 初始化数据
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 加载View
     *
     * @param savedInstanceState
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 加载数据，如：网络请求
     */
    protected void loadData() {

    }

    /**
     * 初始化监听器
     */
    protected void initListener() {

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
        if (hasResume) {
            this.hasFront = !hidden;
        } else {
            this.hasFront = false;
        }
    }

}
