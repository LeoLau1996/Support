package leo.work.support.base.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import leo.work.support.base.adapter.CommonRecyclerAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2018/3/4.
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 备　　注:
 * ---------------------------------------------------------------------------------------------
 **/

public abstract class CommonRecyclerAdapter<T, H extends ViewHolder, B extends ViewDataBinding> extends RecyclerView.Adapter {

    public Context context;
    public LayoutInflater mInflater;
    public List<T> mList;

    public CommonRecyclerAdapter(Context context) {
        this(context, new ArrayList<T>());
    }

    public CommonRecyclerAdapter(Context context, List<T> mList) {
        /**
         * 基本传值
         */
        this.context = context;
        this.mInflater = LayoutInflater.from(this.context);
        this.mList = mList;
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
        B binding = DataBindingUtil.inflate(mInflater, layout, parent, false);
        return setViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        H viewHolder = (H) holder;
        viewHolder.initView(position, mList.get(position));
        viewHolder.initListener(position, mList.get(position));
    }

    protected abstract int setLayout();

    protected abstract H setViewHolder(B binding);

    protected abstract void initListener(final H holder, B binding, int position, T model);

    public abstract static class ViewHolder<T, B extends ViewDataBinding> extends RecyclerView.ViewHolder {

        private B binding;

        public ViewHolder(B binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        protected abstract void initView(int position, T model);

        protected abstract void initListener(int position, T model);
    }

}
