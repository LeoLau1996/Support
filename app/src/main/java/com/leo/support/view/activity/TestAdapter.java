package com.leo.support.view.activity;

import android.content.Context;

import com.leo.support.R;
import com.leo.support.databinding.ItemTestBinding;

import java.util.List;

import leo.work.support.base.adapter.BaseRecyclerMVVMAdapter;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间:  2021/4/29
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class TestAdapter extends BaseRecyclerMVVMAdapter<String, TestAdapter.MainHolder, ItemTestBinding> {

    public TestAdapter(Context context, List<String> mList) {
        super(context, mList);
    }

    @Override
    protected int setLayout() {
        return R.layout.item_test;
    }

    @Override
    protected MainHolder setViewHolder(ItemTestBinding binding) {
        return new MainHolder(binding);
    }


    @Override
    protected void initView(MainHolder holder, int position, String data) {
        holder.getBinding().setStr(data);
    }

    @Override
    protected void initListener(MainHolder holder, int position, String data) {

    }

    static class MainHolder extends BaseRecyclerMVVMAdapter.ViewHolder<ItemTestBinding> {

        public MainHolder(ItemTestBinding binding) {
            super(binding);
        }

    }
}
