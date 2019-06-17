package com.liujie.loveyouapp.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.liujie.loveyouapp.mvp.service.OnItemClickListener;

/**
 * BaseHolder
 */
public class BaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;
    private OnItemClickListener mListener;// 声明自定义的接口

    public BaseHolder(@NonNull View itemView, Context context, OnItemClickListener listener) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mListener = listener;
        // 为ItemView添加点击事件
        itemView.setOnClickListener(this);
        mViews = new SparseArray<>();
    }

    public View getItemView() {
        return itemView;
    }

    public <T extends View> T getView(int id) {
        View v = mViews.get(id);
        if (v == null) {
            v = mConvertView.findViewById(id);
            mViews.put(id, v);
        }
        return (T) v;
    }

    @Override
    public void onClick(View v) {
        // getpostion()为Viewholder自带的一个方法，用来获取RecyclerView当前的位置，将此作为参数，传出去
        mListener.onItemClick(v, getPosition());
    }
}
