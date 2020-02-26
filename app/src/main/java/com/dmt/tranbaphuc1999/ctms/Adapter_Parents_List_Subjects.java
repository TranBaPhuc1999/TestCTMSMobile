package com.dmt.tranbaphuc1999.ctms;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_Parents_List_Subjects extends BaseExpandableListAdapter {
    private  Context mContext;
    private  List<String> mListDataHeader;
    private  Map<String, List<String>> mListData_SecondLevel_Map;
    private  Map<String, List<String>> mListData_ThirdLevel_Map;
    public Adapter_Parents_List_Subjects(Context mContext, List<String> mListDataHeader, Map<String, List<String>> mListData_SecondLevel_Map) {
        this.mContext = mContext;
        this.mListDataHeader = mListDataHeader;
        this.mListData_SecondLevel_Map=mListData_SecondLevel_Map;
        // Init second level data
//        String[] mItemHeaders;
//        for (int i = 0; i < mListDataHeader.size(); i++) {
//            String content = mListDataHeader.get(i);
//            switch (content) {
//                case "Level 1.1":
//                    mItemHeaders = mContext.getResources().getStringArray(R.array.items_array_expandable_level_one_one_child);
//                    break;
//                case "Level 1.2":
//                    mItemHeaders = mContext.getResources().getStringArray(R.array.items_array_expandable_level_one_two_child);
//                    break;
//                default:
//                    mItemHeaders = mContext.getResources().getStringArray(R.array.items_array_expandable_other_child);
//            }
//            mListData_SecondLevel_Map.put(mListDataHeader.get(i), Arrays.asList(mItemHeaders));
//        }
        // THIRD LEVEL
//        String[] mItemChildOfChild;
//        List<String> listChild;
        mListData_ThirdLevel_Map = new HashMap<>();
        for (Map.Entry<String, List<String>> element : mListData_SecondLevel_Map.entrySet()) {

            for(String string:element.getValue()){
                List<String> level3 = new ArrayList<>();
                switch (string){
                    case "A. CN Công nghệ phần mềm (SE)":
                        level3.add("Phát triển hệ thống trên các Hệ Bán hàng trực tuyến");
                        level3.add("Lập trình trên thiết bị di động nâng cao");
                        level3.add("Xử lý dữ liệu lớn với Apache Hadoop");
                        mListData_ThirdLevel_Map.put("A. CN Công nghệ phần mềm (SE)",level3);
                        break;
                    case "B. CN Hệ thống thông tin (IS)":
                        level3.add("Ứng dụng UML trong Phân tích và Thiết kế");
                        level3.add("Lập trình trên thiết bị di động nâng cao");
                        level3.add("Xử lý dữ liệu lớn với Apache Hadoop");
                        mListData_ThirdLevel_Map.put("B. CN Hệ thống thông tin (IS)",level3);
                        break;
                    case "C. CN Công nghệ đa phương tiện (MT)":
                        level3.add("Phát triển hệ thống trên các Hệ Bán hàng trực tuyến");
                        level3.add("Lập trình trên thiết bị di động nâng cao");
                        level3.add("Xử lý dữ liệu lớn với Apache Hadoop");
                        mListData_ThirdLevel_Map.put("C. CN Công nghệ đa phương tiện (MT)",level3);
                        break;
                    case "D. CN Mạng và an toàn hệ thống (NS)":
                        level3.add("Phát triển hệ thống trên các Hệ Bán hàng trực tuyến");
                        level3.add("Lập trình trên thiết bị di động nâng cao");
                        level3.add("Xử lý dữ liệu lớn với Apache Hadoop");
                        mListData_ThirdLevel_Map.put("D. CN Mạng và an toàn hệ thống (NS)",level3);
                        break;
                }
            }

        }
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mListData_SecondLevel_Map.get(mListDataHeader.get(groupPosition)).get(childPosition);
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final CustomExpListView secondLevelExpListView = new CustomExpListView(this.mContext);
        if(groupPosition==4&&childPosition==0){
            List<String> listlevel3 = new ArrayList<>();
            for (Map.Entry<String, List<String>> element : mListData_ThirdLevel_Map.entrySet()){
                listlevel3.add(element.getKey());
            }
            secondLevelExpListView.setAdapter(new Adapter_Child_List_Subjects(this.mContext,listlevel3, mListData_ThirdLevel_Map));
            return secondLevelExpListView;
        }
        else if(groupPosition!=4){

//            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.row_nav_list_subjects_level_3, parent, false);
//            }

            TextView lblListHeader = (TextView) convertView.findViewById(R.id.textViewlevel_3);
            String headerTitleLv2 = (String) getChild(groupPosition,childPosition);
            lblListHeader.setText(headerTitleLv2);
            return convertView;
//        lblListHeader.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
//        lblListHeader.setTextColor(Color.YELLOW);
        }
        return secondLevelExpListView;

    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return mListData_SecondLevel_Map.get(mListDataHeader.get(groupPosition)).size();
    }
    @Override
    public Object getGroup(int groupPosition) {
        return this.mListDataHeader.get(groupPosition);
    }
    @Override
    public int getGroupCount() {
        return this.mListDataHeader.size();
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.row_nav_list_subjects_level_1, parent, false);
        }
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.textViewlevel_1);
//        lblListHeader.setTypeface(null, Typeface.BOLD);
//        lblListHeader.setTextColor(Color.CYAN);
        lblListHeader.setText(headerTitle);
        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
