package leo.work.support.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.util.List;


/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:基础适配器
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2018/3/14.
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public abstract class CommonListViewAdapter<M, H extends CommonViewHolder, B extends ViewDataBinding, C> extends BaseAdapter {

    public Context context;
    public LayoutInflater layoutInflater;
    public List<M> mList;
    public C callBack;

    public CommonListViewAdapter(Context context, List<M> mList, C callBack) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.mList = mList;
        this.callBack = callBack;
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        if (mList == null) {
            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        H viewHolder;
        if (convertView == null) {
            int layout = setLayout();
            B binding = DataBindingUtil.inflate(layoutInflater, layout, parent, false);
            viewHolder = setViewHolder(binding, callBack);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (H) convertView.getTag();
        }
        viewHolder.initView(position, mList.get(position));
        viewHolder.initListener(position, mList.get(position));
        return convertView;
    }

    protected abstract int setLayout();

    protected abstract H setViewHolder(B binding, C callBack);

}
