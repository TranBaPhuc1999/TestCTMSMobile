package com.dmt.tranbaphuc1999.ctms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Adapter_ListView_Infor extends BaseAdapter {

    private Context __context;
    private int __layout;
    private List<String> __list;

    public Adapter_ListView_Infor(Context __context, int __layout, List<String> __list) {
        this.__context = __context;
        this.__layout = __layout;
        this.__list = __list;
    }

    @Override
    public int getCount() {
        return __list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView textViewInfor;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView==null)
        {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) __context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(__layout,null);

            holder.textViewInfor = (TextView) convertView.findViewById(R.id.textViewInfor);

            convertView.setTag(holder);
        } else holder= (ViewHolder) convertView.getTag();

        String content = __list.get(position);

        holder.textViewInfor.setText(content);

        return convertView;
    }
}
