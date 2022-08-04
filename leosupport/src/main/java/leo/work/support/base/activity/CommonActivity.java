package leo.work.support.base.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;

import leo.work.support.util.LogUtil;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 简单的Activity
 * ---------------------------------------------------------------------------------------------
 * 时　　间:  2021/4/21
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public abstract class CommonActivity<T extends ViewDataBinding> extends CommonAbstractActivity {

    //Activity
    public CommonActivity activity;
    //ViewDataBinding
    public T binding;
    //当前页面是否在前台
    public boolean hasFront = false;
    //
    private Configuration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //打印Log
        LogUtil.e("=======================>" + this.getClass().getName());
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //
        activity = this;
        //
        binding = DataBindingUtil.setContentView(activity, setLayout());
        //初始化数据
        initData(savedInstanceState);
        //加载View
        initViews(savedInstanceState);
        //加载数据
        loadData();
        //初始化监听器
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
    protected void onResume() {
        super.onResume();
        hasFront = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        hasFront = false;
    }

}
