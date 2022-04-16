package com.aryomtech.plasmaz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class login extends AppCompatActivity {

    private  static  int SPLASH_SCREEN =2500;

    ImageView imageView2;
    Button button;
    Animation top, bottom;
    DatabaseReference reflink;
    GoogleSignInClient mGoogleSignInClient;
    String getlink;
    ImageView imageView34;
    GoogleSignInClient agooglesigninclient;
    ImageView signinbutton;
    private static final int RC_SIGN_IN = 101;
    ProgressDialog pd;
    TextView  txtlogo,textsub;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    boolean only_once=true;
    DatabaseReference reference,Verify;
    Animation blink;

    CheckBox male;
    CheckBox female;
    String gender;

    DatabaseReference five_am_club;

    ConstraintLayout mainlayout;
    int once=0;
    ImageView insta,whatsapp,phone,ImageView22;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Intent intent=getIntent();

        male=findViewById(R.id.checkBox2);
        female=findViewById(R.id.checkBox);

        mainlayout=findViewById(R.id.mainlayout_main);

        decodeSampledBitmapFromResource(getResources(), R.id.imageView34, 100, 100);
        insta=findViewById(R.id.imageView23);
        whatsapp=findViewById(R.id.imageView24);
        phone=findViewById(R.id.imageView25);
        ImageView22=findViewById(R.id.imageView22);

        imageView2 = findViewById(R.id.imageView2);
        button = findViewById(R.id.button);
        txtlogo=findViewById(R.id.txtlogo);
        textsub=findViewById(R.id.textView4);
        imageView34=findViewById(R.id.imageView34);

        five_am_club = FirebaseDatabase.getInstance().getReference().child("five_am_club");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    insta.setImageResource(R.drawable.insta);
                    whatsapp.setImageResource(R.drawable.whatsapp);
                    phone.setImageResource(R.drawable.phone);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        },2000);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            blink = AnimationUtils.loadAnimation(this, R.anim.blink);
    /*        textsub.setAnimation(blink);
            txtlogo.setAnimation(blink);*/
           /* imageView34.setAnimation(blink);*/

            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

            Window window = login.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(login.this, R.color.log_bg));
            window.setNavigationBarColor(ContextCompat.getColor(login.this, R.color.log_bg));

            mainlayout.setBackgroundResource(R.color.log_bg);
        }

        getSharedPreferences("isloginornot",MODE_PRIVATE).edit()
                .putBoolean("islogin",false).apply();



        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/_u/plasmaz.in");


                Intent i= new Intent(Intent.ACTION_VIEW,uri);

                i.setPackage("com.instagram.android");

                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {

                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/xxx")));
                }
            }
        });

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String smsNumber="917999502978@s.whatsapp.net";
                Uri uri = Uri.parse("smsto:" + smsNumber);
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.setPackage("com.whatsapp");
                startActivity(i);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });




        top = AnimationUtils.loadAnimation(this, R.anim.top);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom);
        button.setAnimation(top);
        imageView2.setAnimation(bottom);


        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //
        }


        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).apply();

        signinbutton = findViewById(R.id.imageView2);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        agooglesigninclient = GoogleSignIn.getClient(this,gso);
        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(male.isChecked() && !female.isChecked() || !male.isChecked() && female.isChecked()) {
                    signIn();
                }
                else{
                    Toast.makeText(login.this, "Please select the gender.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(male.isChecked() && !female.isChecked() || !male.isChecked() && female.isChecked()) {
                    signIn();
                }
                else{
                    Toast.makeText(login.this, "Please select the gender.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void signIn() {
        Intent SignInIntent = agooglesigninclient.getSignInIntent();
        startActivityForResult(SignInIntent,RC_SIGN_IN);
        pd = new ProgressDialog(this);
        pd.setTitle("Signing You In!");
        pd.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGN_IN){

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);


            } catch (ApiException e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            pd.dismiss();
                            user = mAuth.getCurrentUser();
                            Toast.makeText(login.this, user.getEmail(), Toast.LENGTH_SHORT).show();
                            String DeviceToken= FirebaseInstanceId.getInstance().getToken();
                            String displayname;
                            String name = user.getDisplayName();

                            displayname = name.replaceAll("[^a-zA-Z0-9]","");

                            reference.child(user.getUid()).child(displayname).child("Email").setValue(user.getEmail());
                            reference.child(user.getUid()).child(displayname).child("Name").setValue(user.getDisplayName());
                            reference.child(user.getUid()).child(displayname).child("Token").setValue(DeviceToken);

                            five_am_club.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(!snapshot.child(user.getUid()).exists() && only_once){
                                        five_am_club.child(user.getUid()).child("name").setValue(user.getDisplayName());
                                        five_am_club.child(user.getUid()).child("legend").setValue("no");
                                        five_am_club.child(user.getUid()).child("dp").setValue("no dp");
                                        five_am_club.child(user.getUid()).child("points").setValue(0);
                                        five_am_club.child(user.getUid()).child("uid").setValue(user.getUid());
                                    }
                                    only_once=false;

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            updateUI(user);

                        }
                        else{
                            Toast.makeText(login.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(male.isChecked() && !female.isChecked() || !male.isChecked() && female.isChecked()) {

            if(male.isChecked()){
                gender="Male";
                getSharedPreferences("GENDER",MODE_PRIVATE).edit()
                        .putString("genderbuttonvalue","Male").apply();
            }
            if(female.isChecked()){
                gender="Female";
                getSharedPreferences("GENDER",MODE_PRIVATE).edit()
                        .putString("genderbuttonvalue","Female").apply();
            }

            Verify = FirebaseDatabase.getInstance().getReference().child("RegisteredEmails");
            Verify.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot key : snapshot.getChildren()) {

                        if (snapshot.child(key.getKey()).getValue(String.class).equals(user.getUid())) {
                            once = 1;
                            getSharedPreferences("isloginornot", MODE_PRIVATE).edit()
                                    .putBoolean("islogin", true).apply();

                            Intent intent = new Intent(login.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            Animatoo.animateZoom(login.this);
                            finish();

                        }
                    }
                    if (once == 0) {
                        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(getString(R.string.default_web_client_id))
                                .requestEmail()
                                .build();
                        mGoogleSignInClient = GoogleSignIn.getClient(login.this, gso);
                        five_am_club.child(user.getUid()).removeValue();
                        signOut();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
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
                        Toast.makeText(login.this, "Email Not Registered!!", Toast.LENGTH_LONG).show();
                        Snackbar.make(findViewById(android.R.id.content),"Please Contact us to Register.",Snackbar.LENGTH_LONG).show();
                    }
                });
    }
}