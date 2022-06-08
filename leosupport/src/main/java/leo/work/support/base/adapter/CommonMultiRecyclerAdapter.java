package leo.work.support.base.adapter;

import android.content.Context;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
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

public abstract class CommonMultiRecyclerAdapter<M, H extends CommonRecyclerViewHolder, B extends ViewDataBinding, C> extends RecyclerView.Adapter {

    public Context context;
    public LayoutInflater layoutInflater;
    public List<M> mList;
    public C callBack;


    public CommonMultiRecyclerAdapter(Context context, C callBack) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.mList = new ArrayList<>();
        this.callBack = callBack;
    }

    @Override
    public int getItemViewType(int position) {
        return setItemType(position);
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
        int layout = setLayout(viewType);
        B binding = DataBindingUtil.inflate(layoutInflater, layout, parent, false);
        return setViewHolder(binding, viewType, callBack);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        H viewHolder = (H) holder;
        viewHolder.setList(mList);
        viewHolder.initView(position, mList.get(position));
        viewHolder.initListener(position, mList.get(position));
    }

    protected abstract int setItemType(int position);

    protected abstract int setLayout(int viewType);

    protected abstract H setViewHolder(B binding, int viewType, C callBack);


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
