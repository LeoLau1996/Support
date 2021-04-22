package leo.work.support.Base.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:应对布局稍微复杂、但是都是统一布局管理器的（比如都是垂直布局）需求。
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2018/3/4.
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 备　　注:
 * ---------------------------------------------------------------------------------------------
 **/

public abstract class ProAdapterToRecycler<T> extends RecyclerView.Adapter {
    public Context context;
    public LayoutInflater mInflater;
    public List<T> mList;


    public ProAdapterToRecycler(Context context, List<T> mList) {
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        /**
         * 操作
         */
        initView(holder, position, mList.get(position));
        initListener(holder, position, mList.get(position));
    }

    protected abstract int setItemType(int position);

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
