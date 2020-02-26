package com.dmt.tranbaphuc1999.ctms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Adapter_Nav_Bill extends BaseAdapter {

    private Context __context;
    private int __layout;
    private List<Bill> __list;

    public Adapter_Nav_Bill(Context __context, int __layout, List<Bill> __list) {
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
        TextView textView__Bill_Day, textView__Bill_Code, textView__Person_Create, textView__TC, textView__Money_Paid,textViewDetail;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView==null)
        {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) __context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(__layout,null);

            holder.textViewDetail = (TextView) convertView.findViewById(R.id.textView_Detail);
            holder.textView__Bill_Day = (TextView) convertView.findViewById(R.id.textView_Bill_Day);
            holder.textView__Bill_Code = (TextView) convertView.findViewById(R.id.textView_Bill_Code);
            holder.textView__Person_Create = (TextView) convertView.findViewById(R.id.textView_Person_Create);
            holder.textView__TC = (TextView) convertView.findViewById(R.id.textView_Total_TC);
            holder.textView__Money_Paid = (TextView) convertView.findViewById(R.id.textView_Money_Paid);

            convertView.setTag(holder);
        } else holder= (ViewHolder) convertView.getTag();

        final Bill bill = __list.get(position);

        holder.textView__Bill_Day.setText("Ngày lập: "+bill.get__Bill_Day().split(" ")[1]);
        holder.textView__Bill_Code.setText(bill.get__Bill_Code());
        holder.textView__Person_Create.setText(bill.get__Person_Create());
        holder.textView__TC.setText(bill.get__Total_TC());
        holder.textView__Money_Paid.setText(bill.get__Money_Paid());


        return convertView;
    }


}
