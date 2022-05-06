package com.kfxlabs.smartsociety.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.api.Upassword;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button dgbtn ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dgbtn = findViewById(R.id.dg_img);

        dgbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DgActivity.class);
                startActivity(intent);

            }
        });

    }}
