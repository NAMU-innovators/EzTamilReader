package com.example.eztamilreader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eztamilreader.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, OnboardingActivity.class));
            finish();
        }, 1500);
    }
}
