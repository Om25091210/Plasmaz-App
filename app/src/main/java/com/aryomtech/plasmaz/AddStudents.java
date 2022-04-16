package com.aryomtech.plasmaz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.aryomtech.plasmaz.Adapter.AddStudentsAdapterClass;
import com.aryomtech.plasmaz.Adapter.Five_AM_ClubAdapterClass;
import com.aryomtech.plasmaz.Model.five_am_data;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddStudents extends AppCompatActivity {

    DatabaseReference ref;
    FirebaseDatabase database;
    RecyclerView recyclerView;
    boolean once=false;
    DatabaseReference sheet;
    ArrayList<five_am_data> list;
    ArrayList<String> pic=new ArrayList<String>();
    boolean isOnce=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_students);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("five_am_club");

        sheet=FirebaseDatabase.getInstance().getReference().child("sheet");
        sheet.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(isOnce) {
                    for (DataSnapshot key:snapshot.getChildren()){
                        pic.add(snapshot.child(key.getKey()).child("dp").getValue(String.class));
                    }

                }
                isOnce=false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        list=new ArrayList<>();

        recyclerView=findViewById(R.id.rv_edit_attendance);
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

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists() && !once) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            list.add(ds.getValue(five_am_data.class));

                            AddStudentsAdapterClass addStudentsAdapterClass = new AddStudentsAdapterClass(list, AddStudents.this,pic);
                            recyclerView.setAdapter(addStudentsAdapterClass);

                        }

                        once = true;

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AddStudents.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}