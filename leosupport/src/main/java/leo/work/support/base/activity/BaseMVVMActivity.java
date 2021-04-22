package leo.work.support.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.Window;

import leo.work.support.support.common.LogUtil;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间:  2021/4/21
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public abstract class BaseMVVMActivity<T extends ViewDataBinding> extends Activity {
    public Context context;
    public Activity activity;

    public T binding;
    //数据
    public boolean isLoading = false;//是否正在加载
    public boolean hasFront = false;//当前页面是否在前台

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //支持转场动画
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        LogUtil.e("=======================>" + this.getClass().getName());
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        context = this;
        activity = this;
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
    protected void initViews(Bundle savedInstanceState) {

    }

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

    /**
     * 设置 app 不随着系统字体的调整而变化
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        return resources;
    }
}
