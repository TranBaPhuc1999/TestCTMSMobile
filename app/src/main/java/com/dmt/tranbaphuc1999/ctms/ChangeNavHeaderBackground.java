package com.dmt.tranbaphuc1999.ctms;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

public class ChangeNavHeaderBackground extends AppCompatActivity {

    static ArrayList<Integer> arrayListImageBackGround = new ArrayList<Integer>();

    //Add Image HOU
    private static void AddImage() {
        arrayListImageBackGround.add(R.drawable.hou_image_1);
        arrayListImageBackGround.add(R.drawable.hou_image_2);
        arrayListImageBackGround.add(R.drawable.hou_image_3);
        arrayListImageBackGround.add(R.drawable.hou_image_4);
        arrayListImageBackGround.add(R.drawable.hou_image_5);
    }

    //Random Image
    public static int Change(){
        AddImage();
        Random random = new Random();
        int location = random.nextInt(arrayListImageBackGround.size());
        return arrayListImageBackGround.get(location);
    }
}
