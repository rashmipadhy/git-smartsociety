package com.kfxlabs.smartsociety.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;

public class CedarbolckActivity extends AppCompatActivity {

    com.kfxlabs.smartsociety.api.GetTanklist mAPIService = Api.getTanklistAPIService(CedarbolckActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cedarbolck);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}