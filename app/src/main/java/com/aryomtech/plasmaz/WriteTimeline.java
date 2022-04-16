package com.aryomtech.plasmaz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.aryomtech.plasmaz.TimeLine.timeline;

public class WriteTimeline extends AppCompatActivity {

    EditText note_timeline;
    DatabaseReference ref;
    FloatingActionButton floatingActionButton;
    String key="",text="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_timeline);

        note_timeline=findViewById(R.id.editTextTextMultiLine4);
        ref= FirebaseDatabase.getInstance().getReference().child("Timeline");


        key=getIntent().getStringExtra("key_push");
        text=getIntent().getStringExtra("text_push");

        note_timeline.setText(text);

        floatingActionButton=findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!note_timeline.getText().toString().trim().equals("")) {

                    ref.child(key).child("text").setValue(note_timeline.getText().toString().trim());
                    Snackbar snackbar = Snackbar.make(v, "Saved Successfully.  Do You Want to Exit?", Snackbar.LENGTH_LONG);
                    snackbar.setDuration(10000);
                    snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    snackbar.setAction("Yes", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            timeline.finish();
                            Intent time=new Intent(WriteTimeline.this,TimeLine.class);
                            startActivity(time);
                            Animatoo.animateZoom(WriteTimeline.this);
                            finish();
                        }
                    });
                    snackbar.show();
                }
                else{
                    Snackbar snackbar = Snackbar.make(v, "Would you want to delete this timeline?", Snackbar.LENGTH_LONG);
                    snackbar.setDuration(10000);
                    snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    snackbar.setAction("Yes", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ref.child(key).removeValue();
                        }
                    });
                    snackbar.show();
                }
            }
        });
    }
}