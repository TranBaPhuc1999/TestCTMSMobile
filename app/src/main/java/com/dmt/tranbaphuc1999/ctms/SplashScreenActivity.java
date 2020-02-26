package com.dmt.tranbaphuc1999.ctms;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.sharedPreferencesUser = getSharedPreferences("Data",MODE_PRIVATE);
        SharedPreferences.Editor editor =  MainActivity.sharedPreferencesUser.edit();

        if(MainActivity.sharedPreferencesUser.getBoolean("ExitApp",false)){

            editor.putBoolean("ExitApp",false);                 /* Ghi nhớ thoát app*/

            if(MainActivity.sharedPreferencesUser.getBoolean("CheckBoxRemember", false)==false){
                editor.remove("Email");
                editor.remove("Pass");
                editor.remove("CheckBoxRemember");
            }

            editor.commit();

            finish();
        }
            if (isConnected()) {
                if (MainActivity.sharedPreferencesUser.contains("Email")) {
                    try {

                        String email = MainActivity.sharedPreferencesUser.getString("Email", "");
                        String pass = MainActivity.sharedPreferencesUser.getString("Pass", "");

                        String statusLogin = new Take_Data(SplashScreenActivity.this).execute(email, pass).get();
                        switch (statusLogin) {
                            case "success":
                                Toasty.success(SplashScreenActivity.this, "Đã cập nhật lịch mới nhất!", Toast.LENGTH_LONG, true).show();
                                startActivity(new Intent(this, MainActivity.class));
                                finish();
                                break;

                            case "failure":
                                Toasty.error(SplashScreenActivity.this, "Email hoặc mật khẩu không chính xác!", Toast.LENGTH_LONG, true).show();
                                startActivity(new Intent(SplashScreenActivity.this, Login.class));
                                finish();
                                break;

                            case "time up":
                                Toasty.warning(SplashScreenActivity.this, "Sever không phản hồi, hiển thị lịch của lần cập nhật gần nhất!", Toast.LENGTH_LONG, true).show();
                                startActivity(new Intent(this, MainActivity.class));
                                finish();
                                break;

                            case "error":
                                Toasty.error(SplashScreenActivity.this, "Xảy ra lỗi, báo ngay cho tôi, hiển thị lịch của lần cập nhật gần nhất!", Toast.LENGTH_LONG, true).show();
                                startActivity(new Intent(this, MainActivity.class));
                                finish();
                                break;

                            case "expired":
                                Renewal();
                                break;

                            case "Logined":
                                Toasty.warning(SplashScreenActivity.this, "Tài khoản đang đăng nhập ở thiết bị khác!", Toast.LENGTH_LONG, true).show();
                                startActivity(new Intent(SplashScreenActivity.this, Login.class));
                                finish();
                                break;

                            }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, Login.class));
                    finish();
                }

//                Kiểm tra nếu không có kết nối internet thì thông báo
            } else {
                Toasty.error(SplashScreenActivity.this, "Không có kết nối Internet, hiển thị lịch của lần cập nhật gần nhất!", Toast.LENGTH_LONG, true).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }


    }

//    Kiểm tra kết nối INTERNET
    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        try{
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return false;
    }


//    Thông báo đã hết hạn CTMS và có muốn gia hạn ngay không
    private void Renewal(){
        final Dialog dialog = new Dialog(SplashScreenActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_yes_no);
        dialog.setCanceledOnTouchOutside(false);

        TextView textViewQuestion = (TextView) dialog.findViewById(R.id.textViewQuestionYesNo);
        Button buttonNo = (Button)  dialog.findViewById(R.id.buttonNo);
        Button buttonYes = (Button) dialog.findViewById(R.id.buttonYes);

        textViewQuestion.setText("Hết hạn tài khoản CTMS, gia hạn ngay hoặc hiển thị lịch của lần cập nhật gần nhất! Bạn có muốn gia hạn ngay?");

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            }
        });

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(SplashScreenActivity.this,Nav_Service_OCC.class));
                finish();
            }
        });
        dialog.show();
    }
}
