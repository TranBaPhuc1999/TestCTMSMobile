package com.dmt.tranbaphuc1999.ctms;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class Fragment_Tabbar_Schedule_Class extends Fragment {

    ListView listViewStudyWeek;
    ArrayList<Schedule_Class> arrayListSchedule_Class;
    ArrayList<Adapter_Schedule_Class_Day> arrayListAdapter_Schedule_Day;
    Adapter_Schedule_Class_Week adapter_schedule_class_week;
    static Calendar calendar;
    SimpleDateFormat simpleDateFormatTexView,simpleDateFormatList;
    TextView textViewTime;
    Button buttonShowSchedule;
    String Time;
    View view;
    ProgressBar progressBar;
    ArrayList<Thu_Ngay_Schedule_Class> arrayListThu_Ngay;
    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tabbar_schedule_class,container,false);
        Init();

//        Cài đặt độ mờ, và ẩn progressBar đi, Méo biết làm chuyên nghiệp nên làm thủ công ^^, Vô hiệu hóa nút xem lịch khi mới vào luôn
        progressBar.setVisibility(View.GONE);
        progressBar.setBackgroundColor(0xbf000000);
        buttonShowSchedule.setEnabled(false);






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

        Time = simpleDateFormatList.format(calendar.getTime()); /*Format để so sánh trong thêm dữ liệu vào list*/

        textViewTime.setText("Tuần từ: "+simpleDateFormatTexView.format(calendar.getTime()));
        textViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickDates();
            }
        });


//        Click nút xem lịch
        Show_Schedule();

        AddArraySchedule(Time);

        adapter_schedule_class_week = new Adapter_Schedule_Class_Week(getActivity(),R.layout.row_schedule_class_week,arrayListAdapter_Schedule_Day,arrayListThu_Ngay);
        listViewStudyWeek.setAdapter(adapter_schedule_class_week);


        return view;
    }


//    Ánh xạ
    private void Init() {
        textViewTime = (TextView) view.findViewById(R.id.textViewTime);
        simpleDateFormatTexView = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormatList = new SimpleDateFormat("yyyy-MM-dd");
        listViewStudyWeek = (ListView) view.findViewById(R.id.listViewWeek_Class);
        arrayListAdapter_Schedule_Day = new ArrayList<>();
        arrayListSchedule_Class = new ArrayList<>();
        arrayListThu_Ngay = new ArrayList<>();
        buttonShowSchedule = (Button) view.findViewById(R.id.buttonShowSchedule);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarLoadingClass);
    }


