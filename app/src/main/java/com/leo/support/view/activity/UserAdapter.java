package com.leo.support.view.activity;

import android.content.Context;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.leo.support.R;
import com.leo.support.databinding.ItemUserBinding;

import java.util.List;

import leo.work.support.base.adapter.CommonMultiRecyclerAdapter;
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
public class UserAdapter extends CommonMultiRecyclerAdapter<String, UserAdapter.OnUserAdapterCallBack> {

    public UserAdapter(Context context, List<String> list, UserAdapter.OnUserAdapterCallBack callBack) {
        super(context, list, callBack);
    }

    @Override
    protected int getItemType(int position) {
        return 0;
    }

    @Override
    protected CommonRecyclerViewHolder getViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            default: {
                ItemUserBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_user, parent, false);
                return new UserHolder(context, binding, callBack);
            }
        }
    }

    static class UserHolder extends CommonRecyclerViewHolder<String, ItemUserBinding, UserAdapter.OnUserAdapterCallBack> {


        public UserHolder(Context context, ItemUserBinding binding, OnUserAdapterCallBack callBack) {
            super(context, binding, callBack);
        }

        @Override
        protected void initView(int position, String model) {

        }

        @Override
        protected void initListener(int position, String model) {

        }
    }

    public interface OnUserAdapterCallBack {

        void onClickItem(int position);

    }
}
