package leo.work.support.base.adapter;

import android.content.Context;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2021/12/21
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 1613-3
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public abstract class CommonRecyclerViewHolder<M, B extends ViewDataBinding, C> extends RecyclerView.ViewHolder {

    public B binding;
    public C callBack;
    public List<M> mList;
    public Context context;

    public CommonRecyclerViewHolder(Context context, B binding, C callBack) {
        super(binding.getRoot());
        this.context = context;
        this.binding = binding;
        this.callBack = callBack;
    }

    protected abstract void initView(int position, M model);

    protected abstract void initListener(int position, M model);

    public List<M> getList() {
        return mList;
    }

    public void setList(List<M> mList) {
        this.mList = mList;
    }
}
