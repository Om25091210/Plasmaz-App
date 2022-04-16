package com.aryomtech.plasmaz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aryomtech.plasmaz.Game.MainMenu;
import com.aryomtech.plasmaz.service.MyService;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView name_of_user;
    Boolean islogin;
    public static final int REQUEST_CODE = 1;
    String getlink;
    private FirebaseAuth mAuth;
    static FirebaseUser useruuid;
    GoogleSignInClient mGoogleSignInClient;
    LinearLayout todo,Event,Timeline,notice,brainch,publicNotice;
    ImageView profile;
    ImageView alert;
    String gender="";
    ImageView avatar;
    ScrollView layout_main;
    TextView welcome;
    int reset_alarm=12;
    int time_check=5;
    DatabaseReference notification,reflink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        layout_main=findViewById(R.id.layout_main);
        welcome=findViewById(R.id.textView);
        name_of_user=findViewById(R.id.textView2);

        gender= getSharedPreferences("GENDER",MODE_PRIVATE)
                .getString("genderbuttonvalue","");

        avatar=findViewById(R.id.imageView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(gender.equals("Male")){
                    avatar.setImageResource(R.drawable.profile);
                }
                else if(gender.equals("Female")){
                    avatar.setImageResource(R.drawable.girlwarrior);
                }
            }
        },1000);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);


            layout_main.setBackgroundResource(R.drawable.homebg);
        }
        else{
            layout_main.setBackgroundResource(R.color.homestatus);
        }



        mAuth = FirebaseAuth.getInstance();
        useruuid = mAuth.getCurrentUser();
        //Variables declaration Block===================================

        Window window = MainActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.homestatus));
        window.setNavigationBarColor(ContextCompat.getColor(MainActivity.this, R.color.homenav));
//////////=================================================================================================================
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(MainActivity.this, MyService.class);
        intent.setAction("reset_data");

        PendingIntent pendingintent = PendingIntent.getBroadcast(MainActivity.this, reset_alarm, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,1);
        calendar.set(Calendar.MINUTE,5);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingintent);
//////////=================================================================================================================

        AlarmManager al_manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent1 = new Intent(MainActivity.this, MyService.class);
        intent1.setAction("check_time");

        PendingIntent pendingintent1 = PendingIntent.getBroadcast(MainActivity.this, time_check, intent1, 0);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY,5);
        calendar1.set(Calendar.MINUTE, 2);

        al_manager.setExact(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingintent1);
