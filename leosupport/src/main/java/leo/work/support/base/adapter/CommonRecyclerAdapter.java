package leo.work.support.base.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

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
    public List<M> mList;
    public C callBack;

    public CommonRecyclerAdapter(Context context, C callBack) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.mList = new ArrayList<>();
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
        H viewHolder = (H) holder;
        viewHolder.setList(mList);
        viewHolder.initView(position, mList.get(position));
        viewHolder.initListener(position, mList.get(position));
    }

    protected abstract int setLayout();

    protected abstract H setViewHolder(B binding, C callBack);

}
