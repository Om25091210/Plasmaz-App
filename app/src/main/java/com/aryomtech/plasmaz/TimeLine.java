package com.aryomtech.plasmaz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.aryomtech.plasmaz.Adapter.TimeAdapterClass;
import com.aryomtech.plasmaz.Model.TimeData;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TimeLine extends AppCompatActivity {

    DatabaseReference ref,Teachers;
    RecyclerView recyclerView;
    SearchView searchView;
    public static Activity timeline;

    private FirebaseAuth mAuth;
    static FirebaseUser useruuid;

    ArrayList<TimeData> list;
    ArrayList<String> teachers_list = new ArrayList<>();
    boolean once=false;
    FloatingActionButton edit_Students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        timeline=this;

        mAuth = FirebaseAuth.getInstance();
        useruuid = mAuth.getCurrentUser();

        ref= FirebaseDatabase.getInstance().getReference().child("Timeline");
        recyclerView=findViewById(R.id.rv_timeline);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        edit_Students=findViewById(R.id.floatingActionButton2);
        edit_Students.setVisibility(View.GONE);

        searchView=findViewById(R.id.searchView);

        list=new ArrayList<>();

        Teachers=FirebaseDatabase.getInstance().getReference().child("Teachers");
        Teachers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(useruuid.getUid()).exists()){
                    teachers_list.add(snapshot.child(useruuid.getUid()).getValue(String.class));
                    edit_Students.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        edit_Students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_choice=new Intent(TimeLine.this,addtimeline.class);
                startActivity(open_choice);
                Animatoo.animateZoom(TimeLine.this);
            }
        });

        Window window = TimeLine.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(TimeLine.this, R.color.white));
        window.setNavigationBarColor(ContextCompat.getColor(TimeLine.this, R.color.white));

    }
    @Override
    protected void onStart() {
        super.onStart();

        if(ref!=null){

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists() && !once) {
                        for(DataSnapshot ds:snapshot.getChildren()){
                            list.add(ds.getValue(TimeData.class));

                            TimeAdapterClass timeAdapterClass=new TimeAdapterClass(list, TimeLine.this,teachers_list);
                            recyclerView.setAdapter(timeAdapterClass);
                        }

                        once=true;

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(TimeLine.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(searchView!=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }

    }
    private void search(String str) {

        ArrayList<TimeData> mylist=new ArrayList<>();
        for(TimeData object:list){

            if(object.getDate().toLowerCase().contains(str.toLowerCase().trim()))
            {
                mylist.add(object);
            }
        }
        TimeAdapterClass timeAdapterClass=new TimeAdapterClass(mylist, TimeLine.this,teachers_list);
        recyclerView.setAdapter(timeAdapterClass);

    }
    @Override
    public void onBackPressed() {
        Intent main_home=new Intent(TimeLine.this,MainActivity.class);
        startActivity(main_home);
        finish();
        super.onBackPressed();

    }
}