//    Click nút xem lịch
    private void Show_Schedule(){
        buttonShowSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                buttonShowSchedule.setEnabled(false);               /*Vô hiệu hóa nút xem lịch tạm thời*/
                Take_Date();                                        /*Lấy ngày đầu tiên của tuần vừa chọn*/
                Time = simpleDateFormatList.format(calendar.getTime());
                String stt = "";
                try {
                    stt = new Take_Schedule_Class(getActivity()).execute(Time).get();           /*Lấy lịch*/
                }catch (Exception e) {
                    Toasty.error(getActivity(), "Lỗi cập nhật xảy ra!", Toast.LENGTH_LONG, true).show();
                }
                if(stt.equals("success")) {
                    AddArraySchedule(Time);        /* Cập nhật thành công mới cho phép đổ ra view*/
                    textViewTime.setText("Tuần từ: "+simpleDateFormatTexView.format(calendar.getTime()));
                }
                adapter_schedule_class_week.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

//    Thêm dữ liệu vào list lịch học
    private void AddArraySchedule(String timeWeek){
        arrayListAdapter_Schedule_Day.clear();
        arrayListThu_Ngay.clear();
//        Tạo mảng các thứ trong tuần để duyệt
        String[] arr = {"Thứ Hai","Thứ Ba","Thứ Tư","Thứ Năm","Thứ Sáu","Thứ Bảy","Chủ nhật"};
        ArrayList<String> day = new ArrayList<>(Arrays.asList(arr));

        for(int i=0;i<day.size();i++){
            arrayListSchedule_Class = new ArrayList<>();
            Cursor dataScheduleClass = Connection_CTMS.dataBase.GetData("SELECT * FROM tblLichHoc WHERE Tuan='"+timeWeek+"' AND Thu='"+day.get(i)+"'");

            String thu="", ngay="";
            while(dataScheduleClass.moveToNext()){
                thu = dataScheduleClass.getString(2);
                ngay = dataScheduleClass.getString(3);
                String GioBD = dataScheduleClass.getString(4);
                String GioKT = dataScheduleClass.getString(5);
                String Phong = dataScheduleClass.getString(6);
                String GV = dataScheduleClass.getString(8);
                String Mon = dataScheduleClass.getString(7);
                String TT = dataScheduleClass.getString(9);
                int icon = 0, background =0;
                if(TT.equals("Học")) {
                    icon=R.drawable.icon_button_green;
                    background=R.drawable.color_hoc;
                }
                else if(TT.equals("Nghỉ")){
                    icon=R.drawable.icon_button_grey;
                    background=R.drawable.color_nghi;
                }
                    else if(TT.equals("Thi")){
                    icon=R.drawable.icon_button_red;
                    background=R.drawable.color_thi;
                }

                arrayListSchedule_Class.add(new Schedule_Class(GioBD,GioKT,Phong,GV,Mon,icon,background));
            }
            if(!(arrayListSchedule_Class.isEmpty())){
                arrayListThu_Ngay.add(new Thu_Ngay_Schedule_Class(thu,ngay));
                arrayListAdapter_Schedule_Day.add(new Adapter_Schedule_Class_Day(getActivity(),R.layout.row_schedule_class_day,arrayListSchedule_Class));
            }
        }
    }

//    Dialog Lịch hiện lên
    private void PickDates(){
        buttonShowSchedule.setEnabled(true);    /*Nút xem lịch hoạt động trở lại*/
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i,i1,i2);
                        textViewTime.setText("Tuần từ: "+simpleDateFormatTexView.format(calendar.getTime()));
                    }
                },year,month,day);
        datePickerDialog.show();
    }


//    Hàm tìm ngày đầu tiên trong tuần
    private void Take_Date(){
            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                calendar.add(Calendar.DATE, -1); // Đi đến ngày thứ hai đầu tuần
            }
    }


//    Class lấy lịch học lớp
    private class Take_Schedule_Class extends AsyncTask<String,String,String>{

        Activity context;
        SharedPreferences.Editor editor = MainActivity.sharedPreferencesUser.edit();

        public Take_Schedule_Class(Activity ctx)
        {
            context=ctx;
        }

        @Override
        protected String doInBackground(final String... date) {

            Cursor dataScheduleClassAAAAA = Connection_CTMS.dataBase.GetData("SELECT * FROM tblLichHoc WHERE Tuan='"+date[0]+"'");
            if(dataScheduleClassAAAAA.getCount()==0){
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

                    Thread ThreadScheduleClassPage1 = new Thread() {
                        public void run() {
                            try {
                                Connection_CTMS.DataScheduleClassPage = connectionCtms.GetPageContent("ScheduleClassPage");         /*Luồng lấy lịch học*/
                                if(!(Connection_CTMS.DataScheduleClassPage.equals("time up")||Connection_CTMS.DataScheduleClassPage.equals("error"))){
                                    connectionCtms.Take_Schedule_Class_Next(date[0]);
                                }
                            } catch (Exception e) {                                                      /*Xét các giá trị để lưu trạng thái đã cập nhật hay chưa*/
                                e.printStackTrace();
                            }
                        }
                    };


                    ThreadScheduleClassPage1.start();

                    ThreadScheduleClassPage1.join();

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
