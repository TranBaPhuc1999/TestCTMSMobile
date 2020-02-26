package com.dmt.tranbaphuc1999.ctms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter_list_new extends BaseAdapter {

    private Context __Context;
    private int __Layout;
    private List<News> __List;

    //Constructor
    public Adapter_list_new(Context __Context, int __Layout, List<News> __List) {
        this.__Context = __Context;
        this.__Layout = __Layout;
        this.__List = __List;
    }

    @Override
    public int getCount() {
        return __List.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //ViewHolder
    private class ViewHolder{
        TextView textViewTitle, textViewDetail;
        ImageView imageView_new;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if(view==null){
            holder= new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) __Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(__Layout,null);

            holder.textViewTitle = (TextView) view.findViewById(R.id.textView_title);
            holder.textViewDetail = (TextView) view.findViewById(R.id.textView_detail);
            holder.imageView_new = (ImageView) view.findViewById(R.id.image_new);

            view.setTag(holder);
        }
        else holder = (ViewHolder) view.getTag();

        News news = __List.get(i);
        holder.textViewTitle.setText(news.getTitle());
        holder.textViewDetail.setText(news.getDetail());
        holder.imageView_new.setImageResource(R.drawable.logofit);

        return view;
    }
}
