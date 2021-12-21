package leo.work.support.base.activity.mvp;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * View
 *
 * @param <A>
 */
public abstract class ActivityView<A extends CommonMVPActivity, T extends ViewDataBinding> {

    public A activity;
    //ViewDataBinding
    public T binding;

    public ActivityView(A mActivity, int layoutId) {
        activity = mActivity;
        //设置布局
        activity.setContentView(layoutId);
        //ViewDataBinding
        binding = DataBindingUtil.setContentView(activity, layoutId);
    }


    //初始化View
    protected abstract void initView();

    //初始化监听事件
    protected abstract void initListener();


    public void onResume() {

    }

    public void onStart() {

    }

    public void onPause() {

    }

    public void onStop() {

    }

    public void onDestroy() {

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

    }


    protected void onNewIntent(Intent intent) {

    }

    public void onBackPressed() {

    }
}
