package com.dmt.tranbaphuc1999.ctms;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class Fragment_Tabbar_Schedule_Room extends Fragment {

    HorizontalScrollView hori,hori2;
    TableLayout scrollablePart, fixedColumn,header;
    LinearLayout linear;
    TextView textViewPickDate;
    static Calendar calendar;
    SimpleDateFormat simpleDateFormat,simpleDateFormatList;
    TableRow row;
    TableRow.LayoutParams wrapWrapTableRowParams;
    View view;
    ProgressBar progressBar;
    Button buttonShowScheduleRoom, buttonSaveChoice, buttonDefaultShowRoom;
    TextView textViewDay;
    CheckBox checkBoxAllTime, checkBox6h, checkBox12h, checkBox17h, checkBoxAllFloor, checkBoxFloor2, checkBoxFloor3, checkBoxFloor4, checkBoxFloor5;
    int columnWidths, columnWidthsImage, rowHeight, rowHeightImage,columnWidthsHeader;
    ArrayList<String> arrayListRoom;
    String[] arrayDay = {"Thứ 2","Thứ 3","Thứ 4","Thứ 5","Thứ 6","Thứ 7","CN"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tabbar_schedule_room,container,false);

//        Khai báo nè
        Init();

//        Cài đặt độ mờ, và ẩn progressBar đi, Méo biết làm chuyên nghiệp nên làm thủ công ^^, Vô hiệu hóa nút xem lịch khi mới vào luôn
        progressBar.setVisibility(View.GONE);
        progressBar.setBackgroundColor(0xbf000000);
        buttonShowScheduleRoom.setEnabled(false);       /*Vô hiệu hóa nút lọc khi mới vào*/


//        Thêm danh sách phòng khi mới vào
        AddListRoomFirst();


//        Cài đặt chiều cho 2 scrollview
        hori.setVerticalScrollBarEnabled(false);
        hori.setHorizontalScrollBarEnabled(false);


//        Tìm ngày đầu tiên trong tuần khi mở app
        calendar =Calendar.getInstance();
        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            Toasty.warning(getActivity(), "Hôm nay là chủ nhật, hiển thị lịch tuần tới!", Toast.LENGTH_LONG, true).show();

            calendar.add(Calendar.DATE, 1); // Đi đến thứ hai đầu tuần tiếp theo nếu đó là chủ nhật
        } else {
            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                calendar.add(Calendar.DATE, -1); // Đi đến ngày thứ hai đầu tuần nếu hôm nay không phải chủ nhật
            }
        }
        textViewPickDate.setText("Tuần từ: "+simpleDateFormat.format(calendar.getTime()));
        textViewPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogScheduleRoom();
            }
        });


//        Cài đặt Params cho row
        wrapWrapTableRowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        row = new TableRow(getActivity());

//        Hiện lịch khi mở app
        SetScheduleRoomFirst(simpleDateFormatList.format(calendar.getTime()));

//        Di chuyển đồng bộ các Scrollview
        ScrollviewsetOnTouchListener();


//        Click nút xem lịch
        Show_Schedule();

        return view;
    }


//    Hàm ánh xạ
    private void Init(){
        simpleDateFormatList = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        fixedColumn = (TableLayout) view.findViewById(R.id.fixed_column);
        header = (TableLayout) view.findViewById(R.id.table_header);
        linear=(LinearLayout)view.findViewById(R.id.fillable_area);
        scrollablePart = (TableLayout) view.findViewById(R.id.scrollable_part);
        hori=(HorizontalScrollView)view.findViewById(R.id.horizon);
        hori2=(HorizontalScrollView)view.findViewById(R.id.hr_2);
        textViewPickDate = (TextView) view.findViewById(R.id.textViewPickDate);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarLoadingRoom);
        buttonShowScheduleRoom = (Button) view.findViewById(R.id.buttonShowScheduleRoom);
        arrayListRoom = new ArrayList<>();

        //        Cài đặt các thông số kích thước
        columnWidths = Convert(60);
        columnWidthsImage = Convert(58);
        rowHeight = Convert(60);
        rowHeightImage = Convert(58);
        columnWidthsHeader = Convert(80);
    }


//        Click nút xem lịch
    private void Show_Schedule(){
        buttonShowScheduleRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                buttonShowScheduleRoom.setEnabled(false);               /*Vô hiệu hóa nút xem lịch tạm thời*/
                Take_Date();                                        /*Lấy ngày đầu tiên của tuần vừa chọn*/
                String stt = "";
                try {
                    stt = new Take_Schedule_Room(getActivity()).execute(simpleDateFormatList.format(calendar.getTime())).get();           /*Lấy lịch theo thời gian*/
                }catch (Exception e) {
                    Toasty.error(getActivity(), "Lỗi cập nhật xảy ra!", Toast.LENGTH_LONG, true).show();
                    Log.d("acb","Looi ne-----------------"+e.toString()+"-------------------");
                }
                if(stt.equals("success")) {
                    SetListRoom();        /* Cập nhật thành công mới cho phép đổ ra view*/
                    SetScheduleRoom(simpleDateFormatList.format(calendar.getTime()));
                    textViewDay.setText(simpleDateFormat.format(calendar.getTime()));
                    textViewPickDate.setText("Tuần từ: "+simpleDateFormat.format(calendar.getTime()));
                }
                progressBar.setVisibility(View.GONE);
            }
        });

    }


    private void cal(){
        final Handler handler2 = new Handler();
        Runnable runnable = new Runnable() {
            int i=0;
            public void run()
            {
                if(i==0)
                {
                    try{
                        moveScrollView();
                    }catch (Exception e){
                    }
                    i++;
                }
                handler2.postDelayed(this,500);
            }
        };
        handler2.postDelayed(runnable, 500);
    }
    private void cal1(){
        final Handler handler2 = new Handler();
        Runnable runnable = new Runnable() {
            int i=0;
            public void run()
            {
                if(i==0)
                {
                    try{
                        moveScrollView1();
                    }catch (Exception e){
                    }
                    i++;
                }
                handler2.postDelayed(this,500);
            }
        };
        handler2.postDelayed(runnable, 500);
    }


    private int scrollPos = 0;
