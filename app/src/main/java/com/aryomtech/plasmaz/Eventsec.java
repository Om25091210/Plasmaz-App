package com.aryomtech.plasmaz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.marcohc.robotocalendar.RobotoCalendarView;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class Eventsec extends AppCompatActivity implements RobotoCalendarView.RobotoCalendarListener {

    private RobotoCalendarView robotoCalendarView;
    DatabaseReference event;
    RoundedImageView imageNote;
    TextView textTitle,textSubtitle,textDateTime;
    FirebaseAuth auth;
    FirebaseUser useruuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventsec);

        auth = FirebaseAuth.getInstance();
        useruuid = auth.getCurrentUser();

        robotoCalendarView = findViewById(R.id.robotoCalendarPicker);

        textTitle=findViewById(R.id.textTitle);
        textSubtitle=findViewById(R.id.textSubtitle);
        textDateTime=findViewById(R.id.textDateTime);
        imageNote=findViewById(R.id.imageNote);

       // Button clearSelectedDayButton = findViewById(R.id.clearSelectedDayButton);

        event= FirebaseDatabase.getInstance().getReference().child("Events");

        event.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot date_As_keys:snapshot.getChildren()) {

                    String sub_1 = date_As_keys.getKey().substring(4, 7);


                    Calendar calendar = Calendar.getInstance();
                    String sub_text = calendar.get(Calendar.MONTH)+"";

                    String month="";
                    if(sub_text.equals("0")){
                        month="Jan";
                    }
                    if(sub_text.equals("1")){
                        month="Feb";
                    }
                    if(sub_text.equals("2")){
                        month="Mar";
                    }
                    if(sub_text.equals("3")){
                        month="Apr";
                    }
                    if(sub_text.equals("4")){
                        month="May";
                    }
                    if(sub_text.equals("5")){
                        month="Jun";
                    }
                    if(sub_text.equals("6")){
                        month="Jul";
                    }
                    if(sub_text.equals("7")){
                        month="Aug";
                    }
                    if(sub_text.equals("8")){
                        month="Sep";
                    }
                    if(sub_text.equals("9")){
                        month="Oct";
                    }
                    if(sub_text.equals("10")){
                        month="Nov";
                    }
                    if(sub_text.equals("11")){
                        month="Dec";
                    }

                    if (sub_1.equals(month)) {
                        Random random = new Random(System.currentTimeMillis());
                        int style = random.nextInt(2);
                        String str = date_As_keys.getKey();
                        assert str != null;
                        String slice = str.substring(8, 10);
                        if (slice.toCharArray()[0] == '0') {
                            slice = slice.toCharArray()[1] + "";
                        }

                        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(slice));

                        switch (style) {
                            case 0:
                                robotoCalendarView.markCircleImage1(calendar.getTime());
                                break;
                            case 1:
                                robotoCalendarView.markCircleImage2(calendar.getTime());
                                break;
                            default:
                                break;
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       // clearSelectedDayButton.setOnClickListener(v -> robotoCalendarView.clearSelectedDay());

        // Set listener, in this case, the same activity
        robotoCalendarView.setRobotoCalendarListener((RobotoCalendarView.RobotoCalendarListener) this);

        robotoCalendarView.setShortWeekDays(false);

        robotoCalendarView.showDateTitle(true);

        robotoCalendarView.setDate(new Date());

    }
    @Override
    public void onDayClick(Date date) {

        int ch=0;
        String store = null;
        int whiteend=0;
        String str=date+"";
        String slice=str.substring(0,10);
        for(char c:str.toCharArray()){
            ch++;
            if(Character.isWhitespace(c)){
                whiteend++;
            }
            if(whiteend==5){
                String slice2=str.substring(ch,str.length());
                store=slice+" "+slice2;
                break;
            }
        }

        Intent viewEvent=new Intent(Eventsec.this, Eventview.class);
        viewEvent.putExtra("SendingWhichDate",store+"");
        startActivity(viewEvent);

    }

    @Override
    public void onDayLongClick(Date date) {

    }

    @Override
    public void onRightButtonClick() {

    }

    @Override
    public void onLeftButtonClick() {

    }
    @Override
    public void onBackPressed() {
        Intent main_home=new Intent(Eventsec.this,MainActivity.class);
        startActivity(main_home);
        finish();
        super.onBackPressed();

    }

}