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

import com.aryomtech.plasmaz.Adapter.CertifyAdapterClass;
import com.aryomtech.plasmaz.Model.AssignmentData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Certify extends AppCompatActivity {

    DatabaseReference ref;
    RecyclerView recyclerView;
    SearchView searchView;
    private FirebaseAuth mAuth;
    static FirebaseUser useruuid;
    ArrayList<AssignmentData> list;

    boolean once=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certify);

        mAuth = FirebaseAuth.getInstance();
        useruuid = mAuth.getCurrentUser();

        ref= FirebaseDatabase.getInstance().getReference().child("Certificates").child(useruuid.getUid());
        recyclerView=findViewById(R.id.rv_certify);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);

        searchView=findViewById(R.id.searchView);

        list=new ArrayList<>();

        Window window = Certify.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(Certify.this, R.color.black));
        window.setNavigationBarColor(ContextCompat.getColor(Certify.this, R.color.black));


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

                            CertifyAdapterClass certifyAdapterClass=new CertifyAdapterClass(list, Certify.this);
                            recyclerView.setAdapter(certifyAdapterClass);

                        }

                        once=true;

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Certify.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

            if(object.getSubject().toLowerCase().contains(str.toLowerCase().trim()))
            {
                mylist.add(object);
            }
            else if(object.getChapter().toLowerCase().contains(str.toLowerCase().trim())){
                mylist.add(object);
            }
        }
        CertifyAdapterClass certifyAdapterClass=new CertifyAdapterClass(mylist, Certify.this);
        recyclerView.setAdapter(certifyAdapterClass);

    }
}