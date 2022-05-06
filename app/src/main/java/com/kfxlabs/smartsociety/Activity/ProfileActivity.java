package com.kfxlabs.smartsociety.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.appbar.MaterialToolbar;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.module.User;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {
    EditText userName,userphone,email,orgid;
    Button uButton;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userName =findViewById(R.id.tv_puser);
        userphone =findViewById(R.id.tv_pphone);
        email =findViewById(R.id.tv_pmail);
        orgid =findViewById(R.id.tv_orgid);
        uButton = findViewById(R.id.Cpwdbtn);

        uButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this,ChangePwdActivity.class);
            startActivity(intent);
        });

//        User user = SharedPrefManager.getInstance(this).getUser();
        String storedUserInfo = SharedPrefManager.getInstance(this).getUserInfo();
        String storedUserphone = SharedPrefManager.getInstance(this).getphoneNumb();

        String storedEmail = SharedPrefManager.getInstance(this).getEmail();

        /*userName.setText("hello  " + ""
                );*/
        userName.setText(storedUserInfo);
        userphone.setText(storedUserphone);
        email.setText(storedEmail);


        toolbar = findViewById(R.id.toolbar_1);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("Profile");
        }


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
