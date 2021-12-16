package com.leo.support.view.activity;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.leo.support.R;

import java.util.ArrayList;
import java.util.List;

import leo.work.support.base.activity.CommonActivity;
import leo.work.support.support.common.RecyclerSupport;

public class MainActivity extends CommonActivity {

    private RecyclerView mRecyclerView;
    private TestAdapter adapter;
    private List<String> mList;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mList = new ArrayList<>();
        mList.add("0");
        mList.add("1");
        mList.add("2");
        mList.add("3");
        adapter = new TestAdapter(context, mList);
        RecyclerSupport.setLinearLayoutManager(context, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }
}
