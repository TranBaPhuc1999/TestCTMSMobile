package com.dmt.tranbaphuc1999.ctms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Adapter_Nav_List_Teacher extends BaseAdapter {

    private Context __context;
    private int __layout;
    private List<List_Teacher> __list;
    ArrayList<List_Teacher> arrayList;

    public Adapter_Nav_List_Teacher(Context __context, int __layout, List<List_Teacher> __list) {
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
        TextView textViewTeacherName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView==null)
        {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) __context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(__layout,null);

            holder.textViewTeacherName = (TextView) convertView.findViewById(R.id.textViewTeacherName);

            convertView.setTag(holder);
        } else holder= (ViewHolder) convertView.getTag();

        List_Teacher list_teacher = __list.get(position);

        holder.textViewTeacherName.setText(list_teacher.get__nameTeacher());

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
            for(List_Teacher list_teacher:arrayList){
                if(list_teacher.get__nameTeacher().toLowerCase(Locale.getDefault()).contains(charText)){
                    __list.add(list_teacher);
                }
            }
        }
        notifyDataSetChanged();
    }
}
