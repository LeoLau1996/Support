# 导入
```text

android {

    // MVVM
    dataBinding {
        enabled = true
    }
}

dependencies {
    implementation files('libs/leosupport-release.aar')
}

```
# 代码模板
## CommonActivity

```text
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

import android.app.Activity;
import android.os.Bundle;

import leo.work.support.base.activity.CommonActivity;
import leo.work.support.util.JumpUtil;



#parse("File Header.java")
public class ${NAME} extends CommonActivity<#if (${BINDING_NAME} && ${BINDING_NAME} != "")${BINDING_NAME}#end#if (${BINDING_NAME} && ${BINDING_NAME} == "")Activity(xxxBinding)#end> {

    public static void go(Activity activity) {
        Bundle bundle = new Bundle();
        JumpUtil.go(activity,${NAME}.class, bundle);
    }

    /*********************
     *     全 局 变 量    *
     *********************/
    //.....
    
    /*********************
     *  生 命 周 期 方 法  *
     *********************/
     
    @Override
    protected int setLayout() {
        return R.layout.#if (${LAYOUT_NAME} && ${LAYOUT_NAME} != "")${LAYOUT_NAME}#end#if (${LAYOUT_NAME} && ${LAYOUT_NAME} == "")activity_#end;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initViews(Object data, int propertyId) {
        
    }

    /*********************
     *     业 务 方 法    *
     *********************/
    //.....
     
}
```

## CommonFragment
```text
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import leo.work.support.base.fragment.CommonFragment;


#parse("File Header.java")
public class ${NAME} extends CommonFragment<#if (${BINDING_NAME} && ${BINDING_NAME} != "") ${BINDING_NAME} #else Fragment(xxxBinding)#end> {

    public static ${NAME} newInstance(FragmentManager mFragmentManager, String TAG) {
        ${NAME} fragment = null;
        if (mFragmentManager != null && !Is.isEmpty(TAG)) {
            fragment = (${NAME}) mFragmentManager.findFragmentByTag(TAG);
        }
        if (fragment == null) {
            fragment = new ${NAME}();
        }
        return fragment;
    }

    /*********************
     *     全 局 变 量    *
     *********************/
    //.....
    
    /*********************
     *  生 命 周 期 方 法  *
     *********************/

    @Override
    protected int setLayout() {
        return R.layout#if (${LAYOUT_NAME} && ${LAYOUT_NAME} != "").${LAYOUT_NAME} #else.fragment_#end;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initViews(Object data, int propertyId) {

    }

    /*********************
     *     业 务 方 法    *
     *********************/
    //.....

}
```

## CommonDialog
```text
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

#parse("File Header.java")
public class ${NAME} extends CommonDialog<#if (${BINDING_NAME} && ${BINDING_NAME} != "")${BINDING_NAME}#end#if (${BINDING_NAME} && ${BINDING_NAME} == "")Dialog(xxxBinding)#end> {

    private static ${NAME} instance;

    public static synchronized void show${NAME}(Context context, On${NAME}CallBack callBack) {
        dismissDialog();
        instance = new ${NAME}(context, callBack);
        instance.show();
    }

    public static synchronized void dismissDialog() {
        try {
            if ((instance != null) && instance.isShowing()) {
                instance.dismiss();
            }
        } catch (final Exception e) {

        } finally {
            instance = null;
        }
    }

    /*********************
     *     全 局 变 量    *
     *********************/
    private On${NAME}CallBack callBack;

    /*********************
     *  生 命 周 期 方 法  *
     *********************/

    public ${NAME}(@NonNull Context context, On${NAME}CallBack callBack) {
        super(context);       
        this.callBack = callBack;
    }

    @Override
    protected int setLayout() {
        return R.layout.#if (${LAYOUT_NAME} && ${LAYOUT_NAME} != "")${LAYOUT_NAME}#end#if (${LAYOUT_NAME} && ${LAYOUT_NAME} == "")dialog_#end;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews(Object data, int propertyId) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 必须设置这两个,才能设置宽度
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getDecorView().setBackgroundColor(Color.TRANSPARENT);
        // 遮罩层透明度
        //window.setDimAmount(0);
        // 设置宽度
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.windowAnimations = R.style.dialog_bottom_animation;
        window.setAttributes(params);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void destroy() {
        dismissDialog();
    }

    /*********************
     *     业 务 方 法    *
     *********************/
    //.....


    public interface On${NAME}CallBack {
     
    }
    
}
```

