package com.leo.support.view.activity;

import android.content.Context;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.leo.support.R;
import com.leo.support.databinding.ItemTestBinding;

import leo.work.support.base.adapter.CommonRecyclerAdapter;
import leo.work.support.base.adapter.CommonRecyclerViewHolder;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/6/26
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class TestAdapter extends CommonRecyclerAdapter<String, TestAdapter.TestHolder, ItemTestBinding, TestAdapter.OnTestAdapterCallBack> {


    public TestAdapter(Context context, OnTestAdapterCallBack callBack) {
        super(context, callBack);
    }

    @Override
    protected TestHolder getViewHolder(ViewGroup parent, int viewType) {
        ItemTestBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, parent, false);
        return new TestHolder(context, binding, callBack);
    }

    static class TestHolder extends CommonRecyclerViewHolder<String, ItemTestBinding, OnTestAdapterCallBack> {


        public TestHolder(Context context, ItemTestBinding binding, OnTestAdapterCallBack callBack) {
            super(context, binding, callBack);
        }

        @Override
        protected void initView(int position, String model) {

        }

        @Override
        protected void initListener(int position, String model) {

        }
    }

    public interface OnTestAdapterCallBack {

        void onClickItem(int position);

    }

}
