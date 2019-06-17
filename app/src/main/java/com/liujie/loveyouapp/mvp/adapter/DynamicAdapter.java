package com.liujie.loveyouapp.mvp.adapter;

import android.content.Context;
import android.widget.TextView;

import com.liujie.loveyouapp.R;
import com.liujie.loveyouapp.app.BaseAdapter;
import com.liujie.loveyouapp.app.BaseHolder;
import com.liujie.loveyouapp.mvp.model.DynamicResponse;

/**
 * 动态的适配器
 */
public class DynamicAdapter extends BaseAdapter<DynamicResponse> {
    public DynamicAdapter(Context context) {
        super(context, R.layout.item_dynamic);
    }

    @Override
    public void bindViewHolder(BaseHolder holder, DynamicResponse itemData, int position) {
        TextView tvTitle = holder.getView(R.id.title);
        TextView tvTest = holder.getView(R.id.text);
        tvTitle.setText(itemData.getTitle());
        tvTest.setText(itemData.getDes());
    }
}
