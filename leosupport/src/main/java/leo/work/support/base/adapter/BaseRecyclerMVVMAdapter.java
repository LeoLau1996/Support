package leo.work.support.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间:  2021/4/29
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public abstract class BaseRecyclerMVVMAdapter<T, H extends BaseRecyclerMVVMAdapter.ViewHolder, B extends ViewDataBinding> extends RecyclerView.Adapter {

    public Context context;
    public LayoutInflater mInflater;
    public List<T> mList;

    public BaseRecyclerMVVMAdapter(Context context, List<T> mList) {
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
        B binding = DataBindingUtil.inflate(mInflater, setLayout(), parent, false);
        return setViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        initView((H) holder, position, mList.get(position));
        initListener((H) holder, position, mList.get(position));
    }

    //设置样式
    protected abstract int setLayout();

    //获取Item布局
    protected abstract H setViewHolder(B binding);

    //初始化数据
    protected abstract void initView(H holder, int position, T data);

    //初始化监听
    protected abstract void initListener(H holder, int position, T data);


    public static class ViewHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {

        private B binding;

        public ViewHolder(B binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public B getBinding() {
            return binding;
        }
    }
}