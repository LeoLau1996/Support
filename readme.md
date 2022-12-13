# 导入

```text

android {

    // MVVM
    dataBinding {
        enabled = true
    }
}

dependencies {
    // 基础包（必须）
    implementation files('libs/leosupport-release.aar')
    // 注解（非必需）
    annotationProcessor files('libs/apt-processor.jar')
    // ViewModel
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
}

```

# 代码模板

## 头文件

```text
/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: ${DATE}
 * ---------------------------------------------------------------------------------------------
 * 代码创建: ${USER}
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
```

## CommonActivity

```text
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

import android.app.Activity;
import android.os.Bundle;

import com.surgery.scalpel.base.activity.CommonActivity;
import com.surgery.scalpel.util.JumpUtil;



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
import com.surgery.scalpel.base.fragment.CommonFragment;


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

import com.surgery.scalpel.base.dialog.CommonDialogFragment;

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

import com.surgery.scalpel.base.adapter.CommonRecyclerAdapter;

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

import com.surgery.scalpel.base.adapter.CommonMultiRecyclerAdapter;
import com.surgery.scalpel.base.adapter.CommonRecyclerViewHolder;

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

import com.surgery.scalpel.base.adapter.CommonListViewAdapter;
import com.surgery.scalpel.base.adapter.CommonViewHolder;

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

## ObservableGet 快速生成Get方法

```text
@Bindable
#if($field.modifierStatic)
static ##
#end
$field.type ##
#if($field.recordComponent)
  ${field.name}##
#else
#set($name = $StringUtil.capitalizeWithJavaBeanConvention($StringUtil.sanitizeJavaIdentifier($helper.getPropertyName($field, $project))))
#if ($field.boolean && $field.primitive)
  is##
#else
  get##
#end
${name}##
#end
() {
  return $field.name;
}
```

### ObservableSet 快速生成Set方法

```text
#set($paramName = $helper.getParamName($field, $project))
#if($field.modifierStatic)
static ##
#end
void set$StringUtil.capitalizeWithJavaBeanConvention($StringUtil.sanitizeJavaIdentifier($helper.getPropertyName($field, $project)))($field.type $paramName) {
  #if ($field.name == $paramName)
    #if (!$field.modifierStatic)
      this.##
    #else
      $classname.##
    #end
  #end
  $field.name = $paramName;
  notifyPropertyChanged(BR.$field.name);
}
```