//    Cuộn scroll view
    public void moveScrollView(){

        scrollPos                           =   (int) (hori.getScrollX() + 1.0);
        scrollPos                           =   (int) (hori2.getScrollX() + 1.0);

        hori2.scrollTo(scrollPos, 0);
        hori.scrollTo(scrollPos, 0);

    }
    public void moveScrollView1(){

        scrollPos                           =   (int) (hori2.getScrollX() + 1.0);
        scrollPos                           =   (int) (hori.getScrollX() + 1.0);

        hori.scrollTo(scrollPos, 0);
        hori2.scrollTo(scrollPos, 0);

    }

//    Chạm lướt ScrollView
    private void ScrollviewsetOnTouchListener(){
        hori2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                moveScrollView();
                cal();

                return false;
            }
        });
        hori.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                moveScrollView1();
                cal1();

                return false;
            }
        });
    }

//    Cài đặt Textview trên Header và Image biểu thị trạng thái
    public TextView makeTableRowWithText(String text, int widthInPercentOfScreenWidth, int   fixedHeightInPixels) {
        TextView recyclableTextView;
        recyclableTextView = new TextView(getActivity());
        recyclableTextView.setText(text);
        recyclableTextView.setTextColor(0xFF292828);
        recyclableTextView.setTextSize(14);
//        recyclableTextView.setTypeface(null,Typeface.BOLD);
        recyclableTextView.setWidth(widthInPercentOfScreenWidth);
        recyclableTextView.setHeight(fixedHeightInPixels);
        recyclableTextView.setGravity(Gravity.CENTER);
        recyclableTextView.setBackgroundResource(R.drawable.custom_header_stroke_schedule_room);
        return recyclableTextView;
    }

    public ImageView makeTableRowWithImage(int status, int widthInPercentOfScreenWidth, int   fixedHeightInPixels) {
        TableRow.LayoutParams params = new TableRow.LayoutParams(widthInPercentOfScreenWidth,fixedHeightInPixels);
        params.setMargins(Convert(1),Convert(1),Convert(1),Convert(1));
        ImageView recyclableImageView;
        recyclableImageView = new ImageView(getActivity());
        recyclableImageView.setImageResource(status);
        recyclableImageView.setLayoutParams(params);

        recyclableImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        return recyclableImageView;
    }


//    Chuyển dp thành xp
    private int Convert(int dp){
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return (int) px;
    }

    private void AddListRoomFirst(){
        arrayListRoom.add("21");
        arrayListRoom.add("22");
        arrayListRoom.add("23");
        arrayListRoom.add("24");
        arrayListRoom.add("31");
        arrayListRoom.add("32");
        arrayListRoom.add("33");
        arrayListRoom.add("34");
        arrayListRoom.add("41");
        arrayListRoom.add("42");
        arrayListRoom.add("43");
        arrayListRoom.add("44");
        arrayListRoom.add("51");
        arrayListRoom.add("52");
    }

//    Cài đặt danh sách phòng
    private void SetListRoom(){
        arrayListRoom.clear();
        if(checkBoxAllFloor.isChecked()){
            arrayListRoom.add("P21");
            arrayListRoom.add("P22");
            arrayListRoom.add("P23");
            arrayListRoom.add("P24");
            arrayListRoom.add("P31");
            arrayListRoom.add("P32");
            arrayListRoom.add("P33");
            arrayListRoom.add("P34");
            arrayListRoom.add("P41");
            arrayListRoom.add("P42");
            arrayListRoom.add("P43");
            arrayListRoom.add("P44");
            arrayListRoom.add("P51");
            arrayListRoom.add("P52");
        }
        if(checkBoxFloor2.isChecked()){
            arrayListRoom.add("P21");
            arrayListRoom.add("P22");
            arrayListRoom.add("P23");
            arrayListRoom.add("P24");
        }
        if(checkBoxFloor3.isChecked()){
            arrayListRoom.add("P31");
            arrayListRoom.add("P32");
            arrayListRoom.add("P33");
            arrayListRoom.add("P34");
        }
        if(checkBoxFloor4.isChecked()){
            arrayListRoom.add("P41");
            arrayListRoom.add("P42");
            arrayListRoom.add("P43");
            arrayListRoom.add("P44");
        }
        if(checkBoxFloor5.isChecked()){
            arrayListRoom.add("P51");
            arrayListRoom.add("P52");
        }
    }

//    Click nút lọc lịch phòng
    private void DialogScheduleRoom(){
        buttonShowScheduleRoom.setEnabled(true);
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_pickdate_schedule_room);
        dialog.setCanceledOnTouchOutside(false);

        buttonDefaultShowRoom = (Button) dialog.findViewById(R.id.buttonDefaultShowRoom);
        buttonSaveChoice = (Button) dialog.findViewById(R.id.buttonSaveChoice);
        checkBoxAllTime = (CheckBox) dialog.findViewById(R.id.checkBoxAllTime);
        checkBox6h = (CheckBox) dialog.findViewById(R.id.checkBox6h);
        checkBox12h = (CheckBox) dialog.findViewById(R.id.checkBox12h);
        checkBox17h = (CheckBox) dialog.findViewById(R.id.checkBox17h);
        checkBoxAllFloor = (CheckBox) dialog.findViewById(R.id.checkBoxAllFloor);
        checkBoxFloor2 = (CheckBox) dialog.findViewById(R.id.checkBoxFloor2);
        checkBoxFloor3 = (CheckBox) dialog.findViewById(R.id.checkBoxFloor3);
        checkBoxFloor4 = (CheckBox) dialog.findViewById(R.id.checkBoxFloor4);
        checkBoxFloor5 = (CheckBox) dialog.findViewById(R.id.checkBoxFloor5);
        textViewDay = (TextView) dialog.findViewById(R.id.textViewPickDay);

        textViewDay.setText(simpleDateFormat.format(calendar.getTime()));

