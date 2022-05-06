package com.kfxlabs.smartsociety.Activity;

import static com.google.firebase.iid.FirebaseInstanceId.getInstance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.kfxlabs.smartsociety.MainActivity;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.api.login;
import com.kfxlabs.smartsociety.module.FcmTokenPost;
import com.kfxlabs.smartsociety.module.InfoPost;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    public static final String USER_CREDENTIALS= "com.kfxlabs.smartsociety.UserCredentials";
    com.kfxlabs.smartsociety.api.login loginAPIService;
    com.kfxlabs.smartsociety.api.FcmToken fAPIService ;
    EditText etPhoneNumber;
    EditText etPassword;
    Button btnLogin;
    int buttonMutation;
    ProgressDialog progressDialog;
    LoadingDialog loadingDialog;
    private TextView eRegister;

    public static final String DETAILS = "com.kfxlabs.smartsociety.Details";
    public static String getANDROIDID() {

        return ANDROIDID;
    }

    public static void setANDROIDID(String ANDROIDID) {
        LoginActivity.ANDROIDID = ANDROIDID;
    }

    private static String ANDROIDID;
    private static String password;
    private static String phoneNumber;


 //   @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPhoneNumber = findViewById(R.id.etlPhone);
        etPassword = findViewById(R.id.etlPassword);
        btnLogin = findViewById(R.id.etlbutton);
        loginAPIService = Api.getLoginAPIService(LoginActivity.this);
        fAPIService = Api.getFcmTokenAPIService(LoginActivity.this);

        progressDialog =  new ProgressDialog(LoginActivity.this);
        loadingDialog = new LoadingDialog(LoginActivity.this);


        eRegister = findViewById(R.id.tvRegister);

        eRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        setANDROIDID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));

        etPhoneNumber.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        etPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });


        btnLogin.setOnClickListener(v -> {


                    phoneNumber = etPhoneNumber.getText().toString().trim();
                    password = etPassword.getText().toString();

                    if (phoneNumber.isEmpty() && password.isEmpty()) {
                        etPhoneNumber.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        etPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        Toast.makeText(LoginActivity.this, "Phone Number and Password is Empty", Toast.LENGTH_LONG).show();
                    } else {
                        etPhoneNumber.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        if (!(phoneNumber.length() > 9)) {
                            Toast.makeText(LoginActivity.this, "Phone number must be minimum 10 digits", Toast.LENGTH_LONG).show();
                        } else {
                            logInfoPost(ANDROIDID,phoneNumber, password);
                        }
                    }
         });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("LOGIN");
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(getCurrentFocus()!=null){
            InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            getCurrentFocus().clearFocus();
        }
        return super.dispatchTouchEvent(ev);
    }


    public void logInfoPost( final String androidID,final String uid, final String pwd) {
   // final SharedPreferences.Editor editor = getSharedPreferences(USER_CREDENTIALS, MODE_PRIVATE).edit();
        // Log.d("URL", "sendPost: " + pumpInfoAPIService.toString());
        loginAPIService.savePost(androidID, uid, pwd).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//                Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Success", response.toString());


                int responsecode = response.code();
                if (responsecode == 200) {

                    SharedPrefManager.getInstance(LoginActivity.this);
                    navigate();
                } else {
//
                    Toast.makeText(LoginActivity.this, "Please Enter the correct details", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Failure", t.toString());
            }
        });
    }

    public void navigate() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        finishAffinity();
    }

    boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        assert wifiNetworkInfo != null;
        return activeNetworkInfo != null && (activeNetworkInfo.isConnected()||wifiNetworkInfo.isConnected());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.general_menu, menu);

        return true;
    }



    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}