## CommonDialogFragment
```text
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import net.huidapay.live.R;

import leo.work.support.base.dialog.CommonDialogFragment;

#parse("File Header.java")
public class ${NAME} extends CommonDialogFragment<#if (${BINDING_NAME} && ${BINDING_NAME} != "")${BINDING_NAME}#end#if (${BINDING_NAME} && ${BINDING_NAME} == "")Dialog(xxxBinding)#end> {

    private static ${NAME} instance;

    public static synchronized void show${NAME}(FragmentManager fragmentManager, On${NAME}CallBack callBack) {
        dismissDialog();
        instance = new ${NAME}();
        instance.setCallBack(callBack);
        String TAG = ${NAME}.class.getSimpleName();
        instance.show(fragmentManager, TAG);
    }

    public static void dismissDialog() {
        try {
            if (instance == null || !instance.isVisible()) {
                return;
            }
            instance.dismissAllowingStateLoss();
        } catch (final Exception e) {

        } finally {
            instance = null;
        }
    }

    /*********************
     *     全 局 变 量    *
     *********************/
    private On${NAME}CallBack callBack;

    /*********************
     *  生 命 周 期 方 法  *
     *********************/

    @Override
    protected int setLayout() {
        return R.layout.#if (${LAYOUT_NAME} && ${LAYOUT_NAME} != "")${LAYOUT_NAME}#end#if (${LAYOUT_NAME} && ${LAYOUT_NAME} == "")dialog_#end;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(Object data, int propertyId) {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void destroy() {
        dismissDialog();
    }

    /*********************
     *     业 务 方 法    *
     *********************/

    // 设置回调
    public void setCallBack(On${NAME}CallBack callBack) {
        this.callBack = callBack;
    }


    public interface On${NAME}CallBack {

    }

}
```

## CommonRecyclerAdapter
```text
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

import android.content.Context;
import android.view.View;

import leo.work.support.base.adapter.CommonRecyclerAdapter;

#parse("File Header.java")
public class ${NAME} extends CommonRecyclerAdapter<Object, ${NAME}.${NAME}Holder,#if (${BINDING_NAME} && ${BINDING_NAME} != "") ${BINDING_NAME} #else ItemxxxBinding#end, ${NAME}.On${NAME}CallBack> {


    public ${NAME}(Context context, On${NAME}CallBack callBack) {
        super(context, callBack);
    }

    @Override
    protected int setLayout() {
        return R.layout#if (${LAYOUT_NAME} && ${LAYOUT_NAME} != "").${LAYOUT_NAME} #else.item_#end;
    }

    @Override
    protected ${NAME}Holder setViewHolder#if (${BINDING_NAME} && ${BINDING_NAME} != "")(${BINDING_NAME} #else(ItemxxxBinding#end binding, On${NAME}CallBack callBack) {
        return new ${NAME}Holder(context, binding, callBack);
    }


    static class ${NAME}Holder extends CommonRecyclerViewHolder<Object,#if (${BINDING_NAME} && ${BINDING_NAME} != "") ${BINDING_NAME} #else ItemxxxBinding#end, ${NAME}.On${NAME}CallBack> {

        public ${NAME}Holder(Context context,#if (${BINDING_NAME} && ${BINDING_NAME} != "") ${BINDING_NAME} #else ItemxxxBinding#end binding, On${NAME}CallBack callBack) {
            super(context, binding, callBack);
        }

        @Override
        protected void initView(int position, Object model) {

        }

        @Override
        protected void initListener(int position, Object model) {

        }
    }

    public interface On${NAME}CallBack {

    }
}
```

