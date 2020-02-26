package com.dmt.tranbaphuc1999.ctms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Adapter_Nav_Show_Exam_Schedule extends BaseAdapter {

    private Context __context;
    private int __layout;
    private List<Show_Exam_Schedule> __list;
    ArrayList<Show_Exam_Schedule> arrayList;

    public Adapter_Nav_Show_Exam_Schedule(Context __context, int __layout, List<Show_Exam_Schedule> __list) {
        this.__context = __context;
        this.__layout = __layout;
        this.__list = __list;
        arrayList = new ArrayList<>();
        arrayList.addAll(__list);
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
        TextView textViewSubject_Exam;
        TextView textViewRoom_Exam;
        TextView textViewTime_Exam;
        TextView textViewDate_Exam;
        TextView textViewCode_Exam;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView==null)
        {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) __context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(__layout,null);

            holder.textViewSubject_Exam = (TextView) convertView.findViewById(R.id.textViewSub);
            holder.textViewRoom_Exam = (TextView) convertView.findViewById(R.id.textViewRoom_Exam_Schedule);
            holder.textViewTime_Exam = (TextView) convertView.findViewById(R.id.textViewTime_Exam_Schedule);
            holder.textViewDate_Exam = (TextView) convertView.findViewById(R.id.textViewSTT);
            holder.textViewCode_Exam = (TextView) convertView.findViewById(R.id.textViewCode_Exam_Schedule);

            convertView.setTag(holder);
        } else holder= (ViewHolder) convertView.getTag();

        Show_Exam_Schedule showExamSchedule = __list.get(position);

        holder.textViewSubject_Exam.setText(showExamSchedule.get__subjectExam());
        holder.textViewRoom_Exam.setText(showExamSchedule.get__roomExam());
        holder.textViewTime_Exam.setText(showExamSchedule.get__timeExam());
        holder.textViewDate_Exam.setText(showExamSchedule.get__dateExam());
        holder.textViewCode_Exam.setText(showExamSchedule.get__codeExam());
        return convertView;
    }

    //filter
    public  void filter (String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        __list.clear();

        if(charText.length()==0){
            __list.addAll(arrayList);
        }
        else {
            for(Show_Exam_Schedule showExamSchedule:arrayList){
                if(showExamSchedule.get__subjectExam().toLowerCase(Locale.getDefault()).contains(charText)){
                    __list.add(showExamSchedule);
                }
            }
        }
        notifyDataSetChanged();
    }
}
