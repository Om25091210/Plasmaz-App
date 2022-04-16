package com.aryomtech.plasmaz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.aryomtech.plasmaz.Adapter.ExamsAdapterClass;
import com.aryomtech.plasmaz.Adapter.ReceiptAdapterClass;
import com.aryomtech.plasmaz.Model.AssignmentData;
import com.aryomtech.plasmaz.Model.feesData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Receipt extends AppCompatActivity {

    DatabaseReference ref;
    RecyclerView recyclerView;
    SearchView searchView;

    private FirebaseAuth mAuth;
    static FirebaseUser useruuid;

    ArrayList<feesData> list;

    boolean once=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        mAuth = FirebaseAuth.getInstance();
        useruuid = mAuth.getCurrentUser();

        ref= FirebaseDatabase.getInstance().getReference().child("fees").child(useruuid.getUid());
        recyclerView=findViewById(R.id.rv_re);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        searchView=findViewById(R.id.searchView);

        list=new ArrayList<>();
        
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
                            list.add(ds.getValue(feesData.class));

                            ReceiptAdapterClass receiptAdapterClass=new ReceiptAdapterClass(list, Receipt.this);
                            recyclerView.setAdapter(receiptAdapterClass);
                        }

                        once=true;

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Receipt.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        ArrayList<feesData> mylist=new ArrayList<>();
        for(feesData object:list){

            if(object.getDate().toLowerCase().contains(str.toLowerCase().trim()))
            {
                mylist.add(object);
            }
            else if(object.getNote().toLowerCase().contains(str.toLowerCase().trim())){
                mylist.add(object);
            }
            else if(object.getDate().toLowerCase().contains(str.toLowerCase().trim())){
                mylist.add(object);
            }
        }
        ReceiptAdapterClass receiptAdapterClass=new ReceiptAdapterClass(mylist, Receipt.this);
        recyclerView.setAdapter(receiptAdapterClass);

    }

}