package com.dmt.tranbaphuc1999.ctms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter_Schedule_Class_Day extends BaseAdapter {

    private Context __Context;
    private int __Layout;
    private List<Schedule_Class> __List;

    //Constructor
    public Adapter_Schedule_Class_Day(Context __Context, int __Layout, List<Schedule_Class> __List) {
        this.__Context = __Context;
        this.__Layout = __Layout;
        this.__List = __List;
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

    //ViewHolder
    private class ViewHolder{
    TextView textViewTimeStart;
    TextView textViewTimeEnd;
    TextView textViewRoom;
    TextView textViewTeacher;
    TextView textViewSubject;
    ImageView imageViewIconStatus;
    }

    //Get View
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder= new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) __Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(__Layout,null);

            holder.textViewRoom = (TextView) view.findViewById(R.id.textViewRoom_Exam_Schedule);
            holder.textViewSubject = (TextView) view.findViewById(R.id.textViewSub);
            holder.textViewTeacher = (TextView) view.findViewById(R.id.textViewTeacher_Row_Study);
            holder.textViewTimeEnd = (TextView) view.findViewById(R.id.textViewTimeEnd_Row_Study);
            holder.textViewTimeStart = (TextView) view.findViewById(R.id.textViewSTT);
            holder.imageViewIconStatus = (ImageView) view.findViewById(R.id.imageViewIconStatus_Row_Study);

            view.setTag(holder);
        }
        else holder = (ViewHolder) view.getTag();

        Schedule_Class schedule_study = __List.get(i);
        holder.textViewTimeStart.setText(schedule_study.get__TimeStart());
        holder.textViewTimeEnd.setText(schedule_study.get__TimeEnd());
        holder.textViewTeacher.setText(schedule_study.get__Teacher());
        holder.textViewSubject.setText(schedule_study.get__Subject());
        holder.textViewRoom.setText(schedule_study.get__Room());
        holder.imageViewIconStatus.setImageResource(schedule_study.get__IconStatus());
        view.setBackgroundResource(schedule_study.get__Background());

        return view;
    }
}
