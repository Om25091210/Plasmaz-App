package com.aryomtech.plasmaz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.aryomtech.plasmaz.Adapter.ExamsAdapterClass;
import com.aryomtech.plasmaz.Adapter.Five_AM_ClubAdapterClass;
import com.aryomtech.plasmaz.Model.AssignmentData;
import com.aryomtech.plasmaz.Model.five_am_data;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Five_AM_Club extends AppCompatActivity {

    DatabaseReference ref,check_time;
    FirebaseDatabase database;
    RecyclerView recyclerView;
    boolean once=false;
    String is_time_ok_to_hack="";

    ArrayList<five_am_data> list;
    ArrayList<Long> score=new ArrayList<Long>();


    ConstraintLayout notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five__a_m__club);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("five_am_club");

        recyclerView = findViewById(R.id.rv_five_am_club);

        check_time = FirebaseDatabase.getInstance().getReference().child("check_time");
        check_time.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                is_time_ok_to_hack=snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        list=new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ref != null) {

            Query sort_ref=ref.orderByChild("points");
            sort_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists() && !once) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            list.add(ds.getValue(five_am_data.class));

                            score.add(snapshot.child(ds.getKey()).child("points").getValue(Long.class));

                            Five_AM_ClubAdapterClass five_am_clubAdapterClass = new Five_AM_ClubAdapterClass(list, Five_AM_Club.this,findViewById(android.R.id.content),is_time_ok_to_hack,score);
                            recyclerView.setAdapter(five_am_clubAdapterClass);

                        }

                        once = true;

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Five_AM_Club.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        Intent main_home=new Intent(Five_AM_Club.this,MainActivity.class);
        startActivity(main_home);
        finish();
        super.onBackPressed();

    }
}