//////////=================================================================================================================
        //TODO:Important for storage related functionalities.
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Dexter.withActivity(MainActivity.this)
                    .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {

                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();
        }


        brainch=findViewById(R.id.brainch);
        brainch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent assign=new Intent(MainActivity.this, MainMenu.class);
                startActivity(assign);
                Animatoo.animateZoom(MainActivity.this);
                finish();
            }
        });

        notice=findViewById(R.id.notice);
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent noti=new Intent(MainActivity.this,Notice.class);
                startActivity(noti);
                Animatoo.animateZoom(MainActivity.this);
                finish();
            }
        });

        todo=findViewById(R.id.TODO);
        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toodoo=new Intent(MainActivity.this,Todo.class);
                startActivity(toodoo);
                Animatoo.animateZoom(MainActivity.this);
                finish();
            }
        });

        Event=findViewById(R.id.Event);
        Event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent event=new Intent(MainActivity.this,Eventsec.class);
                startActivity(event);
                Animatoo.animateZoom(MainActivity.this);
                finish();
            }
        });

        Timeline=findViewById(R.id.Timeline);
        Timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent time=new Intent(MainActivity.this,TimeLine.class);
                startActivity(time);
                Animatoo.animateZoom(MainActivity.this);
                finish();
            }
        });

        profile=findViewById(R.id.imageView);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pro=new Intent(MainActivity.this,Profilesec.class);
                startActivity(pro);
                Animatoo.animateZoom(MainActivity.this);
                finish();
            }
        });

        publicNotice=findViewById(R.id.publicNotice);
        publicNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pubnot=new Intent(MainActivity.this,Five_AM_Club.class);
                startActivity(pubnot);
                Animatoo.animateZoom(MainActivity.this);
                finish();
            }
        });

        try {
            name_of_user.setText(useruuid.getDisplayName() + "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String oncelogin = getSharedPreferences("onceforloginandcheck", MODE_PRIVATE)
                .getString("showonlyonceinfirstlogin", "true");

        islogin = getSharedPreferences("isloginornot", MODE_PRIVATE)
                .getBoolean("islogin", false);

        if (!islogin && oncelogin.equals("true")) {   // condition true means user is already login
            Intent i = new Intent(MainActivity.this, login.class);
            startActivity(i);
            finish();
        } else {
            getSharedPreferences("onceforloginandcheck", MODE_PRIVATE).edit()
                    .putString("showonlyonceinfirstlogin", "false").apply();
        }



        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        alert=findViewById(R.id.alert);

        alert.setVisibility(View.GONE);
        notification= FirebaseDatabase.getInstance().getReference().child("Notifications");
        notification.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot resnapshot) {
                long notificationcount = resnapshot.getChildrenCount();
                long checknotifycount = getSharedPreferences("storenotificationcount", MODE_PRIVATE)
                        .getLong("notifycount", 0);

                if (checknotifycount < notificationcount) {
                    try {
                        if (useruuid.getUid().equals("WzjVfwYBp1Njj0dQ2i3Yx5aFC3o2")) {
                            topic topic = new topic();
                            topic.noti("Plasmaz New Notice!!", "Tap To View..");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    alert.setVisibility(View.VISIBLE);

                }
                if (checknotifycount == notificationcount) {
                    alert.setVisibility(View.GONE);

                }
                if (checknotifycount > notificationcount) {

                    getSharedPreferences("storenotificationcount", MODE_PRIVATE).edit()
                            .putLong("notifycount", notificationcount).apply();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.nav_logout){

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mGoogleSignInClient= GoogleSignIn.getClient(this, gso);

            signOut();
            Intent login=new Intent(MainActivity.this, login.class);
            startActivity(login);
        }
        else if(id==R.id.nav_home){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(id==R.id.nav_exams){
            Intent exams=new Intent(MainActivity.this,Exams.class);
            startActivity(exams);
            drawerLayout.closeDrawer(GravityCompat.START);
            finish();
        }
        else if(id==R.id.nav_learn){
            Intent exams=new Intent(MainActivity.this,Learn.class);
            startActivity(exams);
            drawerLayout.closeDrawer(GravityCompat.START);
            finish();
        }
        else if(id==R.id.nav_about_us){
            String url = "https://www.plasmaz.in/";
            Uri uriUrl = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(id==R.id.nav_help){

            reflink = FirebaseDatabase.getInstance().getReference("link");

            reflink.child("app").child("trigger").setValue("yes");
            Query querylink = reflink.child("app");
            querylink.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshotapp) {

                    getlink = dataSnapshotapp.child("contact").getValue(String.class);

                    Uri number = Uri.parse("tel:"+getlink);
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(callIntent);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else if(id==R.id.nav_contribute){
            reflink = FirebaseDatabase.getInstance().getReference("link");

            reflink.child("app").child("trigger").setValue("yes");
            Query querylink = reflink.child("app");
            querylink.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshotapp) {

                    getlink = dataSnapshotapp.child("app").getValue(String.class);

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "NSA HELP");
                    String shareMessage = getlink;
                    intent.putExtra(Intent.EXTRA_TEXT, shareMessage);

                    startActivityForResult(Intent.createChooser(intent, "Share by:"),REQUEST_CODE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {

        getSharedPreferences("onceforloginandcheck", MODE_PRIVATE).edit()
                .putString("showonlyonceinfirstlogin", "true").apply();

        getSharedPreferences("isloginornot",MODE_PRIVATE).edit()
                .putBoolean("islogin",false).apply();

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "SignOut Successfully", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }

}
