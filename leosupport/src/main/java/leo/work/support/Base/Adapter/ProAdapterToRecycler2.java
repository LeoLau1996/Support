package leo.work.support.Base.Adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:应对布局复杂、布局管理器多样（比如一部分是网格布局，一部分是垂直布局）的需求。但是不兼容瀑布流
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2018/3/4.
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 备　　注:
 * ---------------------------------------------------------------------------------------------
 **/

public abstract class ProAdapterToRecycler2<T> extends RecyclerView.Adapter {
    public Context context;
    public LayoutInflater mInflater;
    public List<T> mList;


    public ProAdapterToRecycler2(Context context, List<T> mList) {
        /**
         * 基本传值
         */
        this.context = context;
        this.mInflater = LayoutInflater.from(this.context);
        this.mList = mList;
    }

    @Override
    public int getItemViewType(int position) {
        return setItemType(position);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = setViewHolder(mInflater.inflate(setLayout(viewType), parent, false), viewType);
        return viewHolder;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return setSpanSize(position, getItemViewType(position));
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        /**
         * 操作
         */
        initView(holder, position, mList.get(position));
        initListener(holder, position, mList.get(position));
    }

    protected abstract int setItemType(int position);

    protected abstract int setSpanSize(int position, int viewType);

    protected abstract int setLayout(int viewType);

    protected abstract RecyclerView.ViewHolder setViewHolder(View itemView, int viewType);

    protected abstract void initView(final Object holder, int position, T t);

    protected abstract void initListener(final Object holder, int position, T t);

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