//        Click nút chọn ngày
        textViewDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dialog Calendar
                    int day = calendar.get(Calendar.DATE);
                    int month = calendar.get(Calendar.MONTH);
                    int year = calendar.get(Calendar.YEAR);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                    calendar.set(i,i1,i2);
                                    textViewDay.setText(simpleDateFormat.format(calendar.getTime()));
                                    textViewPickDate.setText("Tuần từ: "+simpleDateFormat.format(calendar.getTime()));
                                }
                            },year,month,day);
                    datePickerDialog.show();
            }
        });


//        Cài đặt chọn các checkbox
        CheckABox(checkBoxAllTime);
        CheckABox(checkBox6h);
        CheckABox(checkBox12h);
        CheckABox(checkBox17h);
        CheckRoom(checkBoxAllFloor);
        CheckRoom(checkBoxFloor2);
        CheckRoom(checkBoxFloor3);
        CheckRoom(checkBoxFloor4);
        CheckRoom(checkBoxFloor5);

        buttonDefaultShowRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxAllFloor.isChecked();
                checkBoxAllTime.isChecked();

//              Tìm ngày đầu tiên trong tuần hiện tại
                calendar =Calendar.getInstance();
                if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                    calendar.add(Calendar.DATE, 1); // Đi đến thứ hai đầu tuần tiếp theo nếu đó là chủ nhật
                } else {
                    while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                        calendar.add(Calendar.DATE, -1); // Đi đến ngày thứ hai đầu tuần nếu hôm nay không phải chủ nhật
                    }
                }
                textViewPickDate.setText("Tuần từ: "+simpleDateFormat.format(calendar.getTime()));
                textViewDay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        });

        buttonSaveChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(checkBoxAllTime.isChecked()|checkBox6h.isChecked()||checkBox12h.isChecked()||checkBox17h.isChecked())){
                    if(!(checkBoxAllFloor.isChecked()||checkBoxFloor2.isChecked()||checkBoxFloor3.isChecked()||checkBoxFloor4.isChecked()||checkBoxFloor5.isChecked())){
                        Toasty.warning(getActivity(), "Vui lòng chọn khung giờ và tầng muốn xem!", Toast.LENGTH_SHORT, true).show();
                    } else Toasty.warning(getActivity(), "Vui lòng chọn khung giờ muốn xem!", Toast.LENGTH_SHORT, true).show();
                } else if(!(checkBoxAllFloor.isChecked()||checkBoxFloor2.isChecked()||checkBoxFloor3.isChecked()||checkBoxFloor4.isChecked()||checkBoxFloor5.isChecked())){
                    Toasty.warning(getActivity(), "Vui lòng chọn tầng muốn xem!", Toast.LENGTH_SHORT, true).show();
                } else dialog.dismiss();

            }
        });
        dialog.show();
    }

//    Cài đặt chỉ chọn được 1 checkbox
    private void CheckABox(final CheckBox checkBox){
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if (checkBox == checkBoxAllTime) {
                        checkBox6h.setChecked(false);
                        checkBox12h.setChecked(false);
                        checkBox17h.setChecked(false);
                    } else if (checkBox == checkBox6h) {
                        checkBoxAllTime.setChecked(false);
                        checkBox12h.setChecked(false);
                        checkBox17h.setChecked(false);
                    } else if (checkBox == checkBox12h) {
                        checkBoxAllTime.setChecked(false);
                        checkBox6h.setChecked(false);
                        checkBox17h.setChecked(false);
                    } else if (checkBox == checkBox17h) {
                        checkBoxAllTime.setChecked(false);
                        checkBox6h.setChecked(false);
                        checkBox12h.setChecked(false);
                    }
                }

            }
        });
    }

//    Cài đặt chỉ chọn checkbox phòng
    private void CheckRoom(final CheckBox checkBox){
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBoxFloor2.isChecked()&&checkBoxFloor3.isChecked()&&checkBoxFloor4.isChecked()&&checkBoxFloor5.isChecked()){
                    checkBoxFloor2.setChecked(false);
                    checkBoxFloor3.setChecked(false);
                    checkBoxFloor4.setChecked(false);
                    checkBoxFloor5.setChecked(false);
                    checkBoxAllFloor.setChecked(true);
                } else{
                         if(checkBox.equals(checkBoxAllFloor)){
                            if(checkBoxAllFloor.isChecked()){
                                checkBoxFloor2.setChecked(false);
                                checkBoxFloor3.setChecked(false);
                                checkBoxFloor4.setChecked(false);
                                checkBoxFloor5.setChecked(false);
                            }
                        }else if(checkBox.isChecked())
                            checkBoxAllFloor.setChecked(false);
                    }
            }
        });
    }

//    Hàm tìm ngày đầu tiên trong tuần
    private void Take_Date(){
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1); // Đi đến ngày thứ hai đầu tuần
        }
    }

