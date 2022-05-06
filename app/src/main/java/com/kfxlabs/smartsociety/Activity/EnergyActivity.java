package com.kfxlabs.smartsociety.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kfxlabs.smartsociety.R;

public class EnergyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("Energy Management");
        }
    }
}