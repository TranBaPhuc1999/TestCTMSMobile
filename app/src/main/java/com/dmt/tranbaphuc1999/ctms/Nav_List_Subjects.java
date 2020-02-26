package com.dmt.tranbaphuc1999.ctms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Nav_List_Subjects extends AppCompatActivity {

    // Init top level data
    List<String> listSubjectsLv1;
    HashMap<String,List<String>> listSubjectsLv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav__list__subjects);


        listSubjectsLv2 = new HashMap<>();


        AddExpandableList();

        ExpandableListView mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListViewSubjects);
        if (mExpandableListView != null) {
            Adapter_Parents_List_Subjects parentLevelAdapter = new Adapter_Parents_List_Subjects(this, listSubjectsLv1,listSubjectsLv2);
            mExpandableListView.setAdapter(parentLevelAdapter);
        }
    }

    //Add ExpandableListView
    private void AddExpandableList(){
        listSubjectsLv1 = new ArrayList<>();
        listSubjectsLv1.add("Khối I. Kiến thức giáo dục đại cương");
        listSubjectsLv1.add("Khối II. Kiến thức giáo dục chuyên nghiệp");
        listSubjectsLv1.add("1. Khối kiến thức cơ sở ngành");
        listSubjectsLv1.add("2. Khối kiến thức ngành");
        listSubjectsLv1.add("3. Khối kiến thức chuyên ngành");
        listSubjectsLv1.add("Khối III. Khối kiến thức tốt nghiệp");

        List<String> name0 = new ArrayList<>();
        name0.add("Tiếng Anh cơ bản 2");
        name0.add("Tiếng Anh chuyên ngành");
        name0.add("Lý thuyết xác suất và thống kê toán");
        name0.add("Đường lối cách mạng của Đảng CSVN");

        List<String> name1 = new ArrayList<>();
        name1.add("Tiếng Anh cơ bản 2");
        name1.add("Tiếng Anh chuyên ngành");
        name1.add("Lý thuyết xác suất và thống kê toán");
        name1.add("Đường lối cách mạng của Đảng CSVN");

        List<String> name2 = new ArrayList<>();
        name2.add("Lý thuyết xác suất và thống kê toán");
        name2.add("Đường lối cách mạng của Đảng CSVN");

        List<String> name3 = new ArrayList<>();
        name3.add("A. CN Công nghệ phần mềm (SE)");
        name3.add("B. CN Hệ thống thông tin (IS)");
        name3.add("C. CN Công nghệ đa phương tiện (MT)");
        name3.add("D. CN Mạng và an toàn hệ thống (NS)");

        List<String> name4 = new ArrayList<>();
        name4.add("Chuyên đề Lập trình ứng dụng");
        name4.add("Chuyên đề kết thúc chuyên ngành");
        name4.add("Đồ án tốt nghiệp");

        List<String> name5 = new ArrayList<>();
        name5.add("aaaaaaaaaaaa");
        name5.add("vvvvvvvvvvvvvvvvvvvvvv");

        listSubjectsLv2.put(listSubjectsLv1.get(0),name0);
        listSubjectsLv2.put(listSubjectsLv1.get(1),name5);
        listSubjectsLv2.put(listSubjectsLv1.get(2),name1);
        listSubjectsLv2.put(listSubjectsLv1.get(3),name2);
        listSubjectsLv2.put(listSubjectsLv1.get(4),name3);
        listSubjectsLv2.put(listSubjectsLv1.get(5),name4);
    }
}
