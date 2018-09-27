package com.rohan.android.assignments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    Boolean isFirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        isFirstTime = sharedPreferences.getBoolean("firstTime", true);
        if (isFirstTime) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    isFirstTime = false;
                    editor.putBoolean("firstTime", isFirstTime);
                    editor.apply();
                    Intent intent = new Intent(SplashActivity.this, MainMenuActivity.class);
                    startActivity(intent);

                }
            }, 5000);
        } else {
            Intent intent = new Intent(SplashActivity.this, MainMenuActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
