package com.liujie.loveyouapp.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liujie.loveyouapp.mvp.service.OnItemClickListener;

import java.util.List;

/**
 * @param <T> 适配器封装
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder> {

    private Context context;
    private int layoutId;
    private List<T> dataList;
    private OnItemClickListener mClickListener;

    public BaseAdapter(Context context, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new BaseHolder(itemView, context, mClickListener);
    }

    @Override
    public void onBindViewHolder(final BaseHolder holder, int position) {
        bindViewHolder(holder, dataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public abstract void bindViewHolder(BaseHolder holder, T itemData, int position);

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }
}