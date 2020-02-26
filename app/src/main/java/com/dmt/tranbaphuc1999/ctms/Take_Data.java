package com.dmt.tranbaphuc1999.ctms;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class Take_Data extends AsyncTask<String,String,String> {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar;
    Activity context;
    SharedPreferences.Editor editor = MainActivity.sharedPreferencesUser.edit();

    public Take_Data(Activity ctx)
    {
        context=ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... Infor) {

        final Connection_CTMS connectionCtms = new Connection_CTMS(context);
        String status = "";
        try {
            status = connectionCtms.Login(Infor[0],Infor[1]);
        } catch (Exception e) {
            connectionCtms.Logout();
            e.printStackTrace();
        }

        if(status.equals("success")){


            //        Tìm ngày đầu tiên trong tuần
            calendar = Calendar.getInstance();
            if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                calendar.add(Calendar.DATE, 1); // Đi đến thứ hai đầu tuần tiếp theo nếu đó là chủ nhật
            } else {
                while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                    calendar.add(Calendar.DATE, -1); // Đi đến ngày thứ hai đầu tuần nếu hôm nay không phải chủ nhật
                }
            }

            calendar.add(Calendar.DATE, 7);

            final String Time = simpleDateFormat.format(calendar.getTime()); /*Format để so sánh*/


            try {

//                    Tạo hai luồng lấy dữ liệu cho nhanh
                Thread ThreadScheduleClassPage = new Thread() {
                    public void run() {
                        try {

                            Connection_CTMS.DataScheduleClassPage = connectionCtms.GetPageContent("ScheduleClassPage");         /*Luồng lấy lịch học*/
                            if(!(Connection_CTMS.DataScheduleClassPage.equals("time up")||Connection_CTMS.DataScheduleClassPage.equals("error"))){
                                connectionCtms.ReadScheduleClass(Connection_CTMS.DataScheduleClassPage," ");

                                Thread ThreadScheduleClassPageNext = new Thread() {
                                    public void run() {
                                        connectionCtms.Take_Schedule_Class_Next(Time);    /*Luồng lấy dữ liệu tuần sau*/
                                    }
                                };

                                ThreadScheduleClassPageNext.start();


                                editor.putBoolean("DataScheduleClassPage",true);
                                editor.commit();
                            }else {
                                editor.putBoolean("DataScheduleClassPage",false);
                                editor.commit();
                            }
                        } catch (IOException e) {                                                      /*Xét các giá trị để lưu trạng thái đã cập nhật hay chưa*/
                            editor.putBoolean("DataScheduleClassPage",false);
                            editor.commit();
                            e.printStackTrace();
                        }
                    }
                };

                Thread ThreadScheduleRoomPage = new Thread() {
                    public void run() {
                        try {

                            Connection_CTMS.DataScheduleRoomPage = connectionCtms.GetPageContent("ScheduleRoomPage");      /*Luồng lấy lịch thời khóa biểu phòng*/
                                connectionCtms.Take_teacher_sub(9);
                            if(!(Connection_CTMS.DataScheduleRoomPage.equals("time up")||Connection_CTMS.DataScheduleRoomPage.equals("error"))){
                                connectionCtms.ReadScheduleRoom(Connection_CTMS.DataScheduleRoomPage," ");

                                Thread ThreadScheduleRoomPageNext = new Thread() {
                                    public void run() {
                                        connectionCtms.Take_Schedule_Room_Next(Time);    /*Luồng lấy dữ liệu tuần sau*/
                                    }
                                };

                                ThreadScheduleRoomPageNext.start();


                                editor.putBoolean("DataScheduleRoomPage",true);
                                editor.commit();
                            }
                            else {
                                editor.putBoolean("DataScheduleRoomPage",false);
                                editor.commit();
                            }
                        } catch (IOException e) {                                                   /*Xét các giá trị để lưu trạng thái đã cập nhật hay chưa*/
                            editor.putBoolean("DataScheduleRoomPage",false);
                            editor.commit();
                            e.printStackTrace();
                        }
                    }
                };


                Thread ThreadNews = new Thread() {
                    public void run() {
                        try {

                            Connection_CTMS.DataNews = connectionCtms.GetPageContent("News");      /*Luồng lấy bản tin*/
                            if(!(Connection_CTMS.DataScheduleRoomPage.equals("time up")||Connection_CTMS.DataScheduleRoomPage.equals("error"))){
                                connectionCtms.Read_News(Connection_CTMS.DataNews);

                                editor.putBoolean("News",true);
                                editor.commit();
                            }
                            else {
                                editor.putBoolean("News",false);
                                editor.commit();
                            }
                        } catch (IOException e) {                                                   /*Xét các giá trị để lưu trạng thái đã cập nhật hay chưa*/
                            editor.putBoolean("news",false);
                            editor.commit();
                            e.printStackTrace();
                        }
                    }
                };


                ThreadScheduleRoomPage.start();
                ThreadScheduleClassPage.start();
                ThreadNews.start();

                ThreadScheduleRoomPage.join();
                ThreadScheduleClassPage.join();
                ThreadNews.join();

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

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}