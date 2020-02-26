package com.dmt.tranbaphuc1999.ctms;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import es.dmoral.toasty.Toasty;

public class Login extends AppCompatActivity {

    EditText editTextEmailUser, editTextPassUser;
    TextView textViewForgetPass,buttonLogin;
    CheckBox checkBoxRememberPass;
    ProgressBar progressBar;
    SharedPreferences.Editor editor = MainActivity.sharedPreferencesUser.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Init();
//        Ẩn progressBar đi
        progressBar.setVisibility(View.GONE);
        progressBar.setBackgroundColor(0xbf000000);


//        Lấy Email và Pass nếu có sẵn đưa ra textView
        editTextEmailUser.setText(MainActivity.sharedPreferencesUser.getString("Email",""));
        editTextPassUser.setText(MainActivity.sharedPreferencesUser.getString("Pass",""));
        checkBoxRememberPass.setChecked(MainActivity.sharedPreferencesUser.getBoolean("CheckBoxRemember",false));


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonLogin.setEnabled(false);          /*Vô hiệu hóa nút đăng nhập*/

                String email = editTextEmailUser.getText().toString().trim();
                String pass = editTextPassUser.getText().toString().trim();

//                Kiểm tra nếu người dùng quên nhập
                if(email.equals("")||pass.equals("")){
                    Toasty.warning(Login.this, "Nhập đủ và chính xác thông tin đăng nhập!", Toast.LENGTH_LONG, true).show();
                }
                else if(email.contains("@")) {    /*  Kiểm tra có phải email không, chưa đủ cần xây dựng thêm nhưng hiện tại cứ để đó xử lí sau*/
                    progressBar.setVisibility(View.VISIBLE);      /*  Cho progress lên mà không biết tại sao không lên, có lẽ do chờ cho Thread xử lí xong nó mới hiện, mà Thread xong thì xong hết rồi nên chả kịp thấy luôn xD*/
                    try {
                        String statusLogin;
                        if(MainActivity.sharedPreferencesUser.contains("Fisrt")&&MainActivity.sharedPreferencesUser.getString("EmailOld","").equals(email)) {
                            statusLogin = new Take_Data(Login.this).execute(email, pass).get();
                        }else {
                            editor.putBoolean("Fisrt",false);
                            editor.commit();
                            Toasty.info(Login.this, "Tài khoản mới đăng nhập sẽ mất chút thời gian cài đặt dữ liệu!", Toast.LENGTH_LONG, true).show();
                            statusLogin = new Take_First_Data(Login.this).execute(email, pass).get();
                        }
                        switch (statusLogin){

                            case "success":          /*Nếu đăng nhập thành công thì lưu tài khoản và đến trang chủ*/
                                            if(checkBoxRememberPass.isChecked()){
                                                editor.putString("Email",email);
                                                editor.putString("EmailOld",email);
                                                editor.putString("Pass",pass);
                                                editor.putBoolean("CheckBoxRemember",true);
                                                editor.commit();
                                            } else{ /* Vẫn ghi nhớ tài khoản mật khẩu dù không click check box*/
                                                editor.putString("Email",email);
                                                editor.putString("Pass",pass);
                                                editor.putBoolean("CheckBoxRemember",false);
                                                editor.commit();
                                            }

                                            editor.putBoolean("Fisrt",true);
                                            editor.commit();

                                            startActivity(new Intent(Login.this,MainActivity.class));

                                            finish();
                                            break;

                            case "failure": Toasty.error(Login.this, "Email hoặc mật khẩu không chính xác!", Toast.LENGTH_LONG, true).show();
                                            break;

                            case "time up": if(!(MainActivity.sharedPreferencesUser.contains("Fisrt"))){
                                            Toasty.warning(Login.this, "Server không phản hồi, thử lại sau!", Toast.LENGTH_LONG, true).show();
//                                            Nếu Server không phản hồi/sập thì thông báo và hỏi trên Dialog
                                            editor.putBoolean("ExitApp",true);                 /* Ghi nhớ thoát app*/
                                            editor.commit();

                                            startActivity(new Intent(Login.this,SplashScreenActivity.class));
                                            finish();
                                            } else {
                                                OldSchedule();
                                                   }
                                            break;

                            case "expired": if(!(MainActivity.sharedPreferencesUser.contains("Fisrt"))){
                                            Toasty.warning(Login.this, "Hết hạn tài khoản CTMS, gia hạn ngay!", Toast.LENGTH_LONG, true).show();
                                            startActivity(new Intent(Login.this,Nav_Service_OCC.class));
                                            finish();
                                             } else {                                       /*Nếu tài khoản hết hạn thì lưu tài khoản và hỏi trên Dialog*/
                                            editor = MainActivity.sharedPreferencesUser.edit();

                                            if(checkBoxRememberPass.isChecked()){
                                                editor.putString("Email",email);
                                                editor.putString("Pass",pass);
                                                editor.putBoolean("CheckBoxRemember",true);
                                                editor.commit();
                                            } else{
                                                editor.remove("Email");
                                                editor.remove("Pass");
                                                editor.remove("CheckBoxRemember");
                                                editor.commit();
                                            }
                                            Renewal();
                                            }
                                            break;

                            case "Logined": Toasty.error(Login.this, "Tài khoản đang đăng nhập ở thiết bị khác!", Toast.LENGTH_LONG, true).show();
                                            break;

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                progressBar.setVisibility(View.GONE);
                buttonLogin.setEnabled(true);               /*Hoạt động lại nút đăng nhập*/
            }
        });

