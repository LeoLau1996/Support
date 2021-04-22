package leo.work.support.Base.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import leo.work.support.Base.Adapter.BaseAdapterToRecycler.ViewHolder;

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

public abstract class BaseAdapterToRecycler<T, H extends ViewHolder> extends RecyclerView.Adapter {
    public Context context;
    public LayoutInflater mInflater;
    public List<T> mList;

    public BaseAdapterToRecycler(Context context, List<T> mList) {
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
        return setViewHolder(mInflater.inflate(setLayout(), parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        /**
         * 操作
         */
        initView((H) holder, position, mList.get(position));
        initListener((H) holder, position, mList.get(position));
    }


    protected abstract int setLayout();

    protected abstract RecyclerView.ViewHolder setViewHolder(View itemView);

    protected abstract void initView(final H holder, int position, T t);

    protected abstract void initListener(final H holder, int position, T t);

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