//    Cài đặt chi tiết hiển thị cấu trúc bảng
    private void SetScheduleRoom(String week) throws NullPointerException{

//        Xóa các view đã có trên bảng
        header.removeAllViews();
        scrollablePart.removeAllViews();
        fixedColumn.removeAllViews();
        row = new TableRow(getActivity());

//        Lấy ngày để thêm vào thanh Header trên
        Cursor dataScheduleRoom = Connection_CTMS.dataBase.GetData("SELECT DISTINCT Ngay FROM tblLichPhong WHERE Tuan='"+week+"' ");

        row.setLayoutParams(wrapWrapTableRowParams);
        row.setGravity(Gravity.CENTER);

//        Thêm ngày sau khi cài đặt một số thuộc tính
        final ArrayList<String> arrayListDate = new ArrayList<>();           /* Tạo danh sách ngày chút còn so sánh*/
        while(dataScheduleRoom.moveToNext()){
            arrayListDate.add(dataScheduleRoom.getString(0));
        }

        for(int i=0;i<arrayListDate.size();i++){
            row.addView(makeTableRowWithText(arrayDay[i]+"\n"+arrayListDate.get(i), columnWidths, rowHeight));
        }

        header.addView(row);


        final ArrayList<Detail_Schedule_Room> arrayListDetail = new ArrayList<>();      /*Tạo một danh sách các ô trong bảng*/


//        Tạo chuỗi các phòng để lấy trong CSDL
        String listRoom ="";
        for(String a:arrayListRoom){
            listRoom = listRoom.concat(",'"+a+"'");
        }


        Cursor dataRoom = null;
//        Tùy chọn lấy dữ liệu từ SQLite theo các checkbox
        if(checkBoxAllTime.isChecked()){
            dataRoom = Connection_CTMS.dataBase.GetData("SELECT Phong,Ngay,TT FROM tblLichPhong WHERE Phong IN ("+listRoom.substring(1)+") AND Tuan='"+week+"'");
        }else {
            if(checkBox6h.isChecked()){
                dataRoom = Connection_CTMS.dataBase.GetData("SELECT Phong,Ngay,TT FROM tblChiTietLichPhong WHERE Phong IN ("+listRoom.substring(1)+") AND Gio='6h45 -> 9h00' AND Tuan='"+week+"'");
            } else {
                if(checkBox12h.isChecked()){
                    dataRoom = Connection_CTMS.dataBase.GetData("SELECT Phong,Ngay,TT FROM tblChiTietLichPhong WHERE Phong IN ("+listRoom.substring(1)+") AND Gio='12h45 -> 15h00' AND Tuan='"+week+"' ");
                } else {
                    dataRoom = Connection_CTMS.dataBase.GetData("SELECT Phong,Ngay,TT FROM tblChiTietLichPhong WHERE Phong IN ("+listRoom.substring(1)+") AND Gio='17h30 -> 20h10' AND Tuan='"+week+"' ");
                }
            }
        }


//        Lấy dữ liệu đổ vào list
        while(dataRoom.moveToNext()){
            arrayListDetail.add(new Detail_Schedule_Room(dataRoom.getString(0),dataRoom.getString(1),dataRoom.getString(2)));
        }


        try {
            int p = 0;
//        Thêm các dòng cho bảng
            for (int k = 0; k < arrayListRoom.size(); k++) {
                ImageView imageView;
                TextView headerTextView = makeTableRowWithText("Phòng " + arrayListRoom.get(k).substring(1), columnWidthsHeader, rowHeight);
                fixedColumn.addView(headerTextView);           /* Thêm ô phòng*/

//                Tạo các dòng mới và thuộc tính cho các ô thể hiện trạng thái
                row = new TableRow(getActivity());
                row.setLayoutParams(wrapWrapTableRowParams);
                row.setGravity(Gravity.CENTER);
                row.setBackgroundColor(Color.WHITE);

//            Thêm các ô
                for (int j = 0; j < 7; j++) {

//                    Nếu lấy từ bảng tblChiTietLichPhong theo yêu cầu để lấy giờ
//                            Phải xét dữ liệu có chứa ngày và phòng không, nếu ko thì cho là trống
                    if(arrayListDetail.get(p).get__Room().equals(arrayListRoom.get(k))&&arrayListDetail.get(p).get__Day().equals(arrayListDate.get(j))){
                        int l = 0;
                        switch (arrayListDetail.get(p).get__ST()) {     /*Nếu đúng cài đặt id của ảnh*/
                            case "HH":
                                l = R.drawable.hoc;
                                break;
                            case "Học":
                                l = R.drawable.hoc;
                                break;
                            case "TT":
                                l = R.drawable.thi;
                                break;
                            case "Thi":
                                l = R.drawable.thi;
                                break;
                            case "NN":
                                l = R.drawable.nghi;
                                break;
                            case "Nghỉ":
                                l = R.drawable.nghi;
                                break;
                            case "KK":
                                l = R.drawable.trong;
                                break;
                            case "":
                                l = R.drawable.trong;
                                break;
                            case "HT":
                                l = R.drawable.hoc_thi;
                                break;
                            case "HN":
                                l = R.drawable.hoc_nghi;
                                break;
                            case "HK":
                                l = R.drawable.hoc_trong;
                                break;
                            case "TH":
                                l = R.drawable.thi_hoc;
                                break;
                            case "TN":
                                l = R.drawable.thi_nghi;
                                break;
                            case "TK":
                                l = R.drawable.thi_trong;
                                break;
                            case "NT":
                                l = R.drawable.nghi_thi;
                                break;
                            case "NH":
                                l = R.drawable.nghi_hoc;
                                break;
                            case "NK":
                                l = R.drawable.nghi_trong;
                                break;
                            case "KH":
                                l = R.drawable.trong_hoc;
                                break;
                            case "KT":
                                l = R.drawable.trong_thi;
                                break;
                            case "KN":
                                l = R.drawable.trong_nghi;
                                break;
                        }

//                        Thêm các ô trạng thái với ảnh tương ứng
                        imageView = makeTableRowWithImage(l, columnWidthsImage, rowHeightImage);
                        row.addView(imageView);
                        row.setClickable(true);
                        imageView.setClickable(true);

//                    Cài đặt click cho mỗi ô trạng thái
                        final int finalP = p;
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DetailSchedule(arrayListDetail.get(finalP).get__Room(), arrayListDetail.get(finalP).get__Day());
                            }
                        });

                        if(p==arrayListDetail.size()-1){        /*Kiểm tra xem cái mảng này hết chưa*/
                            arrayListDetail.get(p).set__Room("100");        /*Cài đặt phần tử cuối bằng số phòng không tồn tại để phía sau trống hết*/
                        } else p++;                  /*Chuyển đến vị trí tiếp theo trong dữ liệu nếu mảng chi tiết lịch vẫn còn*/

                    }else {
                        //                    Thêm các ô trạng thái với ảnh tương ứng
                        imageView = makeTableRowWithImage(R.drawable.trong, columnWidthsImage, rowHeightImage);
                        row.addView(imageView);
                        row.setClickable(true);
                        imageView.setClickable(true);

//                    Cài đặt click cho mỗi ô trạng thái
                        final int finalK = k;
                        final int finalJ = j;
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DetailSchedule(arrayListRoom.get(finalK), arrayListDate.get(finalJ));
                            }
                        });
                        /*Giữ nguyên vị trí này*/
                    }
                }
                scrollablePart.addView(row);
            }
        }catch (Exception e){
            Toasty.error(getActivity(), "Đã xảy ra lỗi!", Toast.LENGTH_LONG, true).show();
            Log.d("aannvv","-----"+e.toString()+"------");
        }
    }


