package com.kfxlabs.smartsociety.Activity;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.appbar.MaterialToolbar;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.api.RetrofitClient;
import com.kfxlabs.smartsociety.api.Upassword;
import com.kfxlabs.smartsociety.module.UPasswordResponse;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePwdActivity extends AppCompatActivity {
    Button updateUserPasswordbtn;
    EditText CurrentPwd, NewPwd;

    SharedPrefManager sharedPrefManager;

    com.kfxlabs.smartsociety.api.Upassword PwdAPIService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("Update Password");
        }

        //for update password


        CurrentPwd = findViewById(R.id.etPwd);
        NewPwd = findViewById(R.id.newPwd);
        updateUserPasswordbtn = findViewById(R.id.Update);
        PwdAPIService = Api.getPwdAPIService(this);
        toolbar = findViewById(R.id.toolbar);

        updateUserPasswordbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String userCurrentPwd = CurrentPwd.getText().toString().trim();
                String userNewPwd = NewPwd.getText().toString().trim();
                if (userCurrentPwd.isEmpty()) {
                    CurrentPwd.setError("Empty Current Password");
                    CurrentPwd.requestFocus();
                    return;
                }


                if (userNewPwd.isEmpty()) {
                    NewPwd.setError("Empty Current Password");
                    NewPwd.requestFocus();
                    return;
                }

                UpasswordPost(userCurrentPwd, userNewPwd);
            }
        });

    }
    public void UpasswordPost(String pwd, String npwd) {

        Log.d("URL", "sendPost: " + PwdAPIService.toString());
        PwdAPIService.savePost(pwd, npwd).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("password success",response.toString());
                Toast.makeText(ChangePwdActivity.this, "success", Toast.LENGTH_SHORT).show();
                navigate();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ChangePwdActivity.this, "t.", Toast.LENGTH_SHORT).show();
                Log.d("password",t.getMessage());
            }
        });


    }

    public void navigate() {
        Intent intent = new Intent(ChangePwdActivity.this, LoginActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.nav_bar_01, menu);

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




