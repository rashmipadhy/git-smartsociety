package com.kfxlabs.smartsociety.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.module.Profile;
import com.kfxlabs.smartsociety.module.User;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    EditText userName,userphone,email,cname;
    Button uButton;
    MaterialToolbar toolbar;
    com.kfxlabs.smartsociety.api.GetProfile mAPIService = Api.getGetProfileAPIService(ProfileActivity.this);
    com.kfxlabs.smartsociety.api.GetProfile getProfileAPIService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userName =findViewById(R.id.tv_puser);
        userphone =findViewById(R.id.tv_pphone);
        email =findViewById(R.id.tv_pmail);
       // orgid =findViewById(R.id.tv_orgid);
        uButton = findViewById(R.id.Cpwdbtn);
        cname =findViewById(R.id.tv_orgid);
        getProfileAPIService = Api.getGetProfileAPIService(ProfileActivity.this);

        uButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this,ChangePwdActivity.class);
            startActivity(intent);
        });


        Log.d("tag","oncreate : mAPIService" + mAPIService);
        profileGet();

        toolbar = findViewById(R.id.toolbar_1);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("Profile");
        }
    }

    public void profileGet(){
        mAPIService.savePost().enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()){
                    Log.i("POST", "post submitted to API." + response.body().toString());
                      userName.setText(response.body().getUname());
                      userphone.setText(response.body().getPhone());
                    email.setText(response.body().getEmail());
                    cname.setText(response.body().getCname());

                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to submit post to API.Response gets failed", Toast.LENGTH_LONG).show();

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.general_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selected_item_id = item.getItemId();
        if (selected_item_id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
