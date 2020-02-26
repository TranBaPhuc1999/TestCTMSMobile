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

public class Adapter_Nav_Detail_Bill extends BaseAdapter {

    private Context __context;
    private int __layout;
    private List<Detail_Bill> __list;

    public Adapter_Nav_Detail_Bill(Context __context, int __layout, List<Detail_Bill> __list) {
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
        TextView textViewNumber, textViewSub, textViewTC, textViewCost, textViewMoney;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView==null)
        {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) __context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(__layout,null);

            holder.textViewNumber = (TextView) convertView.findViewById(R.id.textViewSTT);
            holder.textViewSub = (TextView) convertView.findViewById(R.id.textViewSub);
            holder.textViewTC = (TextView) convertView.findViewById(R.id.textViewNumberTC);
            holder.textViewCost = (TextView) convertView.findViewById(R.id.textViewCost);
            holder.textViewMoney = (TextView) convertView.findViewById(R.id.textViewMoney);

            convertView.setTag(holder);
        } else holder= (ViewHolder) convertView.getTag();

        Detail_Bill detailBill = __list.get(position);

        holder.textViewNumber.setText(String.valueOf(position+1));
        holder.textViewSub.setText(detailBill.get__Sub());
        holder.textViewTC.setText("Số tín: "+detailBill.get__NumberTC());
        holder.textViewCost.setText("Đơn giá: "+detailBill.get__Cost());
        holder.textViewMoney.setText(detailBill.get__Money());



        return convertView;
    }
}
