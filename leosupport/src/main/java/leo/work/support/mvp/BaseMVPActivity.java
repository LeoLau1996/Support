package leo.work.support.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import leo.work.support.support.MVPSupport;

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
public abstract class BaseMVPActivity<T extends BaseView> extends Activity implements BaseMVPListener {
    protected T mView;
    public Context context;
    public Activity activity;
    //数据
    public boolean isLoading = false;//是否正在加载
    public boolean hasFront = false;//当前页面是否在前台

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        activity = this;
        try {
            mView = getViewClass().newInstance();
        } catch (Exception e) {
            mView = null;
            throw new RuntimeException(e.toString());
        }
        setContentView(mView.create(getLayoutInflater(), null, this));
        initData();
        mView.initData();
        mView.initView();
        mView.initListener();
        loadData(true, false);
    }

    protected abstract void initData();

    protected abstract void loadData(final boolean isShowState, final boolean isSaveLastData);

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

    public Class<T> getViewClass() {
        return MVPSupport.getViewClass(getClass());
    }

}