## CommonMultiRecyclerAdapter
```text
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

import android.content.Context;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import java.util.List;

import leo.work.support.base.adapter.CommonMultiRecyclerAdapter;
import leo.work.support.base.adapter.CommonRecyclerViewHolder;

#parse("File Header.java")
public class ${NAME} extends CommonMultiRecyclerAdapter<Object, ${NAME}.On${NAME}CallBack> {


    public ${NAME}(Context context, On${NAME}CallBack callBack) {
        super(context, callBack);
    }

    @Override
    protected int getItemType(int position) {
        switch (position) {
            //
            case xxx: {
                return xxx;
            }
            //
            case xxx:
            default: {
                return xxx;
            }
        }
    }

    @Override
    protected CommonRecyclerViewHolder getViewHolder(ViewGroup viewGroup, int type) {
        switch (type) {
            //
            case xxx: {
                return new AHolder(context, DataBindingUtil.inflate(layoutInflater, R.layout.item_xxx, viewGroup, false), callBack);
            }
            //
            case xxx:
            default: {
                return new BHolder(context, DataBindingUtil.inflate(layoutInflater, R.layout.item_xxx, viewGroup, false), callBack);
            }
        }

    }

    static class AHolder extends CommonRecyclerViewHolder<Object, ItemXXXBinding, ${NAME}.On${NAME}CallBack> {

        public AHolder(Context context, ItemXXXBinding binding, On${NAME}CallBack callBack) {
            super(context, binding, callBack);
        }

        @Override
        protected void initView(int position, Object model) {

        }

        @Override
        protected void initListener(int position, Object model) {

        }
    }

    static class BHolder extends CommonRecyclerViewHolder<Object, ItemXXXBinding, ${NAME}.On${NAME}CallBack> {

        public BHolder(Context context, ItemXXXBinding binding, On${NAME}CallBack callBack) {
            super(context, binding, callBack);
        }

        @Override
        protected void initView(int position, Object model) {

        }

        @Override
        protected void initListener(int position, Object model) {

        }
    }

    public interface On${NAME}CallBack {



    }
}
```

## CommonListViewAdapter
```text
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

import android.content.Context;

import leo.work.support.base.adapter.CommonListViewAdapter;
import leo.work.support.base.adapter.CommonViewHolder;

#parse("File Header.java")
public class ${NAME} extends CommonListViewAdapter<Object, ${NAME}.${NAME}Holder, #if (${BINDING_NAME} && ${BINDING_NAME} != "") ${BINDING_NAME} #else ItemxxxBinding#end, ${NAME}.On${NAME}CallBack> {

    public ${NAME}(Context context, On${NAME}CallBack callBack) {
        super(context, callBack);
    }

    @Override
    protected int setLayout() {
        return R.layout#if (${LAYOUT_NAME} && ${LAYOUT_NAME} != "").${LAYOUT_NAME} #else.item_#end;
    }

    @Override
    protected ${NAME}Holder setViewHolder#if (${BINDING_NAME} && ${BINDING_NAME} != "")(${BINDING_NAME} #else(ItemxxxBinding#end binding, On${NAME}CallBack callBack) {
        return new ${NAME}Holder(context, binding, callBack);
    }

    static class ${NAME}Holder extends CommonViewHolder<Object,#if (${BINDING_NAME} && ${BINDING_NAME} != "") ${BINDING_NAME} #else ItemxxxBinding#end, On${NAME}CallBack> {
        public ${NAME}Holder(Context context,#if (${BINDING_NAME} && ${BINDING_NAME} != "") ${BINDING_NAME} #else ItemxxxBinding#end binding, On${NAME}CallBack callBack) {
            super(context, binding, callBack);
        }

        @Override
        protected void initView(int position, Object model) {

        }

        @Override
        protected void initListener(int position, Object model) {

        }
    }


    public interface On${NAME}CallBack {



    }
}
```