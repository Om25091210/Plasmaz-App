package com.aryomtech.plasmaz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
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

public class Push extends AppCompatActivity {

    DatabaseReference five_am_club,check_time;
    ArrayList<String> arrayList;
    Button send;
    EditText title,body;
    boolean once=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        send=findViewById(R.id.button6);

        title=findViewById(R.id.editTextTextPersonName3);
        body=findViewById(R.id.editTextTextMultiLine3);

        Date date = new Date() ;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
        dateFormat.format(date);

        five_am_club = FirebaseDatabase.getInstance().getReference().child("five_am_club");
        check_time = FirebaseDatabase.getInstance().getReference().child("check_time");

        arrayList=new ArrayList<String>();

        try {
            if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("23:01")) && Objects.requireNonNull(dateFormat.parse(dateFormat.format(date))).before(dateFormat.parse("23:50"))) {

                    check_time.setValue("yes");

                    five_am_club.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (once) {

                                for (DataSnapshot uids : snapshot.getChildren()) {
                                    if((arrayList.size() < snapshot.getChildrenCount())) {
                                        arrayList.add(uids.getKey());
                                    }
                                }
                                once=false;
                                Log.e("size",arrayList.size()+"");
                                for (int i=0;i<arrayList.size();i++){
                                    Log.e("array",arrayList.get(i));
                                    five_am_club.child(arrayList.get(i)).child("legend").setValue("no");
                                    five_am_club.child(arrayList.get(i)).child("liner").setValue("");
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titlestr=title.getText().toString();
                String bodystr=body.getText().toString();
                topic topic = new topic();
                topic.noti(titlestr+"", bodystr+"");
                Snackbar.make(findViewById(android.R.id.content),"Notification Sended Successfully!!.",Snackbar.LENGTH_LONG).show();
            }
        });

    }
}