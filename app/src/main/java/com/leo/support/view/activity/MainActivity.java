package com.leo.support.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.leo.support.R;

import leo.work.support.biz.WorkFlow.CommonWorkFlowBiz;
import leo.work.support.biz.WorkFlow.WorkFlowControl;
import leo.work.support.biz.WorkFlow.WorkFlowTask;
import leo.work.support.support.toolSupport.LeoSupport;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LeoSupport.fullScreen(this, false);
        setContentView(R.layout.activity_main);

        new CommonWorkFlowBiz(control -> {
            control.doNextTask();
        }).addWorkFlowTask(new WorkFlowTask(1) {
            @Override
            public void doTask(WorkFlowTask lastWorkFlowTask, WorkFlowControl control) {
                control.doTaskById(3);
            }
        }).addWorkFlowTask(new WorkFlowTask(2) {
            @Override
            public void doTask(WorkFlowTask lastWorkFlowTask, WorkFlowControl control) {
                control.doNextTask();
            }
        }).addWorkFlowTask(new WorkFlowTask(3) {
            @Override
            public void doTask(WorkFlowTask lastWorkFlowTask, WorkFlowControl control) {
                control.doNextTask();
            }
        }).startTask(() -> {
        });
    }

}
