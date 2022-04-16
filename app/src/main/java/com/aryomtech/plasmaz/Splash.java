package com.aryomtech.plasmaz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class Splash extends AppCompatActivity {

    private  static  int SPLASH_SCREEN =800;


    Animation blink;
    TextView  txtlogo,textsub;
    Dialog dialogAnimateds;
    ConstraintLayout mainlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        txtlogo=findViewById(R.id.txtlogo);
        textsub=findViewById(R.id.textView4);
        mainlayout=findViewById(R.id.main_layout);

        decodeSampledBitmapFromResource(getResources(), R.id.txtlogo, 100, 100);

        Window window = Splash.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Splash.this, R.color.log_bg));
        window.setNavigationBarColor(ContextCompat.getColor(Splash.this, R.color.log_bg));

        blink = AnimationUtils.loadAnimation(this, R.anim.blink);



        boolean isFirstopen = getSharedPreferences("Openonce", MODE_PRIVATE)
                .getBoolean("isFirstopen", false);
        if (isFirstopen) {

            if(getFreeMemory(Environment.getDataDirectory())<=734003200){
                dialogAnimateds = new Dialog(this, R.style.dialogstyle);
                dialogAnimateds.setContentView(R.layout.warning);
                dialogAnimateds.show();

                Button cancel = dialogAnimateds.findViewById(R.id.button2);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogAnimateds.dismiss();
                    }
                });
            }

            getSharedPreferences("Openonce", MODE_PRIVATE).edit()
                    .putBoolean("isFirstopen", false).apply();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    dialogAnimateds.dismiss();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        },20);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              textsub.setAnimation(blink);
              txtlogo.setAnimation(blink);
            }
        },500);

        getSharedPreferences("Openonce", MODE_PRIVATE).edit()
                .putBoolean("isFirstopen", true).apply();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                boolean isFirstLogin = getSharedPreferences("isloginornot",MODE_PRIVATE)
                        .getBoolean("islogin",true);
                if(isFirstLogin){

                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    Animatoo.animateZoom(Splash.this);
                    finish();

                }
                else{

                    Intent intent = new Intent(Splash.this, login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    Animatoo.animateZoom(Splash.this);
                    finish();

                }
            }
        },SPLASH_SCREEN);

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
    public long getFreeMemory(File path)
    {
        StatFs stats = new StatFs(path.getAbsolutePath());
        return stats.getAvailableBlocksLong() * stats.getBlockSizeLong();
    }
}
