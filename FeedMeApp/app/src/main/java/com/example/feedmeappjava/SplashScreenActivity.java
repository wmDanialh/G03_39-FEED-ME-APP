package com.example.feedmeappjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN =5000;

    ProgressBar progressBar;

    private int mProgressStatus;

    private Handler mHandler = new Handler();

    Animation topAnim, bottomAnim,trademarkAnim;
    ImageView image;
    TextView logo, slogan,copyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.activity_splash);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        trademarkAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        progressBar = findViewById(R.id.progressBar);
        image = findViewById(R.id.imageView32);
        slogan = findViewById(R.id.text);
        copyright = findViewById(R.id.textTradeMark);

        image.setAnimation(topAnim);
        slogan.setAnimation(bottomAnim);
        copyright.setAnimation(trademarkAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgressStatus <100){
                    mProgressStatus++;
                    android.os.SystemClock.sleep(50);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {progressBar.setProgress(mProgressStatus);
                        }
                    });
                }
            }
        });

    }
}