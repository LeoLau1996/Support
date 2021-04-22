package leo.work.support.Support.Common;

import android.content.Context;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2018/5/2.
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class RecyclerSupport {
    public static void setLinearLayoutManager(Context context, RecyclerView mRecyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public static void setLinearLayoutManagerForHorizontal(Context context, RecyclerView mRecyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public static void setGridLayoutManager(Context context, RecyclerView mRecyclerView, int spanCount) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanCount);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }

    //添加简单的分割线
    public static void addLine(Context context, RecyclerView mRecyclerView) {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
    }
}
