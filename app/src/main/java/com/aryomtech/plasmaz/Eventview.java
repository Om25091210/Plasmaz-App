package com.aryomtech.plasmaz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.EventLog;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aryomtech.plasmaz.Adapter.AddStudentsAdapterClass;
import com.aryomtech.plasmaz.Adapter.AttendanceAdapterClass;
import com.aryomtech.plasmaz.HolderClasses.EventData;
import com.aryomtech.plasmaz.Model.five_am_data;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.naz013.colorslider.ColorSlider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class Eventview extends AppCompatActivity {

    DatabaseReference event,Teachers;
    String date_on_longpress="";
    FirebaseAuth auth;
    FirebaseUser useruuid;
    FloatingActionButton edit_Students;
    RecyclerView recyclerView;
    TextView textDateTime;
    DatabaseReference sheet_ref;
    boolean once=false;
    ArrayList<five_am_data> list;

    ArrayList<String> teachers_list = new ArrayList<>();
    ArrayList<String> students_list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventview);

        auth = FirebaseAuth.getInstance();
        useruuid = auth.getCurrentUser();

        date_on_longpress=getIntent().getStringExtra("SendingWhichDate");
        event= FirebaseDatabase.getInstance().getReference().child("Attendance").child(date_on_longpress);

        sheet_ref=FirebaseDatabase.getInstance().getReference().child("sheet");


        textDateTime=findViewById(R.id.textDateTime);
        textDateTime.setText(date_on_longpress);

        edit_Students=findViewById(R.id.editnote);
        edit_Students.setVisibility(View.GONE);

        list=new ArrayList<>();
        recyclerView=findViewById(R.id.rv_attendance);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

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


        event.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot key:snapshot.getChildren()){
                    students_list.add(snapshot.child(key.getKey()).getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        edit_Students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_choice=new Intent(Eventview.this,AddStudents.class);
                startActivity(open_choice);
                Animatoo.animateZoom(Eventview.this);
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();

        if (sheet_ref != null) {

            sheet_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists() && !once) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            list.add(ds.getValue(five_am_data.class));

                            AttendanceAdapterClass attendanceAdapterClass = new AttendanceAdapterClass(list, Eventview.this,date_on_longpress,teachers_list,students_list);
                            recyclerView.setAdapter(attendanceAdapterClass);

                        }

                        once = true;

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Eventview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void back(View view) {
        finish();
    }
}