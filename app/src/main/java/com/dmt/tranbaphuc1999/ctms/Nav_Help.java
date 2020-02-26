package com.dmt.tranbaphuc1999.ctms;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.Calendar;

public class Nav_Help extends AppCompatActivity {

    Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        buttonSend = (Button) findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogQuestion();
            }
        });

    }


    private void DialogQuestion() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.question_send_infor);
        dialog.setCanceledOnTouchOutside(false);

        Button buttonNoSend = (Button) dialog.findViewById(R.id.buttonNoSend);
        Button buttonSend = (Button) dialog.findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThanks();
                dialog.dismiss();
            }
        });

        buttonNoSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThanks();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void DialogThanks() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.help_thanks);
        dialog.setCanceledOnTouchOutside(false);

        Button buttonBack = (Button) dialog.findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(Nav_Help.this, MainActivity.class));
            }
        });

        dialog.show();
    }
}