        if(!(MainActivity.sharedPreferencesUser.contains("Fisrt"))) {
            Toasty.success(Login.this, "Xin chào lần đầu đăng nhập!", Toast.LENGTH_LONG, true).show();
        }

        }

//    Ánh xạ
    private void Init(){
        editTextEmailUser = (EditText) findViewById(R.id.editText_Email_User);
        editTextPassUser = (EditText) findViewById(R.id.editText_Pass_User);
        buttonLogin = (TextView) findViewById(R.id.buttonLogin);
        textViewForgetPass = (TextView) findViewById(R.id.textView_Forget_Pass);
        checkBoxRememberPass = (CheckBox) findViewById(R.id.checkBox_Remember_User);
        progressBar = (ProgressBar) findViewById(R.id.progressBarLoading);
    }


//    Thông báo đã hết hạn CTMS và có muốn gia hạn ngay không
    private void Renewal(){
        final Dialog dialog = new Dialog(Login.this);
//        Cài đặt thuộc tính cho Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_yes_no);
        dialog.setCanceledOnTouchOutside(false);

        TextView textViewQuestion = (TextView) dialog.findViewById(R.id.textViewQuestionYesNo);
        Button buttonNo = (Button) dialog.findViewById(R.id.buttonNo);
        Button buttonYes = (Button) dialog.findViewById(R.id.buttonYes);

        textViewQuestion.setText("Hết hạn tài khoản CTMS, gia hạn ngay hoặc hiển thị lịch của lần cập nhật gần nhất! Bạn có muốn gia hạn ngay?");


//        2 nút có, không, chọn có thì vào trang gia hạn, không thì hiển thị lịch cũ
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(Login.this, MainActivity.class));
                finish();
            }
        });

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(Login.this,Nav_Service_OCC.class));
                finish();
            }
        });
        dialog.show();
    }



//    Thông báo Server sập , có muốn hiển thị lịch cũ không
    private void OldSchedule(){
        final Dialog dialog = new Dialog(Login.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_yes_no);
        dialog.setCanceledOnTouchOutside(false);

        TextView textViewQuestion = (TextView) dialog.findViewById(R.id.textViewQuestionYesNo);
        Button buttonNo = (Button) dialog.findViewById(R.id.buttonNo);
        Button buttonYes = (Button) dialog.findViewById(R.id.buttonYes);

        textViewQuestion.setText("Sever không phản hồi, bạn có muốn xem lịch của lần cập nhật gần nhất!");

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(Login.this,MainActivity.class));
                finish();
            }
        });
        dialog.show();
    }
}
