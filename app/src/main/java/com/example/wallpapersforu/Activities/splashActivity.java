package com.example.wallpapersforu.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wallpapersforu.R;

public class splashActivity extends AppCompatActivity {
    Handler mhandler;
    Runnable mrunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mhandler = new Handler();
        mrunnable = () -> {
            Intent i = new Intent(splashActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        };
        mhandler.postDelayed(mrunnable,4000);




    }
}