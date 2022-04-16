package com.aryomtech.plasmaz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Learn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
    }
    @Override
    public void onBackPressed() {
        Intent main_home=new Intent(Learn.this,MainActivity.class);
        startActivity(main_home);
        finish();
        super.onBackPressed();

    }
}