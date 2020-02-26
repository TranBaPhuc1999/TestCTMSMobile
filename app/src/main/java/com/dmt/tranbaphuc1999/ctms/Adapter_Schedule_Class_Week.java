package com.dmt.tranbaphuc1999.ctms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class Adapter_Schedule_Class_Week extends BaseAdapter {

    private Context __Context;
    private int __Layout;
    private List<Adapter_Schedule_Class_Day> __List;
    private List<Thu_Ngay_Schedule_Class> __ListThu_Ngay;

    //Constructor
    public Adapter_Schedule_Class_Week(Context __Context, int __Layout, List<Adapter_Schedule_Class_Day> __List,List<Thu_Ngay_Schedule_Class> __ListThu_Ngay) {
        this.__Context = __Context;
        this.__Layout = __Layout;
        this.__List = __List;
        this.__ListThu_Ngay=__ListThu_Ngay;
    }

    @Override
    public int getCount() {
        return __List.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    //View Holder
    private class ViewHolder{
        TextView textViewNgay, textViewThu;
        ListView listViewWeek;
    }

    //Get view
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) __Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(__Layout, null);

            holder.textViewNgay = (TextView) view.findViewById(R.id.textViewNgay);
            holder.textViewThu = (TextView) view.findViewById(R.id.textViewEmail);
            holder.listViewWeek = (ListView) view.findViewById(R.id.listViewDay_Study);
            view.setTag(holder);
        }
        else holder = (ViewHolder) view.getTag();
        Adapter_Schedule_Class_Day adapter_schedule_study_day = __List.get(i);

            holder.textViewThu.setText(__ListThu_Ngay.get(i).getThu());
            holder.textViewNgay.setText(__ListThu_Ngay.get(i).getNgay());
            holder.listViewWeek.setAdapter(adapter_schedule_study_day);
            justifyListViewHeightBasedOnChildren(holder.listViewWeek);


        return view;
    }

    //Cài đặt chiều cao cố định cho list để không có thanh cuộn
    public void justifyListViewHeightBasedOnChildren (ListView listView) {

        Adapter_Schedule_Class_Day adapter = (Adapter_Schedule_Class_Day) listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }
}
