package com.aryomtech.plasmaz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.aryomtech.plasmaz.HolderClasses.notifyHolder;
import com.aryomtech.plasmaz.Model.notifyData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Notice extends AppCompatActivity {

    DatabaseReference ref,notification;
    FirebaseDatabase database;
    RecyclerView recyclerView;
    boolean once=true;
    ConstraintLayout notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        Intent getdatafromtime=getIntent();

/*        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notice=findViewById(R.id.notice);
            notice.setBackgroundResource(R.drawable.noticebg);
        }*/
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Notifications");

        recyclerView = findViewById(R.id.rv_notice);

        notification=FirebaseDatabase.getInstance().getReference().child("Notifications");
        notification.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(once) {
                    long notificationcount = snapshot.getChildrenCount();

                    getSharedPreferences("storenotificationcount", MODE_PRIVATE).edit()
                            .putLong("notifycount", notificationcount).apply();
                    once=false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        Window window = Notice.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Notice.this, R.color.black));
        window.setNavigationBarColor(ContextCompat.getColor(Notice.this, R.color.black));



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<notifyData, notifyHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<notifyData, notifyHolder>(
                        notifyData.class,
                        R.layout.notification_design,
                        notifyHolder.class,
                        ref
                ) {
                    @Override
                    protected void populateViewHolder(notifyHolder notifyHolder, notifyData notifyData, int i) {

                        notifyHolder.setView(Notice.this,notifyData.getText(),notifyData.getDatetime(),notifyData.getLink(),notifyData.getTime(),notifyData.getImagelink());

                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }
    @Override
    public void onBackPressed() {
        Intent main_home=new Intent(Notice.this,MainActivity.class);
        startActivity(main_home);
        finish();
        super.onBackPressed();

    }
}