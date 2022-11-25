package com.surgery.scalpel.biz;

import android.view.MotionEvent;
import android.view.View;

import com.surgery.scalpel.base.LifeControlInterface;
import com.surgery.scalpel.base.biz.CommonLifeBiz;
import com.surgery.scalpel.util.CommonUtil;
import com.surgery.scalpel.util.Is;

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
        if (!isActivity()) {
            return;
        }
        View view = getActivity().getCurrentFocus();
        if (!Is.isShouldHideInput(view, event)) {
            return;
        }
        CommonUtil.hideKeyboard(getActivity(), view);
    }

}
