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
    private List<M> mList;
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
            B binding = DataBindingUtil.inflate(layoutInflater, setLayout(), parent, false);
            convertView = binding.getRoot();
            viewHolder = setViewHolder(binding, callBack);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (H) convertView.getTag();
        }
        viewHolder.setList(mList);
        viewHolder.initView(position, mList.get(position));
        viewHolder.initListener(position, mList.get(position));
        return convertView;
    }

    protected abstract int setLayout();

    protected abstract H setViewHolder(B binding, C callBack);

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
        notifyDataSetChanged();
    }

    public void addData(int index, M model, boolean notify) {
        mList.add(index, model);
        if (!notify) {
            return;
        }
        notifyDataSetChanged();
    }

    public void addData(List<M> list, boolean notify) {
        mList.addAll(list);
        if (!notify) {
            return;
        }
        notifyDataSetChanged();
    }

    public void addData(int index, List<M> list, boolean notify) {
        mList.addAll(index, list);
        if (!notify) {
            return;
        }
        notifyDataSetChanged();
    }

    public void removeData(int index, boolean notify) {
        mList.remove(index);
        if (!notify) {
            return;
        }
        notifyDataSetChanged();
    }

    public void removeAllData(boolean notify) {
        int size = mList.size();
        mList.clear();
        if (!notify) {
            return;
        }
        notifyDataSetChanged();
    }

    public List<M> getData() {
        return mList;
    }
}
