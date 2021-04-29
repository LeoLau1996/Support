package leo.work.support.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import leo.work.support.base.adapter.BaseListViewAdapter.ViewHolder;


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
public abstract class BaseListViewAdapter<T, H extends ViewHolder> extends BaseAdapter {
    public Context context;
    public LayoutInflater mInflater;
    public List<T> mList;

    public BaseListViewAdapter(Context context, List<T> mList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mList = mList;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(setLayout(), null);
            viewHolder = setViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        /**
         * 操作
         */
        initView((H) viewHolder, position, this.mList.get(position));
        initListener((H) viewHolder, position, this.mList.get(position));
        return convertView;
    }

    protected abstract int setLayout();

    protected abstract ViewHolder setViewHolder(View convertView);

    protected abstract void initView(final H holder, final int position, final T bean);

    protected abstract void initListener(final H holder, final int position, final T bean);

    public static class ViewHolder {
        public ViewHolder(View view) {

        }
    }
}
