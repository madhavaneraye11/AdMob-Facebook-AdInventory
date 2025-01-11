package com.adsprovider.combineadinventory;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.adsprovider.combineadinventory.util.AppStart;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (AppStart.getNews() != null && !AppStart.getNews().isEmpty()){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }

        // Set listener for updates from AppStart
        AppStart.setUpdateListener(updatedDevices -> runOnUiThread(() -> {
            if (!updatedDevices.isEmpty()) {
                startActivity(new Intent(this,MainActivity.class));
                finish();
            }
        }));
    }
}