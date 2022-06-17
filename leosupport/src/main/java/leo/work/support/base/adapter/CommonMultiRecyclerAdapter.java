package leo.work.support.base.adapter;

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

public abstract class CommonMultiRecyclerAdapter<M> extends RecyclerView.Adapter {
    public Context context;
    public LayoutInflater layoutInflater;
    private List<M> mList;


    public CommonMultiRecyclerAdapter(Context context, List<M> mList) {
        /**
         * 基本传值
         */
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
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
        return setViewHolder(layoutInflater.inflate(setLayout(viewType), parent, false), viewType);
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

    protected abstract void initView(final Object holder, int position, M t);

    protected abstract void initListener(final Object holder, int position, M t);

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

    public List<M> getData() {
        return mList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
