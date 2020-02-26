package com.dmt.tranbaphuc1999.ctms;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class Fragment_Tabbar_News extends Fragment {

    ListView listView_new;
    TextView textView_more;
    View view;
    Adapter_list_new adapter_list_new;
    ArrayList<News> arrayList_new;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tabbar_news,container,false);

        Init();

        Add();

        adapter_list_new = new Adapter_list_new(getActivity(),R.layout.row_list_new,arrayList_new);
        listView_new.setAdapter(adapter_list_new);

        listView_new.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), Web_view.class);
                intent.putExtra("url",arrayList_new.get(position).getLink());

                startActivity(intent);
            }
        });

        onClick_textMore();

        return view;
    }

    private void onClick_textMore() {
        textView_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Web_view.class);

                intent.putExtra("url","http://fithou.edu.vn");

                startActivity(intent);
            }
        });
    }

    private void Add() {
        arrayList_new.clear();
        Cursor dataNews = Connection_CTMS.dataBase.GetData("SELECT * FROM tblBantin");
        while(dataNews.moveToNext()){
            arrayList_new.add(new News(dataNews.getString(1),dataNews.getString(2),dataNews.getString(3)));
        }
    }

    private void Init() {
        listView_new = view.findViewById(R.id.list_new);
        textView_more = view.findViewById(R.id.text_more);
        arrayList_new=new ArrayList<>();
    }
}
