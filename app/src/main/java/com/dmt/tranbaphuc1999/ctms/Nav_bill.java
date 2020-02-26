package com.dmt.tranbaphuc1999.ctms;

import android.app.Dialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.dmt.tranbaphuc1999.ctms.MainActivity.sharedPreferencesUser;

public class Nav_bill extends AppCompatActivity {

    ListView listView;
    ArrayList<Bill> arrayList;
    Adapter_Nav_Bill adapter_nav_bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bill);

        Init();

        //Set Adapter for ListView
        Add();
        adapter_nav_bill = new Adapter_Nav_Bill(this,R.layout.row_bill,arrayList);
        listView.setAdapter(adapter_nav_bill);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailSchedule(arrayList.get(position).get__Bill_Code(),arrayList.get(position).get__TT(),arrayList.get(position).get__Money_Paid(),arrayList.get(position).get__Bill_Day(),arrayList.get(position).get__Person_Create(),arrayList.get(position).get__GT(),arrayList.get(position).get__CanNop());
            }
        });
    }

    private void Add() {
        Cursor dataBill = Connection_CTMS.dataBase.GetData("SELECT * FROM tblHoaDon");
        while(dataBill.moveToNext()){
            arrayList.add(new Bill(dataBill.getString(1),dataBill.getString(2),dataBill.getString(3),dataBill.getString(4),dataBill.getString(5),dataBill.getString(6),dataBill.getString(7),dataBill.getString(8)));
        }
    }

    //    Init
    private void Init(){
        listView = (ListView) findViewById(R.id.listView_Bill);
        arrayList = new ArrayList<>();
    }

    //    Cài đặt chi tiết lịch phòng của từng ô
    public void DetailSchedule(String CodeBill, String Total, String Paid, String Date, String GV, String GT, String CanNop){

//        Tạo dialog
        final Dialog dialog = new Dialog(Nav_bill.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_detail_bill);
        dialog.setCanceledOnTouchOutside(true);


        TextView textViewCode = (TextView) dialog.findViewById(R.id.textViewname);
        TextView textViewName = (TextView) dialog.findViewById(R.id.textViewName);
        TextView textViewCodeUser = (TextView) dialog.findViewById(R.id.textViewCodeUser);
        TextView textViewBirthDay = (TextView) dialog.findViewById(R.id.textViewBirth);
        TextView textViewClass = (TextView) dialog.findViewById(R.id.textViewClass);
        ListView listViewDetail = (ListView) dialog.findViewById(R.id.listView);
        TextView textViewTotal_Money = (TextView) dialog.findViewById(R.id.textViewTongTien);
        TextView textViewNeed = (TextView) dialog.findViewById(R.id.textViewNeed);
        TextView textViewGiamTru = (TextView) dialog.findViewById(R.id.textViewTongTiened);
        TextView textViewCosted = (TextView) dialog.findViewById(R.id.textViewCosted);
        TextView textViewPersonCreate = (TextView) dialog.findViewById(R.id.textViewPersonCreate);
        TextView textViewDate = (TextView) dialog.findViewById(R.id.textViewDateCreate);

        textViewCode.setText("Mã hóa đơn: "+CodeBill);

        textViewName.setText("Họ tên"+sharedPreferencesUser.getString("NameUser",""));
        textViewCodeUser.setText("Mã sinh viên"+sharedPreferencesUser.getString("CodeUser",""));
        textViewBirthDay.setText("Ngày sinh"+sharedPreferencesUser.getString("BirthDayUser",""));
        textViewClass.setText("Lớp"+sharedPreferencesUser.getString("ClassUser",""));

        textViewTotal_Money.setText(Total);
        textViewNeed.setText(CanNop);
        textViewGiamTru.setText(GT);
        textViewCosted.setText(Paid);

        textViewPersonCreate.setText("Người lập: "+GV);
        textViewDate.setText("Thời gian lập: "+Date);

        ArrayList<Detail_Bill> detailBillArrayList= new ArrayList<>();

        Cursor dataDetailBill = Connection_CTMS.dataBase.GetData("SELECT * FROM tblChiTietHoaDon WHERE MaHD='"+CodeBill+"' ");
        while(dataDetailBill.moveToNext()){
            detailBillArrayList.add(new Detail_Bill(dataDetailBill.getString(2),dataDetailBill.getString(3),dataDetailBill.getString(4),dataDetailBill.getString(5)));
        }

        Adapter_Nav_Detail_Bill adapterNavDetailBill = new Adapter_Nav_Detail_Bill(this,R.layout.row_detail_bill,detailBillArrayList);
        listViewDetail.setAdapter(adapterNavDetailBill);
        justifyListViewHeightBasedOnChildren(listViewDetail);

        dialog.show();
    }

    //Cài đặt chiều cao cố định cho list để không có thanh cuộn
    public void justifyListViewHeightBasedOnChildren (ListView listView) {

        Adapter_Nav_Detail_Bill adapter = (Adapter_Nav_Detail_Bill) listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }
}
