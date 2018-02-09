package com.shitu.epathmapguide.common;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shitu.epathmapguide.R;

/**
 * Created by lfei1 on 12/16/2016.
 */

public class ViewUtils {

    /**
     * 配置recycleview的间距
     *
     * @param recyclerView
     * @param context
     */
    public static void initRecycleView(RecyclerView recyclerView, Context context) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL_LIST,
                2,
                context.getResources().getColor(R.color.colorBackGround)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 配置recycleview的间距
     *
     * @param recyclerView
     * @param context
     */
    public static void initRecycleView(RecyclerView recyclerView, Context context, int height) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL_LIST,
                height,
                context.getResources().getColor(R.color.colorBackGround)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 配置recycleview的间距
     *
     * @param recyclerView
     * @param context
     */
    public static LinearLayoutManager initRecycleViewResult(RecyclerView recyclerView, Context context, int height) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL_LIST,
                height,
                context.getResources().getColor(R.color.colorBackGround)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return linearLayoutManager;
    }

    /**
     * 配置recycleview的间距
     *
     * @param recyclerView
     * @param context
     */
    public static void initRecycleView(RecyclerView recyclerView, Context context, int height, int resource) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL_LIST,
                height,
                context.getResources().getColor(resource)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 配置recycleview的间距
     *
     * @param recyclerView
     * @param context
     */
    public static void initRecycleViewHorizontal(RecyclerView recyclerView, Context context, int height) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.HORIZONTAL_LIST,
                height,
                context.getResources().getColor(android.R.color.white)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

//    public static void initRecycleViewPaddingLeft(RecyclerView recyclerView, Context context) {
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        recyclerView.addItemDecoration(new DividerItemDecoration(context,
//                DividerItemDecoration.VERTICAL_LIST,
//                context.getResources().getDimensionPixelSize(R.dimen.height_1),
//                context.getResources().getColor(R.color.colorGrayBackground)));
//    }

    /**
     * 设置刷新的颜色
     *
     * @param refreshLayout
     * @param context
     */
    public static void initRefreshLayout(SwipeRefreshLayout refreshLayout, Context context) {
        refreshLayout.setColorSchemeColors(context.getResources().getColor(R.color.colorPrimary),
                context.getResources().getColor(R.color.colorOrange),
                context.getResources().getColor(R.color.colorBlue),
                context.getResources().getColor(R.color.colorRed));
    }
}
