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
import android.widget.SearchView;
import android.widget.Toast;

import com.aryomtech.plasmaz.Adapter.ExamsAdapterClass;
import com.aryomtech.plasmaz.Model.AssignmentData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Exams extends AppCompatActivity {

    DatabaseReference ref;
    RecyclerView recyclerView;
    SearchView searchView;

    ArrayList<AssignmentData> list;

    boolean once=false;
    ConstraintLayout exams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);



        ref= FirebaseDatabase.getInstance().getReference().child("Exams");
        recyclerView=findViewById(R.id.rv_exams);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);

        searchView=findViewById(R.id.searchView);

        list=new ArrayList<>();

        Window window = Exams.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(Exams.this, R.color.exams_status));
        window.setNavigationBarColor(ContextCompat.getColor(Exams.this, R.color.exams_status));
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
                            list.add(ds.getValue(AssignmentData.class));

                            ExamsAdapterClass examsAdapterClass=new ExamsAdapterClass(list, Exams.this);
                            recyclerView.setAdapter(examsAdapterClass);
                        }

                        once=true;

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Exams.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        ArrayList<AssignmentData> mylist=new ArrayList<>();
        for(AssignmentData object:list){

            if(object.getChapter().toLowerCase().contains(str.toLowerCase().trim()))
            {
                mylist.add(object);
            }
            else if(object.getSubject().toLowerCase().contains(str.toLowerCase().trim())){
                mylist.add(object);
            }
            else if(object.getDate().toLowerCase().contains(str.toLowerCase().trim())){
                mylist.add(object);
            }
        }
        ExamsAdapterClass examsAdapterClass=new ExamsAdapterClass(mylist, Exams.this);
        recyclerView.setAdapter(examsAdapterClass);

    }
    @Override
    public void onBackPressed() {
        Intent main_home=new Intent(Exams.this,MainActivity.class);
        startActivity(main_home);
        finish();
        super.onBackPressed();

    }
}