//    Cài đặt chi tiết lịch phòng của từng ô
    private void DetailSchedule(String Room, String Date){

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.detail_schedule_room);
        dialog.setCanceledOnTouchOutside(false);

        TextView textViewDate = (TextView) dialog.findViewById(R.id.textViewname);

        textViewDate.setText("Phòng "+Room+" - Ngày "+Date+"");


        Cursor dataDetailRoom = null;
//        Tùy chọn lấy dữ liệu từ SQLite theo các checkbox
        if(checkBoxAllTime.isChecked()){
            dataDetailRoom = Connection_CTMS.dataBase.GetData("SELECT * FROM tblChiTietLichPhong WHERE Ngay='"+Date+"' AND Phong='"+Room+"' ");
        }else {
            if(checkBox6h.isChecked()){
                dataDetailRoom = Connection_CTMS.dataBase.GetData("SELECT * FROM tblChiTietLichPhong WHERE Ngay='"+Date+"' AND Phong='"+Room+"' AND Gio='6h45 -> 9h00' ");
            } else {
                if(checkBox12h.isChecked()){
                    dataDetailRoom = Connection_CTMS.dataBase.GetData("SELECT * FROM tblChiTietLichPhong WHERE Ngay='"+Date+"' AND Phong='"+Room+"' AND Gio='12h45 -> 15h00' ");
                } else {
                    dataDetailRoom = Connection_CTMS.dataBase.GetData("SELECT * FROM tblChiTietLichPhong WHERE Ngay='"+Date+"' AND Phong='"+Room+"' AND Gio='17h30 -> 20h10' ");
                }
            }
        }





        while(dataDetailRoom.moveToNext()){
//            if(dataDetailRoom.getString(1).equals(Date)&&dataDetailRoom.getString(2).equals(Room)) {
            Drawable image = null;
            int h, w;
            switch (dataDetailRoom.getString(4)){
                    case "6h45 -> 9h00": TextView textViewTeacher1 = (TextView) dialog.findViewById(R.id.textViewTeacher1);
                                         TextView textViewSub1 = (TextView) dialog.findViewById(R.id.textViewSub1);
                                         TextView textViewStatus1 = (TextView) dialog.findViewById(R.id.textViewClass);
                                         TextView textViewSpace1 = (TextView) dialog.findViewById(R.id.textViewSpace1);
                                         textViewTeacher1.setText(dataDetailRoom.getString(6));
                                         textViewSub1.setText(dataDetailRoom.getString(5));
                                         textViewStatus1.setText(dataDetailRoom.getString(7));
                                         textViewStatus1.setBackgroundResource(R.drawable.stroke_textview);
                                         switch (dataDetailRoom.getString(7)){
                                             case "Học": image = getResources().getDrawable( R.drawable.icon_button_green ); break;
                                             case "Thi": image = getResources().getDrawable( R.drawable.icon_button_red ); break;
                                             case "Nghỉ": image = getResources().getDrawable( R.drawable.icon_button_grey ); break;
                                         }
                                         h = image.getIntrinsicHeight();
                                         w = image.getIntrinsicWidth();
                                         image.setBounds( 0, 0, w, h );
                                         textViewStatus1.setCompoundDrawables(null,null,image,null);
                                         textViewSpace1.setVisibility(View.GONE);
                                         break;
                    case "9h10 -> 11h25": TextView textViewTeacher2 = (TextView) dialog.findViewById(R.id.textViewTeacher2);
                                         TextView textViewSub2 = (TextView) dialog.findViewById(R.id.textViewSub2);
                                         TextView textViewStatus2 = (TextView) dialog.findViewById(R.id.textViewStatus2);
                                         TextView textViewSpace2 = (TextView) dialog.findViewById(R.id.textViewSpace2);
                                         textViewTeacher2.setText(dataDetailRoom.getString(6));
                                         textViewSub2.setText(dataDetailRoom.getString(5));
                                         textViewStatus2.setText(dataDetailRoom.getString(7));
                                         textViewStatus2.setBackgroundResource(R.drawable.stroke_textview);
                                         switch (dataDetailRoom.getString(7)){
                                             case "Học": image = getResources().getDrawable( R.drawable.icon_button_green ); break;
                                             case "Thi": image = getResources().getDrawable( R.drawable.icon_button_red ); break;
                                             case "Nghỉ": image = getResources().getDrawable( R.drawable.icon_button_grey ); break;
                                         }
                                         h = image.getIntrinsicHeight();
                                         w = image.getIntrinsicWidth();
                                         image.setBounds( 0, 0, w, h );
                                         textViewStatus2.setCompoundDrawables(null,null,image,null);
                                         textViewSpace2.setVisibility(View.GONE);
                                         break;
                    case "12h45 -> 15h00": TextView textViewTeacher3 = (TextView) dialog.findViewById(R.id.textViewTeacher3);
                                         TextView textViewSub3 = (TextView) dialog.findViewById(R.id.textViewSub3);
                                         TextView textViewStatus3 = (TextView) dialog.findViewById(R.id.textViewStatus3);
                                         TextView textViewSpace3 = (TextView) dialog.findViewById(R.id.textViewSpace3);
                                         textViewTeacher3.setText(dataDetailRoom.getString(6));
                                         textViewSub3.setText(dataDetailRoom.getString(5));
                                         textViewStatus3.setText(dataDetailRoom.getString(7));
                                         textViewStatus3.setBackgroundResource(R.drawable.stroke_textview);
                                         switch (dataDetailRoom.getString(7)){
                                             case "Học": image = getResources().getDrawable( R.drawable.icon_button_green ); break;
                                             case "Thi": image = getResources().getDrawable( R.drawable.icon_button_red ); break;
                                             case "Nghỉ": image = getResources().getDrawable( R.drawable.icon_button_grey ); break;
                                         }
                                         h = image.getIntrinsicHeight();
                                         w = image.getIntrinsicWidth();
                                         image.setBounds( 0, 0, w, h );
                                         textViewStatus3.setCompoundDrawables(null,null,image,null);
                                         textViewSpace3.setVisibility(View.GONE);
                                         break;
                    case "15h10 -> 17h25": TextView textViewTeacher4 = (TextView) dialog.findViewById(R.id.textViewTeacher4);
                                         TextView textViewSub4 = (TextView) dialog.findViewById(R.id.textViewSub4);
                                         TextView textViewStatus4 = (TextView) dialog.findViewById(R.id.textViewStatus4);
                                         TextView textViewSpace4 = (TextView) dialog.findViewById(R.id.textViewSpace4);
                                         textViewTeacher4.setText(dataDetailRoom.getString(6));
                                         textViewSub4.setText(dataDetailRoom.getString(5));
                                         textViewStatus4.setText(dataDetailRoom.getString(7));
                                         textViewStatus4.setBackgroundResource(R.drawable.stroke_textview);
                                         switch (dataDetailRoom.getString(7)){
                                             case "Học": image = getResources().getDrawable( R.drawable.icon_button_green ); break;
                                             case "Thi": image = getResources().getDrawable( R.drawable.icon_button_red ); break;
                                             case "Nghỉ": image = getResources().getDrawable( R.drawable.icon_button_grey ); break;
                                         }
                                         h = image.getIntrinsicHeight();
                                         w = image.getIntrinsicWidth();
                                         image.setBounds( 0, 0, w, h );
                                         textViewStatus4.setCompoundDrawables(null,null,image,null);
                                         textViewSpace4.setVisibility(View.GONE);
                                         break;
                    case "17h30 -> 20h10": TextView textViewTeacher5 = (TextView) dialog.findViewById(R.id.textViewTeacher5);
                                         TextView textViewSub5 = (TextView) dialog.findViewById(R.id.textViewSub5);
                                         TextView textViewStatus5 = (TextView) dialog.findViewById(R.id.textViewStatus5);
                                         TextView textViewSpace5 = (TextView) dialog.findViewById(R.id.textViewSpace5);
                                         textViewTeacher5.setText(dataDetailRoom.getString(6));
                                         textViewSub5.setText(dataDetailRoom.getString(5));
                                         textViewStatus5.setText(dataDetailRoom.getString(7));
                                         textViewStatus5.setBackgroundResource(R.drawable.stroke_textview);
                                         switch (dataDetailRoom.getString(7)){
                                             case "Học": image = getResources().getDrawable( R.drawable.icon_button_green ); break;
                                             case "Thi": image = getResources().getDrawable( R.drawable.icon_button_red ); break;
                                             case "Nghỉ": image = getResources().getDrawable( R.drawable.icon_button_grey ); break;
                                         }
                                         h = image.getIntrinsicHeight();
                                         w = image.getIntrinsicWidth();
                                         image.setBounds( 0, 0, w, h );
                                         textViewStatus5.setCompoundDrawables(null,null,image,null);
                                         textViewSpace5.setVisibility(View.GONE);
                                         break;
                }
        }

        dialog.show();
    }

    //    Cài đặt chi tiết hiển thị cấu trúc bảng khi chưa click lọc lịch
    private void SetScheduleRoomFirst(String week) throws NullPointerException{

//        Xóa các view đã có trên bảng
        header.removeAllViews();
        scrollablePart.removeAllViews();
        fixedColumn.removeAllViews();

//        Lấy ngày để thêm vào thanh Header trên
        Cursor dataScheduleRoom = Connection_CTMS.dataBase.GetData("SELECT DISTINCT Ngay FROM tblLichPhong WHERE Tuan='"+week+"'");

        row.setLayoutParams(wrapWrapTableRowParams);
        row.setGravity(Gravity.CENTER);

//        Thêm ngày sau khi cài đặt một số thuộc tính
        int i=0;
        while(dataScheduleRoom.moveToNext()){
            row.addView(makeTableRowWithText(arrayDay[i]+"\n"+dataScheduleRoom.getString(0), columnWidths, rowHeight));
            i++;
        }
        header.addView(row);


        final ArrayList<Detail_Schedule_Room> arrayListDetail = new ArrayList<>();      /*Tạo một danh sách các ô trong bảng*/

        Cursor dataRoomFirst = Connection_CTMS.dataBase.GetData("SELECT * FROM tblLichPhong WHERE Tuan='"+week+"'");


//        Lấy dữ liệu đổ vào list
        while(dataRoomFirst.moveToNext()){
            arrayListDetail.add(new Detail_Schedule_Room(dataRoomFirst.getString(3),dataRoomFirst.getString(2),dataRoomFirst.getString(4)));
        }


        try {
            int p = 0;
//        Thêm các dòng cho bảng
            for (int k = 0; k < arrayListRoom.size(); k++) {
                ImageView imageView;
                TextView headerTextView = makeTableRowWithText("Phòng " + arrayListRoom.get(k), columnWidthsHeader, rowHeight);
                fixedColumn.addView(headerTextView);           /* Thêm ô phòng*/

//                Tạo các dòng mới và thuộc tính cho các ô thể hiện trạng thái
                row = new TableRow(getActivity());
                row.setLayoutParams(wrapWrapTableRowParams);
                row.setGravity(Gravity.CENTER);
                row.setBackgroundColor(Color.WHITE);

//            Thêm các ô
                for (int j = 1; j <= 7; j++) {
                    int l = 0;
//
                    switch (arrayListDetail.get(p).get__ST()) {
                        case "HH":
                            l = R.drawable.hoc;
                            break;
                        case "TT":
                            l = R.drawable.thi;
                            break;
                        case "NN":
                            l = R.drawable.nghi;
                            break;
                        case "KK":
                            l = R.drawable.trong;
                            break;
                        case "HT":
                            l = R.drawable.hoc_thi;
                            break;
                        case "HN":
                            l = R.drawable.hoc_nghi;
                            break;
                        case "HK":
                            l = R.drawable.hoc_trong;
                            break;
                        case "TH":
                            l = R.drawable.thi_hoc;
                            break;
                        case "TN":
                            l = R.drawable.thi_nghi;
                            break;
                        case "TK":
                            l = R.drawable.thi_trong;
                            break;
                        case "NT":
                            l = R.drawable.nghi_thi;
                            break;
                        case "NH":
                            l = R.drawable.nghi_hoc;
                            break;
                        case "NK":
                            l = R.drawable.nghi_trong;
                            break;
                        case "KH":
                            l = R.drawable.trong_hoc;
                            break;
                        case "KT":
                            l = R.drawable.trong_thi;
                            break;
                        case "KN":
                            l = R.drawable.trong_nghi;
                            break;
                    }

//                    Thêm các ô trạng thái với ảnh tương ứng
                    imageView = makeTableRowWithImage(l, columnWidthsImage, rowHeightImage);
                    row.addView(imageView);
                    row.setClickable(true);
                    imageView.setClickable(true);

//                    Cài đặt click cho mỗi ô trạng thái
                    final int finalP = p;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DetailScheduleFisrt(arrayListDetail.get(finalP).get__Room(), arrayListDetail.get(finalP).get__Day());
                        }
                    });
                    p++;

                }
                scrollablePart.addView(row);
            }
        }catch (Exception e){
            Toasty.error(getActivity(), "Đã xảy ra lỗi!", Toast.LENGTH_LONG, true).show();

        }
    }


    //    Cài đặt chi tiết lịch phòng của từng ô
    private void DetailScheduleFisrt(String Room, String Date){

        final Dialog dialogFirst = new Dialog(getActivity());
        dialogFirst.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFirst.setContentView(R.layout.detail_schedule_room);
        dialogFirst.setCanceledOnTouchOutside(false);

        TextView textViewDate = (TextView) dialogFirst.findViewById(R.id.textViewname);

        textViewDate.setText("Phòng "+Room+" - Ngày "+Date+"");


        Cursor dataDetailRoom = Connection_CTMS.dataBase.GetData("SELECT * FROM tblChiTietLichPhong WHERE Ngay='"+Date+"' AND Phong='"+Room+"' ");

        while(dataDetailRoom.moveToNext()){
            Drawable image = null;
            int h, w;
            switch (dataDetailRoom.getString(4)){
                case "6h45 -> 9h00": TextView textViewTeacher1 = (TextView) dialogFirst.findViewById(R.id.textViewTeacher1);
                    TextView textViewSub1 = (TextView) dialogFirst.findViewById(R.id.textViewSub1);
                    TextView textViewStatus1 = (TextView) dialogFirst.findViewById(R.id.textViewClass);
                    TextView textViewSpace1 = (TextView) dialogFirst.findViewById(R.id.textViewSpace1);
                    textViewTeacher1.setText(dataDetailRoom.getString(6));
                    textViewSub1.setText(dataDetailRoom.getString(5));
                    textViewStatus1.setText(dataDetailRoom.getString(7));
                    textViewStatus1.setBackgroundResource(R.drawable.stroke_textview);
                    switch (dataDetailRoom.getString(7)){
                        case "Học": image = getResources().getDrawable( R.drawable.icon_button_green ); break;
                        case "Thi": image = getResources().getDrawable( R.drawable.icon_button_red ); break;
                        case "Nghỉ": image = getResources().getDrawable( R.drawable.icon_button_grey ); break;
                    }
                    h = image.getIntrinsicHeight();
                    w = image.getIntrinsicWidth();
                    image.setBounds( 0, 0, w, h );
                    textViewStatus1.setCompoundDrawables(null,null,image,null);
                    textViewSpace1.setVisibility(View.GONE);
                    break;
                case "9h10 -> 11h25": TextView textViewTeacher2 = (TextView) dialogFirst.findViewById(R.id.textViewTeacher2);
                    TextView textViewSub2 = (TextView) dialogFirst.findViewById(R.id.textViewSub2);
                    TextView textViewStatus2 = (TextView) dialogFirst.findViewById(R.id.textViewStatus2);
                    TextView textViewSpace2 = (TextView) dialogFirst.findViewById(R.id.textViewSpace2);
                    textViewTeacher2.setText(dataDetailRoom.getString(6));
                    textViewSub2.setText(dataDetailRoom.getString(5));
                    textViewStatus2.setText(dataDetailRoom.getString(7));
                    textViewStatus2.setBackgroundResource(R.drawable.stroke_textview);
                    switch (dataDetailRoom.getString(7)){
                        case "Học": image = getResources().getDrawable( R.drawable.icon_button_green ); break;
                        case "Thi": image = getResources().getDrawable( R.drawable.icon_button_red ); break;
                        case "Nghỉ": image = getResources().getDrawable( R.drawable.icon_button_grey ); break;
                    }
                    h = image.getIntrinsicHeight();
                    w = image.getIntrinsicWidth();
                    image.setBounds( 0, 0, w, h );
                    textViewStatus2.setCompoundDrawables(null,null,image,null);
                    textViewSpace2.setVisibility(View.GONE);
                    break;
                case "12h45 -> 15h00": TextView textViewTeacher3 = (TextView) dialogFirst.findViewById(R.id.textViewTeacher3);
                    TextView textViewSub3 = (TextView) dialogFirst.findViewById(R.id.textViewSub3);
                    TextView textViewStatus3 = (TextView) dialogFirst.findViewById(R.id.textViewStatus3);
                    TextView textViewSpace3 = (TextView) dialogFirst.findViewById(R.id.textViewSpace3);
                    textViewTeacher3.setText(dataDetailRoom.getString(6));
                    textViewSub3.setText(dataDetailRoom.getString(5));
                    textViewStatus3.setText(dataDetailRoom.getString(7));
                    textViewStatus3.setBackgroundResource(R.drawable.stroke_textview);
                    switch (dataDetailRoom.getString(7)){
                        case "Học": image = getResources().getDrawable( R.drawable.icon_button_green ); break;
                        case "Thi": image = getResources().getDrawable( R.drawable.icon_button_red ); break;
                        case "Nghỉ": image = getResources().getDrawable( R.drawable.icon_button_grey ); break;
                    }
                    h = image.getIntrinsicHeight();
                    w = image.getIntrinsicWidth();
                    image.setBounds( 0, 0, w, h );
                    textViewStatus3.setCompoundDrawables(null,null,image,null);
                    textViewSpace3.setVisibility(View.GONE);
                    break;
                case "15h10 -> 17h25": TextView textViewTeacher4 = (TextView) dialogFirst.findViewById(R.id.textViewTeacher4);
                    TextView textViewSub4 = (TextView) dialogFirst.findViewById(R.id.textViewSub4);
                    TextView textViewStatus4 = (TextView) dialogFirst.findViewById(R.id.textViewStatus4);
                    TextView textViewSpace4 = (TextView) dialogFirst.findViewById(R.id.textViewSpace4);
                    textViewTeacher4.setText(dataDetailRoom.getString(6));
                    textViewSub4.setText(dataDetailRoom.getString(5));
                    textViewStatus4.setText(dataDetailRoom.getString(7));
                    textViewStatus4.setBackgroundResource(R.drawable.stroke_textview);
                    switch (dataDetailRoom.getString(7)){
                        case "Học": image = getResources().getDrawable( R.drawable.icon_button_green ); break;
                        case "Thi": image = getResources().getDrawable( R.drawable.icon_button_red ); break;
                        case "Nghỉ": image = getResources().getDrawable( R.drawable.icon_button_grey ); break;
                    }
                    h = image.getIntrinsicHeight();
                    w = image.getIntrinsicWidth();
                    image.setBounds( 0, 0, w, h );
                    textViewStatus4.setCompoundDrawables(null,null,image,null);
                    textViewSpace4.setVisibility(View.GONE);
                    break;
                case "17h30 -> 20h10": TextView textViewTeacher5 = (TextView) dialogFirst.findViewById(R.id.textViewTeacher5);
                    TextView textViewSub5 = (TextView) dialogFirst.findViewById(R.id.textViewSub5);
                    TextView textViewStatus5 = (TextView) dialogFirst.findViewById(R.id.textViewStatus5);
                    TextView textViewSpace5 = (TextView) dialogFirst.findViewById(R.id.textViewSpace5);
                    textViewTeacher5.setText(dataDetailRoom.getString(6));
                    textViewSub5.setText(dataDetailRoom.getString(5));
                    textViewStatus5.setText(dataDetailRoom.getString(7));
                    textViewStatus5.setBackgroundResource(R.drawable.stroke_textview);
                    switch (dataDetailRoom.getString(7)){
                        case "Học": image = getResources().getDrawable( R.drawable.icon_button_green ); break;
                        case "Thi": image = getResources().getDrawable( R.drawable.icon_button_red ); break;
                        case "Nghỉ": image = getResources().getDrawable( R.drawable.icon_button_grey ); break;
                    }
                    h = image.getIntrinsicHeight();
                    w = image.getIntrinsicWidth();
                    image.setBounds( 0, 0, w, h );
                    textViewStatus5.setCompoundDrawables(null,null,image,null);
                    textViewSpace5.setVisibility(View.GONE);
                    break;
            }
        }

        dialogFirst.show();
    }

