package com.ryfsystems.smi.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ryfsystems.smi.BuildConfig;
import com.ryfsystems.smi.R;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    String programVersion;
    TextView tvSplashVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        programVersion = "V." + BuildConfig.VERSION_NAME;

        tvSplashVersion = findViewById(R.id.tvSplashVersion);
        tvSplashVersion.setText(programVersion);

        new Handler().postDelayed(() -> {
            Intent nextActivity;
            SharedPreferences preferences = getSharedPreferences("smiPreferences", Context.MODE_PRIVATE);
            boolean sesion = preferences.getBoolean("sesion", false);
            String role = preferences.getString("role", "");

            if (sesion) {
                if (!role.isEmpty()) {
                    nextActivity = new Intent(getApplicationContext(), MainActivity.class);
                } else {
                    nextActivity = new Intent(getApplicationContext(), LoginActivity.class);
                }
            } else {
                nextActivity = new Intent(getApplicationContext(), LoginActivity.class);
            }
            startActivity(nextActivity);
            finish();
        }, 3000);
    }
}