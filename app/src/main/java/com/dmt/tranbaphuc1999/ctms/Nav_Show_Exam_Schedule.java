package com.dmt.tranbaphuc1999.ctms;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.util.ArrayList;

public class Nav_Show_Exam_Schedule extends AppCompatActivity {

    ListView listView;
    ArrayList<Show_Exam_Schedule> arrayList;
    Adapter_Nav_Show_Exam_Schedule adapterNavShowExamSchedule;
    RadioGroup radioGroup;
    RadioButton fist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav__show__exam__schedule);

        //Init
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupSelect_Exam_Schedule);
        listView = (ListView) findViewById(R.id.listViewExamSchedule);
        arrayList = new ArrayList<>();

//        Cài adapter cho list
        AddFirst();
        adapterNavShowExamSchedule = new Adapter_Nav_Show_Exam_Schedule(this,R.layout.row_nav_show_exam_schedule,arrayList);
        listView.setAdapter(adapterNavShowExamSchedule);

//        Cài đặt nút đầu tiên luôn check
        radioGroup.check(R.id.radioButtonSoon_Exam_Schedule);
        RadioButtonClick();

    }



//    Thêm phần tử cho arraylist khi thay đổi bộ lọc
    private void Add(String STT) {
        arrayList.clear();
        Cursor dataExamSchedule;
        if(STT.equals("All"))
            dataExamSchedule = Connection_CTMS.dataBase.GetData("SELECT * FROM tblLichThi");
        else dataExamSchedule = Connection_CTMS.dataBase.GetData("SELECT * FROM tblLichThi WHERE STT = '"+STT+"'");
        while(dataExamSchedule.moveToNext()){
            arrayList.add(new Show_Exam_Schedule(dataExamSchedule.getString(2),dataExamSchedule.getString(3),dataExamSchedule.getString(4),dataExamSchedule.getString(5),dataExamSchedule.getString(6)));
        }
        adapterNavShowExamSchedule.notifyDataSetChanged();
    }

//    Thêm phần tử cho arraylist khi mở app
    private void AddFirst() {
        Cursor dataExamSchedule;
            dataExamSchedule = Connection_CTMS.dataBase.GetData("SELECT * FROM tblLichThi WHERE STT = 'Soon'");
        while(dataExamSchedule.moveToNext()){
            arrayList.add(new Show_Exam_Schedule(dataExamSchedule.getString(2),dataExamSchedule.getString(3),dataExamSchedule.getString(4),dataExamSchedule.getString(5),dataExamSchedule.getString(6)));
        }
    }


    private void RadioButtonClick(){
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButtonAll_Exam_Schedule: Add("All");  break;
                    case R.id.radioButtonDone_Exam_Schedule: Add("Done"); break;
                    case R.id.radioButtonSoon_Exam_Schedule: Add("Soon"); break;
                }
            }
        });
    }

    //Create Button Search
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem actionmenu = menu.findItem(R.id.secrch);
        SearchView searchView = (SearchView) actionmenu.getActionView();
        searchView.setQueryHint("Nhập tên môn học");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }


            //When change the editText
            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)){
                    adapterNavShowExamSchedule.filter("");
                    listView.clearTextFilter();
                }
                else {
                    adapterNavShowExamSchedule.filter(newText);
                }
                return true;
            }
        });

        return true;
    }


    //Button Search on Click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.secrch)
            return  true;
        return super.onOptionsItemSelected(item);
    }
}
