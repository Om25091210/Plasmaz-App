package com.aryomtech.plasmaz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.aryomtech.plasmaz.Adapter.StudentsAdapterClass;
import com.aryomtech.plasmaz.Model.studData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Students extends AppCompatActivity {
    DatabaseReference ref;
    RecyclerView recyclerView;
    SearchView searchView;

    ArrayList<studData> list;

    boolean once=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        ref= FirebaseDatabase.getInstance().getReference().child("Students");
        recyclerView=findViewById(R.id.rv_stud);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        searchView=findViewById(R.id.searchView);

        list=new ArrayList<>();

        Window window = Students.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(Students.this, R.color.send_status));
        window.setNavigationBarColor(ContextCompat.getColor(Students.this, R.color.black));

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
                            list.add(ds.getValue(studData.class));

                            StudentsAdapterClass studentsAdapterClass=new StudentsAdapterClass(list, Students.this);
                            recyclerView.setAdapter(studentsAdapterClass);
                        }

                        once=true;

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Students.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        ArrayList<studData> mylist=new ArrayList<>();
        for(studData object:list){

            if(object.getName().toLowerCase().contains(str.toLowerCase().trim()))
            {
                mylist.add(object);
            }
            else if(object.getClassroom().toLowerCase().contains(str.toLowerCase().trim())){
                mylist.add(object);
            }
        }
        StudentsAdapterClass studentsAdapterClass=new StudentsAdapterClass(mylist, Students.this);
        recyclerView.setAdapter(studentsAdapterClass);

    }
}