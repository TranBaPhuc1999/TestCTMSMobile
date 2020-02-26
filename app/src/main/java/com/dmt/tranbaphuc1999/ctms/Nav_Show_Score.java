package com.dmt.tranbaphuc1999.ctms;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Nav_Show_Score extends AppCompatActivity {

    ListView listView;
    static ArrayList<Show_Score> arrayList;
    Adapter_Nav_Show_Score adapterShowScore;
    TextView textViewSort;
    TextView textViewKind;
    static TextView textViewTongTC;
    static TextView textViewTBC;
    static  TextView textViewStatusSearch;
//    static float TongTC, TBC, SoLuongTC;

    IconizedMenu PopupMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav__show__score);


//        Ánh xạ
        Init();


//        Cài đặt adapter cho listView
        Add();


        adapterShowScore = new Adapter_Nav_Show_Score(this,R.layout.row_nav_show_score,arrayList);
        listView.setAdapter(adapterShowScore);



//        Click nút lọc điểm
        textViewKind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupmenu1();
            }
        });
        textViewSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupmenu2();
            }
        });

//        final Button _anchor = (Button) findViewById(R.id.buttonDefaultShowRoom);
//
//        _anchor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopUp(_anchor);
//            }
//        });

    }

    private void Init() {
        textViewKind = (TextView) findViewById(R.id.textViewKind);
        textViewSort = (TextView) findViewById(R.id.textViewSort);
        textViewTBC = (TextView) findViewById(R.id.textViewTBC);
        textViewTongTC = (TextView) findViewById(R.id.textViewTongTC);
        listView = (ListView) findViewById(R.id.listview);
        textViewStatusSearch = (TextView) findViewById(R.id.textViewStatusSearch);
        arrayList = new ArrayList<>();
    }


    //Set on Click TextViewKind
    private void popupmenu1(){
        PopupMenu popupMenu = new PopupMenu(this,textViewKind);
        popupMenu.getMenuInflater().inflate(R.menu.popup_nav_show_score,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemMenuCaiThien: textViewKind.setText("Môn cần cải thiện"); adapterShowScore.filter_type(3); break;
                    case R.id.itemMenuDiemCao: textViewKind.setText("Môn điểm cao"); adapterShowScore.filter_type(1); break;
                    case R.id.itemMenuDiemTB: textViewKind.setText("Môn điểm trung bình"); adapterShowScore.filter_type(2); break;
                    case R.id.itemMenuTatca: textViewKind.setText("Tất cả"); adapterShowScore.filter_type(5); break;
                    case R.id.itemMenuChuaQua: textViewKind.setText("Môn chưa qua"); adapterShowScore.filter_type(4); break;
                }
                return false;
            }
        });

        popupMenu.show();
    }


//    Click vào lọc danh sách
    private void popupmenu2(){
        PopupMenu popupMenu = new PopupMenu(this,textViewSort);
        popupMenu.getMenuInflater().inflate(R.menu.popup_nav_show_score_sort,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemMenuTang: textViewSort.setText("Điểm tăng dần"); adapterShowScore.sort(1); break;
                    case R.id.itemMenuGiam: textViewSort.setText("Điểm giảm dần"); adapterShowScore.sort(2); break;
                    case R.id.itemMenuAZ: textViewSort.setText("Thứ tự A-Z"); adapterShowScore.sort(3); break;
                    case R.id.itemMenuZA: textViewSort.setText("Thứ tự Z-A"); adapterShowScore.sort(4); break;
                }
                return false;
            }
        });

        popupMenu.show();
    }



    //Thêm phần tử vào arrayList
    private void Add() {
        Cursor dataScore = Connection_CTMS.dataBase.GetData("SELECT * FROM tblDiem");
        while(dataScore.moveToNext()){
            arrayList.add(new Show_Score(dataScore.getString(1),dataScore.getString(2),dataScore.getInt(3),dataScore.getFloat(4),dataScore.getFloat(5),dataScore.getFloat(6)));
        }

        TongTCAndTBC(arrayList);
    }


    //Tạo nút Search
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
                    adapterShowScore.filter("");
                    listView.clearTextFilter();
                }
                else {
                    adapterShowScore.filter(newText);
                }
                return true;
            }
        });

        return true;
    }


    //Click vào Button Search
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.secrch)
            return  true;
        return super.onOptionsItemSelected(item);
    }


////    Cái này là Icon thì phải :v
//    void showPopUp(View anchorView)
//    {
//        PopupMenu = new IconizedMenu(getWindow().getContext(), anchorView);
//        Menu menu = PopupMenu.getMenu();
//        MenuInflater inflater = PopupMenu.getMenuInflater();
//        inflater.inflate(R.menu.main, PopupMenu.getMenu());
//        PopupMenu.show();
//    }

//    Tính toán điểm trung bình tích lũy và tổng số tín chỉ
    static void TongTCAndTBC(List<Show_Score> List){

        float TongTC = 0, TBC = 0;
        String type;

        for(Show_Score showScore:List){

//            Môn qua mới được tính
            if(showScore.get__totalScore()>=4){
                TongTC+=showScore.get__tcNumber();
                TBC+=(float)(showScore.get__totalScore()*showScore.get__tcNumber());
            }
        }
        TBC= (float) ((TBC/TongTC)*0.4);
        TBC = (float) (Math.round(TBC*100.0)/100.0);

        if(TBC<2)
            type="Yếu";
        else {
            if(TBC<=2.49)
                type="Trung bình";
            else {
                if (TBC<=3.19)
                    type="Khá";
                else {
                    if(TBC<=3.59)
                        type="Giỏi";
                    else type="Xuất sắc";
                }
            }
        }

        textViewTongTC.setText("Tổng số tín chỉ: "+String.valueOf((int)TongTC));
        textViewTBC.setText("TBTL: "+String.valueOf(TBC)+" - "+type);
    }
}
