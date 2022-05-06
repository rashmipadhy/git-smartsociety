package com.kfxlabs.smartsociety.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kfxlabs.smartsociety.R;

public class PumpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pump);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("Pump Monitoring");
        }
    }
}