package com.surgery.scalpel.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 简单的RecyclerView Adapter
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2018/3/4.
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 备　　注:
 * ---------------------------------------------------------------------------------------------
 **/

public abstract class CommonRecyclerAdapter<M, H extends CommonRecyclerViewHolder, B extends ViewDataBinding, C> extends RecyclerView.Adapter {

    public Context context;
    public LayoutInflater layoutInflater;
    private List<M> mList;
    public C callBack;

    public CommonRecyclerAdapter(Context context, C callBack) {
        this(context, new ArrayList<>(), callBack);
    }

    public CommonRecyclerAdapter(Context context, List<M> mList, C callBack) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.mList = mList;
        this.callBack = callBack;
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = setLayout();
        B binding = DataBindingUtil.inflate(layoutInflater, layout, parent, false);
        return setViewHolder(binding, callBack);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List payloads) {
        super.onBindViewHolder(holder, position, payloads);
        H viewHolder = (H) holder;
        viewHolder.setList(mList);
        viewHolder.refreshViews(position, mList.get(position), payloads);
        viewHolder.initListener(position, mList.get(position), payloads);
    }

    protected abstract int setLayout();

    protected abstract H setViewHolder(B binding, C callBack);

    public void addData(M model) {
        addData(model, true);
    }

    public void addData(int index, M model) {
        addData(index, model, true);
    }

    public void addData(List<M> list) {
        addData(list, true);
    }

    public void addData(int index, List<M> list) {
        addData(index, list, true);
    }

    public void removeData(int index) {
        removeData(index, true);
    }

    public void removeAllData() {
        removeAllData(true);
    }

    public void addData(M model, boolean notify) {
        mList.add(model);
        if (!notify) {
            return;
        }
        notifyItemInserted(mList.size());
    }

    public void addData(int index, M model, boolean notify) {
        mList.add(index, model);
        if (!notify) {
            return;
        }
        notifyItemRangeInserted(index, 1);
    }

    public void addData(List<M> list, boolean notify) {
        mList.addAll(list);
        if (!notify) {
            return;
        }
        notifyItemRangeInserted(mList.size(), list.size());
    }

    public void addData(int index, List<M> list, boolean notify) {
        mList.addAll(index, list);
        if (!notify) {
            return;
        }
        notifyItemRangeInserted(index, list.size());
    }

    public void removeData(int index, boolean notify) {
        mList.remove(index);
        if (!notify) {
            return;
        }
        notifyItemRemoved(index);
    }

    public void removeAllData(boolean notify) {
        int size = mList.size();
        mList.clear();
        if (!notify) {
            return;
        }
        notifyItemRangeRemoved(0, size);
    }

    public void setData(List<M> list) {
        setData(list, true);
    }

    public void setData(List<M> list, boolean notify) {
        this.mList = list;
        if (!notify) {
            return;
        }
        notifyDataSetChanged();
    }

    public List<M> getData() {
        return mList;
    }

}
