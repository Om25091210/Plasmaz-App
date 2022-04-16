package com.aryomtech.plasmaz.service;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class MyService extends BroadcastReceiver {

    DatabaseReference five_am_club,check_time;
    ArrayList<String> arrayList;
    @Override
    public void onReceive(Context context, Intent intent) {


        Log.e("alarm","Done");

        Date date = new Date() ;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
        dateFormat.format(date);

        five_am_club = FirebaseDatabase.getInstance().getReference().child("five_am_club");
        check_time = FirebaseDatabase.getInstance().getReference().child("check_time");

        arrayList=new ArrayList<String>();

        try {
            if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("05:01")) && Objects.requireNonNull(dateFormat.parse(dateFormat.format(date))).before(dateFormat.parse("05:10"))) {
                if(intent.getAction().equals("check_time")){
                    check_time.setValue("no");
                    Log.e("check_time","Successfull");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
