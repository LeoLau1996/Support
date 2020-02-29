package leo.work.support.MVP;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public abstract class BaseView {
    protected View mRootView;
    protected BaseMVPListener baseListener;

    public final View create(LayoutInflater inflater, ViewGroup viewGroup, BaseMVPListener listener) {
        mRootView = inflater.inflate(setLayout(), viewGroup, false);
        this.baseListener = listener;
        return mRootView;
    }

    protected abstract int setLayout();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void initListener();

    public <V extends View> V findViewById(int id) {
        return (V) mRootView.findViewById(id);
    }
}
