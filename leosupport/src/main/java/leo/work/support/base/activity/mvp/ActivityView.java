package leo.work.support.base.activity.mvp;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * View
 *
 * @param <A>
 */
public abstract class ActivityView<A extends CommonMVPActivity> {

    public A activity;
    public int layoutId;

    public ActivityView(A activity, int layoutId) {
        this.activity = activity;
        this.layoutId = layoutId;
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
