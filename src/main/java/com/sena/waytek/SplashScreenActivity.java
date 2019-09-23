package com.sena.waytek;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent splash = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(splash);
                finish();
            }
    },1000);
    }

}
