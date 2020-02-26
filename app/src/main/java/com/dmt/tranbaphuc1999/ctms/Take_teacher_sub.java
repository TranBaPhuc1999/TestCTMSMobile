package com.dmt.tranbaphuc1999.ctms;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Take_teacher_sub extends AsyncTask<String,String,String> {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Activity context;
    SharedPreferences.Editor editor = MainActivity.sharedPreferencesUser.edit();
    int id;

    public Take_teacher_sub(Activity ctx,int teacher_id)
    {
        context=ctx;
        id=teacher_id;
    }
    @Override
    protected String doInBackground(String... strings) {
        final Connection_CTMS connectionCtms = new Connection_CTMS(context);

        Thread Thread_Teacher = new Thread() {
            public void run() {
                connectionCtms.Take_teacher_sub(id);
            }
        };

        Thread_Teacher.start();
        try {
            Thread_Teacher.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
