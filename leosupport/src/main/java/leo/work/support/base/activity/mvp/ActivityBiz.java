package leo.work.support.base.activity.mvp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 数据与逻辑
 */
public abstract class ActivityBiz<A extends CommonMVPActivity> {

    public A activity;

    public ActivityBiz(A activity) {
        this.activity = activity;
    }

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void loadData(boolean isShowState, boolean isSaveLastData);

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
