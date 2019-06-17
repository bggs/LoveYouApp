package com.liujie.loveyouapp.mvp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.liujie.loveyouapp.R;
import com.liujie.loveyouapp.mvp.Utils.PinyinUtils;
import com.liujie.loveyouapp.mvp.model.SecretResponse;

import java.util.List;

/**
 * 密友的适配器
 */
public class SecretAdapter extends BaseAdapter {
    private Context context;
    private List<SecretResponse> list;

    public SecretAdapter(Context context, List<SecretResponse> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_secret, null);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.mTextName = (TextView) convertView.findViewById(R.id.name);
            holder.mTextNum = (TextView) convertView.findViewById(R.id.phoneNum);
            holder.image_view = (ImageView) convertView.findViewById(R.id.image_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 拿到当前条目
        String str = list.get(position).getName(); // 取当前条目的首字母
        String first = PinyinUtils.getFirstLetter(str);
        holder.title.setText(first);
        holder.mTextName.setText(list.get(position).getName());
        holder.mTextNum.setText(list.get(position).getPhoneNum());
        TextDrawable drawable = TextDrawable.builder().buildRoundRect(new StringBuffer(list.get(position).getName()).substring(0,1), R.color.colorAccent, 10);
        holder.image_view.setImageDrawable(drawable);
        return convertView;
    }

    class ViewHolder {
        TextView title;
        TextView mTextName;
        TextView mTextNum;
        ImageView image_view;
    }
}
