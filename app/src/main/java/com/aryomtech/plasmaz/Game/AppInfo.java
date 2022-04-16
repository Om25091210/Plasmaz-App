package com.aryomtech.plasmaz.Game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.aryomtech.plasmaz.R;

import java.net.URL;



public class AppInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        TextView versionText = findViewById(R.id.versionText);
        TextView versionText2 = findViewById(R.id.versionText2);
        TextView textView6 = findViewById(R.id.textView6);
        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = pInfo.versionName;
            versionText.setText(String.format("Plasmaz v%s",versionName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean darkModePref = sharedPref.getBoolean(SettingsActivity.KEY_DARK_MODE_SWITCH, false);
        if(darkModePref){
            ConstraintLayout constraintLayout = (findViewById(R.id.infobody));
            constraintLayout.setBackgroundColor(getResources().getColor(R.color.qboard_black));
        }
    }

    public void close(View view){
        this.finish();
    }
}
