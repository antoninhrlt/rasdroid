package com.raslib.rasdroid;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Load MainActivity when it's loaded
 * The SplashScreen look like @style/Theme.Rasdroid
 */
public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity( new Intent( getApplicationContext(), MainActivity.class ));
        overridePendingTransition(0, 0);
        finish();
    }
}
