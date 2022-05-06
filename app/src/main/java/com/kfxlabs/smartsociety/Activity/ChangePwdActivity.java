package com.kfxlabs.smartsociety.Activity;

import androidx.annotation.ContentView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    MaterialToolbar toolbar;
    /* private static String Cpassword;
     private static String Npassword;*/
    com.kfxlabs.smartsociety.api.Upassword PwdAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

   /* public View CreateView(@Nullable LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.activity_change_pwd, container, false);
}*/
      /*  super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
*/
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("Update Password");
        }
        //userPhone=sharedPrefManager.getphoneNumb();

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
  /*      PwdAPIService.savePost(pwd, npwd).enqueue(new Callback<UPasswordResponse>() {
            @Override
            public void onResponse(Call<UPasswordResponse> call, Response<UPasswordResponse> response) {
                Log.d("password success",response.body().getReply());
                Toast.makeText(ChangePwdActivity.this, "success", Toast.LENGTH_SHORT).show();
                navigate();

            }

            @Override
            public void onFailure(Call<UPasswordResponse> call, Throwable t) {
                Toast.makeText(ChangePwdActivity.this, "t.", Toast.LENGTH_SHORT).show();
                Log.d("password",t.getMessage());
            }

        });*/

    }

    public void navigate() {
        Intent intent = new Intent(ChangePwdActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}

/*@Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.Update:
                updateUserPassword();
                break;


        }


    }*/

    /*private void updateUserPassword() {


        String userCurrentPwd = CurrentPwd.getText().toString().trim();
        String userNewPwd = NewPwd.getText().toString().trim();

        if (userCurrentPwd.isEmpty()) {
            CurrentPwd.setError("Empty Current Password");
            CurrentPwd.requestFocus();
            return;
        }

        if (userCurrentPwd.length() < 8) {
            CurrentPwd.setError("Empty 8 Digit Password");
            CurrentPwd.requestFocus();
            return;
        }
        if (userNewPwd.isEmpty()) {
            NewPwd.setError("Empty Current Password");
            NewPwd.requestFocus();
            return;
        }

        if (userNewPwd.length() < 8) {
            NewPwd.setError("Empty 8 Digit Password");
            NewPwd.requestFocus();
            return;
        }
   *//*     if (TextUtils.isEmpty(NewPwd.getText())) {
            NewPwd.setError("Empty Current Password");
            NewPwd.requestFocus();
            return;
        }

        if (NewPwd.length() < 8) {
            NewPwd.setError("Empty 8 Digit Password");
            NewPwd.requestFocus();
            return;
        }*//*


            Call<UPasswordResponse> call = Api.getPwdAPIService().savePost(userCurrentPwd, userNewPwd);

            call.enqueue(new Callback<UPasswordResponse>() {
                @Override
                public void onResponse(Call<UPasswordResponse> call, Response<UPasswordResponse> response) {

                    UPasswordResponse passwordResponse = response.body();

                    if (response.isSuccessful()) {


                            Toast.makeText(ChangePwdActivity.this, "tytimed out", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<UPasswordResponse> call, Throwable t) {
                    Toast.makeText(ChangePwdActivity.this, "timed out", Toast.LENGTH_LONG).show();
                }
            });
        }
    */

   /* public void UpasswordPost(String pwd, String npwd) {

        Log.d("URL", "sendPost: " + PwdAPIService.toString());
        PwdAPIService.savePost(pwd, npwd).enqueue(new Callback<UPasswordResponse>() {
            @Override
            public void onResponse(Call<UPasswordResponse> call, Response<UPasswordResponse> response) {

                Toast.makeText(ChangePwdActivity.this, "success", Toast.LENGTH_SHORT).show();
                navigate(pwd, npwd);


            }

            @Override
            public void onFailure(Call<UPasswordResponse> call, Throwable t) {

            }

        });

    }

    public void navigate(String pwd, String npwd) {
        Intent intent = new Intent(ChangePwdActivity.this, LoginActivity.class);
    }
}
*/
     /*   Call<String>
        Call<UPasswordResponse> call = call.enqueue(new Callback<UPasswordResponse>() {
            @Override
            public void onResponse(Call<UPasswordResponse> call, Response<UPasswordResponse> response) {
                UPasswordResponse passwordResponse= response.body();

            if(response.isSuccessful()){


            }
            }


            @Override
            public void onFailure(Call<UPasswordResponse> call, Throwable t) {

            }
        });
    }*/

