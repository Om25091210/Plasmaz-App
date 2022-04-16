package com.aryomtech.plasmaz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.aryomtech.plasmaz.TimeLine.timeline;

public class addtimeline extends AppCompatActivity {

    EditText note_timeline;
    DatabaseReference ref;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtimeline);

        note_timeline=findViewById(R.id.editTextTextMultiLine4);
        ref= FirebaseDatabase.getInstance().getReference().child("Timeline");

        String push=ref.push().getKey();
        floatingActionButton=findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!note_timeline.getText().toString().trim().equals("")) {

                    DateFormat Date = DateFormat.getDateInstance();
                    Calendar cals = Calendar.getInstance();
                    String currentDate = Date.format(cals.getTime());

                    ref.child(push).child("text").setValue(note_timeline.getText().toString().trim());
                    ref.child(push).child("date").setValue(currentDate);
                    ref.child(push).child("key").setValue(push);


                    Snackbar snackbar = Snackbar.make(v, "Saved Successfully.  Do You Want to Exit?", Snackbar.LENGTH_LONG);
                    snackbar.setDuration(10000);
                    snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    snackbar.setAction("Yes", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            timeline.finish();
                            Intent time=new Intent(addtimeline.this,TimeLine.class);
                            startActivity(time);
                            Animatoo.animateZoom(addtimeline.this);
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
                            ref.child(push).removeValue();
                            timeline.finish();
                            Intent time=new Intent(addtimeline.this,TimeLine.class);
                            startActivity(time);
                            Animatoo.animateZoom(addtimeline.this);
                            finish();
                        }
                    });
                    snackbar.show();
                }
            }
        });
    }
}