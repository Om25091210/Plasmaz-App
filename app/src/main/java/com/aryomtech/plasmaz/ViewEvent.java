package com.aryomtech.plasmaz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewEvent extends AppCompatActivity {

    EditText inputNoteTitle,inputNoteSubtitle,inputNote;
    TextView textDateTime,textWebURL;
    ImageView imageNote,imageback;
    String title,subtitle,image_url,website,tarik,Body;
    FloatingActionButton deleteNote;
    Dialog dialogAnimated;
    DatabaseReference event;

    FirebaseAuth auth;
    FirebaseUser useruuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        auth = FirebaseAuth.getInstance();
        useruuid = auth.getCurrentUser();


        Intent getdata=getIntent();
        tarik=getdata.getStringExtra("sending_tarik");
        title=getdata.getStringExtra("sending_title");
        subtitle=getdata.getStringExtra("sending_subtitle");
        image_url=getdata.getStringExtra("sending_imageUrl");
        website=getdata.getStringExtra("sending_website");
        Body=getdata.getStringExtra("sending_body");

        inputNoteTitle=findViewById(R.id.inputNoteTitle);
        textDateTime=findViewById(R.id.textDateTime);
        inputNoteSubtitle=findViewById(R.id.inputNoteSubtitle);
        textWebURL=findViewById(R.id.textWebURL);
        inputNote=findViewById(R.id.inputNote);
        imageNote=findViewById(R.id.imageNote);
        imageback=findViewById(R.id.imageBack);
        deleteNote=findViewById(R.id.deleteNote);

        event= FirebaseDatabase.getInstance().getReference().child("Events").child(useruuid.getUid());

        inputNoteTitle.setEnabled(false);
        inputNoteSubtitle.setEnabled(false);
        inputNote.setEnabled(false);

        inputNoteTitle.setText(title);
        inputNoteSubtitle.setText(subtitle);
        textDateTime.setText(tarik);
        textWebURL.setText(website);
        inputNote.setText(Body);

        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAnimated=new Dialog(ViewEvent.this,R.style.dialogstyle);
                dialogAnimated.setContentView(R.layout.layout_delete_note);
                TextView cancel=dialogAnimated.findViewById(R.id.textCancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogAnimated.dismiss();
                    }
                });

                TextView delete=dialogAnimated.findViewById(R.id.textDeleteNote);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        event.child(tarik).removeValue();
                        dialogAnimated.dismiss();
                        finish();
                    }
                });
                dialogAnimated.show();
            }
        });

        Glide.with(ViewEvent.this).load(image_url).into(imageNote);

    }
}