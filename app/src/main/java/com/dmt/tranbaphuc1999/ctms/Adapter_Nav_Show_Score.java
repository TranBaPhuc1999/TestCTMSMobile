package com.dmt.tranbaphuc1999.ctms;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
public class Adapter_Nav_Show_Score extends BaseAdapter {

    private Context __context;
    private int __layout;
    private List<Show_Score> __list;
    ArrayList<Show_Score> arrayList;
    static ArrayList<Show_Score> DS_loc = new ArrayList<Show_Score>();
    public Adapter_Nav_Show_Score(Context __context, int __layout, List<Show_Score> __list) {
        this.__context = __context;
        this.__layout = __layout;
        this.__list = __list;
        arrayList = new ArrayList<>();
        arrayList.addAll(__list);
        DS_loc.addAll(__list);
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
        TextView textViewTeacher;
        TextView textViewSubject;
        TextView textViewTCNumber;
        TextView textViewTotalScore;
        TextView textViewDiligenceScore;
        TextView textViewFinalExaminationScore;
        TextView textViewTestScore;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView==null)
        {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) __context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(__layout,null);

            holder.textViewDiligenceScore = (TextView) convertView.findViewById(R.id.textViewDiligenceScore);
            holder.textViewFinalExaminationScore = (TextView) convertView.findViewById(R.id.textViewFinalExaminationScore);
            holder.textViewSubject = (TextView) convertView.findViewById(R.id.textViewSub);
            holder.textViewTCNumber = (TextView) convertView.findViewById(R.id.textViewTCNumber);
            holder.textViewTeacher = (TextView) convertView.findViewById(R.id.textViewTeacher);
            holder.textViewTestScore = (TextView) convertView.findViewById(R.id.textViewTestScore);
            holder.textViewTotalScore = (TextView) convertView.findViewById(R.id.textViewTotalScore);

            convertView.setTag(holder);
        } else holder= (ViewHolder) convertView.getTag();

        Show_Score showScore = __list.get(position);

        holder.textViewTestScore.setText(String.valueOf(showScore.get__testScore()));
        holder.textViewTeacher.setText(showScore.get__teacher());
        holder.textViewTCNumber.setText(String.valueOf(showScore.get__tcNumber()));
        holder.textViewSubject.setText(showScore.get__subject());
        holder.textViewDiligenceScore.setText(String.valueOf(showScore.get__diligenceScore()));
        holder.textViewFinalExaminationScore.setText(String.valueOf(showScore.get__finalExaminationScore()));
        holder.textViewTotalScore.setText(String.valueOf(showScore.get__totalScore()));


        return convertView;
    }


//    Lọc nè
    public  void filter (String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        __list.clear();

        if(charText.length()==0){
            __list.addAll(DS_loc);
        }
        else {
            for(Show_Score showScore:DS_loc){
                if(showScore.get__subject().toLowerCase(Locale.getDefault()).contains(charText)){
                    __list.add(showScore);
                }
            }
        }
        if(__list.size()==0) Nav_Show_Score.textViewStatusSearch.setText("Không có kết quả tìm kiếm cho \""+charText+"\"");
        else Nav_Show_Score.textViewStatusSearch.setText("");
        notifyDataSetChanged();
    }


    //    Lọc type
    public  void filter_type (int type){
        __list.clear();

        switch (type){
            case 1: {
                for(Show_Score showScore:arrayList){
                    if(showScore.get__totalScore()>=7.5)
                        __list.add(showScore);
                };
                break;
            }
            case 2: {
                for(Show_Score showScore:arrayList){
                    if(showScore.get__totalScore()>=5&&showScore.get__totalScore()<7.5)
                        __list.add(showScore);
                };
                break;
            }
            case 3: {
                for(Show_Score showScore:arrayList){
                    if(showScore.get__totalScore()>=4&&showScore.get__totalScore()<5.5)
                        __list.add(showScore);
                };
                break;
            }
            case 4: {
                for(Show_Score showScore:arrayList){
                    if(showScore.get__totalScore()<4)
                        __list.add(showScore);
                };
                break;
            }
            case 5: {
                __list.addAll(arrayList);
                break;
            }
        }

        if(__list.size()==0) Nav_Show_Score.textViewStatusSearch.setText("Không có môn nào!");
        else Nav_Show_Score.textViewStatusSearch.setText("");
        DS_loc.clear();
        DS_loc.addAll(__list);
        notifyDataSetChanged();
    }

//    sắp xếp
    public void sort (int sort){
//        __list.clear();
//
//        __list.addAll(arrayList);

        switch (sort){
            case 1:
                Collections.sort(__list, new DiemTang());
                break;

            case 2:
                Collections.sort(__list, new DiemGiam());
                break;

            case 3:
                Collections.sort(__list, new AZ());
                break;

            case 4:
                Collections.sort(__list, new ZA());
                break;

        }
        DS_loc.clear();
        DS_loc.addAll(__list);
        notifyDataSetChanged();
    }


}

class DiemTang implements Comparator<Show_Score> {
    public int compare(Show_Score s1, Show_Score s2) {
        if (s1.get__totalScore() == s2.get__totalScore())
            return 0;
        else if (s1.get__totalScore() > s2.get__totalScore())
            return 1;
        else
            return -1;
    }
}

class DiemGiam implements Comparator<Show_Score> {
    public int compare(Show_Score s1, Show_Score s2) {
        if (s1.get__totalScore() == s2.get__totalScore())
            return 0;
        else if (s1.get__totalScore() > s2.get__totalScore())
            return -1;
        else
            return 1;
    }
}

class AZ implements Comparator<Show_Score> {
    public int compare(Show_Score s1, Show_Score s2) {
        if (s1.get__subject().compareTo(s2.get__subject())==0)
            return 0;
        else if (s1.get__subject().compareTo(s2.get__subject())>0)
            return 1;
        else
            return -1;
    }
}

class ZA implements Comparator<Show_Score> {
    public int compare(Show_Score s1, Show_Score s2) {
        if (s1.get__subject().compareTo(s2.get__subject())==0)
            return 0;
        else if (s1.get__subject().compareTo(s2.get__subject())>0)
            return -1;
        else
            return 1;
    }
}