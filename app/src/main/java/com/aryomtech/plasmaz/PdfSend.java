package com.aryomtech.plasmaz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PdfSend extends AppCompatActivity {

    EditText link,Date,note;
    Button send;
    DatabaseReference fees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_send);


        String uid=getIntent().getStringExtra("sending_UId");

        Window window = PdfSend.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(PdfSend.this, R.color.send_status));
        window.setNavigationBarColor(ContextCompat.getColor(PdfSend.this, R.color.send_nav));

        fees= FirebaseDatabase.getInstance().getReference().child("fees");

        link=findViewById(R.id.editTextTextPersonName);
        Date=findViewById(R.id.editTextTextPersonName2);
        note=findViewById(R.id.editTextTextMultiLine);

        send=findViewById(R.id.button2);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!link.getText().toString().equals("")){
                    if(!Date.getText().toString().equals("")){
                        String key=fees.push().getKey();
                        fees.child(uid).child(key).child("link").setValue(link.getText().toString());
                        fees.child(uid).child(key).child("Date").setValue(Date.getText().toString());
                        fees.child(uid).child(key).child("note").setValue(note.getText().toString());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PdfSend.this, "Delivered Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        },1000);
                    }
                }
            }
        });
    }
}