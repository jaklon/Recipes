package com.example.recipesapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipes.databinding.ActivitySplashBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private int splashScreenTime = 3000; // 3 seconds
    private int timeInterval = 100; // 0.1 seconds
    private int progress = 0; // 0 to 100 for progress bar
    private Runnable runnable;
    private Handler handler;

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.progressBar.setMax(splashScreenTime);
        binding.progressBar.setProgress(progress);
        handler = new Handler(Looper.getMainLooper());
        runnable = () -> {
            // This code will check if splash screen time is completed or not
            if (progress < splashScreenTime) {
                progress += timeInterval;
                binding.progressBar.setProgress(progress);
                handler.postDelayed(runnable, timeInterval);
            } else {
                // navigate to login activity
                FirebaseApp.initializeApp(this);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                startActivity(user != null ? new Intent(SplashActivity.this, MainActivity.class) : new Intent(SplashActivity.this, LoginActivity.class));
                finish();
                }
        };
        handler.postDelayed(runnable, timeInterval); // Start handler with delay
    }
}
