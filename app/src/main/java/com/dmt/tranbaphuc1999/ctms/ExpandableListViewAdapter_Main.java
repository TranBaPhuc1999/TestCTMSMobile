package com.dmt.tranbaphuc1999.ctms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class ExpandableListViewAdapter_Main extends BaseExpandableListAdapter {

    Context context;
    List<SubjectsExpandableListView> listSubjects;
    HashMap<SubjectsExpandableListView,List<String>> listSubjectsChild;

    //Constructor
    public ExpandableListViewAdapter_Main(Context context, List<SubjectsExpandableListView> listSubjects, HashMap<SubjectsExpandableListView, List<String>> listSubjectsChild) {
        this.context = context;
        this.listSubjects = listSubjects;
        this.listSubjectsChild = listSubjectsChild;
    }

    @Override
    public int getGroupCount() {
        return listSubjects.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listSubjectsChild.get(listSubjects.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listSubjects.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listSubjectsChild.get(listSubjects.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //Get Group View
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_group_expandablelistview_main,null);}

            TextView textViewGroup = (TextView) view.findViewById(R.id.textViewGroup);
            ImageView imageViewGroup = (ImageView) view.findViewById(R.id.imageViewIconGroup);


        SubjectsExpandableListView subjectsExpandableListView = (SubjectsExpandableListView) getGroup(i);
        textViewGroup.setText(subjectsExpandableListView.get__title());
        imageViewGroup.setImageResource(subjectsExpandableListView.get__icon());
        return view;
    }

    //Get Child View
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_child_expandablelistview_main,null);}

            TextView textViewChild = (TextView) view.findViewById(R.id.textViewChild);

        String subjectsChildExpandableListView = (String) getChild(i,i1);
        textViewChild.setText(subjectsChildExpandableListView);

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
