package leo.work.support.biz;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;

import leo.work.support.base.LifeControlInterface;
import leo.work.support.base.biz.CommonLifeBiz;
import leo.work.support.util.CommonUtil;
import leo.work.support.util.Is;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 点击输入框以外的View，隐藏键盘（只适用Activit）
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/1/11
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class EditTextHideBiz extends CommonLifeBiz {

    public EditTextHideBiz(LifeControlInterface lifeControlInterface) {
        super(lifeControlInterface);
    }

    @Override
    public void dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return;
        }
        if (!(lifeControlInterface instanceof Activity)) {
            return;
        }
        View view = ((Activity) lifeControlInterface).getCurrentFocus();
        if (!Is.isShouldHideInput(view, event)) {
            return;
        }
        CommonUtil.hideKeyboard((Activity) lifeControlInterface, view);
    }

}