//    Class lấy lịch phòng
    private class Take_Schedule_Room extends AsyncTask<String,String,String> {

        Activity context;
        SharedPreferences.Editor editor = MainActivity.sharedPreferencesUser.edit();

        public Take_Schedule_Room(Activity ctx)
        {
            context=ctx;
        }

        @Override
        protected String doInBackground(final String... date) {

            Cursor dataScheduleRoomBBBBB = Connection_CTMS.dataBase.GetData("SELECT * FROM tblLichPhong WHERE Tuan='"+date[0]+"'");
            if(dataScheduleRoomBBBBB.getCount()==0){
                final Connection_CTMS connectionCtms = new Connection_CTMS(context);
                String status;
                try {
                    status = connectionCtms.Login(MainActivity.sharedPreferencesUser.getString("Email",""),MainActivity.sharedPreferencesUser.getString("Pass",""));
                } catch (Exception e) {
                    connectionCtms.Logout();
                    e.printStackTrace();
                    return "error";
                }

                if(status.equals("success")){

                    try {

//                    Tạo hai luồng lấy dữ liệu cho nhanh
                        Thread ThreadScheduleRoomPage1 = new Thread() {
                            public void run() {
                                try {
                                    Connection_CTMS.DataScheduleRoomPage = connectionCtms.GetPageContent("ScheduleRoomPage");         /*Luồng lấy lịch học*/
                                    if(!(Connection_CTMS.DataScheduleRoomPage.equals("time up")||Connection_CTMS.DataScheduleRoomPage.equals("error"))){
                                        connectionCtms.Take_Schedule_Room_Next(date[0]);
                                    }
                                } catch (Exception e) {                                                      /*Xét các giá trị để lưu trạng thái đã cập nhật hay chưa*/
                                    e.printStackTrace();
                                }
                            }
                        };


                        ThreadScheduleRoomPage1.start();

                        ThreadScheduleRoomPage1.join();

                        if(connectionCtms.Logout().equals("success")){
                            editor.putBoolean("Logout",true);
                            editor.commit();
                        }else {
                            editor.putBoolean("Logout",false);
                            editor.commit();
                        }
                    }
                    catch (Exception e) {
                        connectionCtms.Logout();
                        e.printStackTrace();
                    }
                }
                return status;
            }
            return "success";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!(s.equals("success"))) Toasty.error(context, "Lỗi xảy ra!", Toast.LENGTH_LONG, true).show();
        }
    }
}
