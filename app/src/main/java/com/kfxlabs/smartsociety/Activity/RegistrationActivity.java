package com.kfxlabs.smartsociety.Activity;

//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
//import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.credentials.Credential;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.module.RegistrationPost;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    ImageButton imgbtnSettings;
    EditText etUserName;
    EditText etPhoneNumber;
    EditText etPassword;
    EditText etEmailID;
    EditText etOrgId;
    Button btRegister;
    ProgressDialog progressDialog;
    private TextView eLogin;

    com.kfxlabs.smartsociety.api.UserRegistration mAPIService = Api.getUserRegAPIService(RegistrationActivity.this);


    // int buttonMutation;
    public static String ANDROIDID;

    public static String getANDROIDID() {

        return ANDROIDID;
    }

    public static void setANDROIDID(String ANDROIDID) {
        RegistrationActivity.ANDROIDID = ANDROIDID;
    }

    public static String userName;
    public static String password;
    public static String phoneNumber;
    public static String emailId;
    public static String orgId;

    //@SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setANDROIDID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
               Settings.Secure.ANDROID_ID));

        btRegister = findViewById(R.id.rbutton);
        etPassword = findViewById(R.id.etrPassword);
        etUserName = findViewById(R.id.etrUserName);
        etPhoneNumber = findViewById(R.id.etrPhone);
        etEmailID = findViewById(R.id.etrEmail);
        etOrgId = findViewById(R.id.etrOrgid);
        eLogin = findViewById(R.id.rLogin);
        progressDialog =  new ProgressDialog(RegistrationActivity.this);

        imgbtnSettings = findViewById(R.id.imagbtnSettings);

        imgbtnSettings.setBackgroundColor(getResources().getColor(R.color.transparent));


        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        imgbtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, com.kfxlabs.smartsociety.Activity.SettingActivity.class);
                startActivity(intent);
            }
        });

        Log.d("TAG","onCreate : mAPIService" + mAPIService);

        final String credentialsFileName = getIntent().getStringExtra("USER_CREDENTIALS");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("USER REGISTRATION");
        }

        etUserName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
       etPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        etPhoneNumber.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        etEmailID.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        etOrgId.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        btRegister.setOnClickListener(v -> {
         userName = etUserName.getText().toString().trim();
         password = etPassword.getText().toString().trim();
         phoneNumber = etPhoneNumber.getText().toString().trim();
         emailId = etEmailID.getText().toString().trim();
         orgId = etOrgId.getText().toString().trim();

         if (userName.isEmpty() && phoneNumber.isEmpty() && orgId.isEmpty()) {
             etUserName.onEditorAction(EditorInfo.IME_ACTION_DONE);
             etPhoneNumber.onEditorAction(EditorInfo.IME_ACTION_DONE);
             Toast.makeText(RegistrationActivity.this, "Please enter the Username,Phone number and orgId", Toast.LENGTH_LONG).show();
         } else if (password.isEmpty()) {
             etPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
             Toast.makeText(RegistrationActivity.this, "Username is Empty", Toast.LENGTH_LONG).show();
         }
         else if (userName.isEmpty()) {
             etUserName.onEditorAction(EditorInfo.IME_ACTION_DONE);
             Toast.makeText(RegistrationActivity.this, "Username is Empty", Toast.LENGTH_LONG).show();
         }
         else if (phoneNumber.isEmpty()) {
             etPhoneNumber.onEditorAction(EditorInfo.IME_ACTION_DONE);
             Toast.makeText(RegistrationActivity.this, "Phone Number is Empty", Toast.LENGTH_LONG).show();
         }
         else if (orgId.isEmpty()) {
             etOrgId.onEditorAction(EditorInfo.IME_ACTION_DONE);
             Toast.makeText(RegistrationActivity.this, "OrgId is Empty", Toast.LENGTH_LONG).show();
         }
         else if (emailId.isEmpty()) {
             etEmailID.onEditorAction(EditorInfo.IME_ACTION_DONE);
             Toast.makeText(RegistrationActivity.this, "EmailId is Empty", Toast.LENGTH_LONG).show();
         }
         else {
               boolean borgId= Pattern.matches("[0-9]{10}", orgId);
             boolean bEmailID = Pattern.matches("^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$", emailId);


             if(!bEmailID)
             {
                 etEmailID.onEditorAction(EditorInfo.IME_ACTION_DONE);
                 Toast.makeText(RegistrationActivity.this, "Invalid email id", Toast.LENGTH_LONG).show();
             }
             if(!borgId)
             {
                 etOrgId.onEditorAction(EditorInfo.IME_ACTION_DONE);
             }
             if (!(phoneNumber.length()>9))
             {
                 Toast.makeText(RegistrationActivity.this, "Phone number must be minimum 10 digits", Toast.LENGTH_LONG).show();
             }
             else if(!bEmailID)
             {
                 Toast.makeText(RegistrationActivity.this, "Please enter the correct EMAIL ID", Toast.LENGTH_LONG).show();
             }
             else {
                 etUserName.onEditorAction(EditorInfo.IME_ACTION_DONE);
                 etPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
                 etPhoneNumber.onEditorAction(EditorInfo.IME_ACTION_DONE);
                 etEmailID.onEditorAction(EditorInfo.IME_ACTION_DONE);
                 etOrgId.onEditorAction(EditorInfo.IME_ACTION_DONE);

                 boolean networkAvailable = isNetworkAvailable();

                 if(networkAvailable) {

                     sendPost(ANDROIDID, password,  userName, phoneNumber,emailId, orgId, credentialsFileName);

                 }
                 else
                 {
                     Toast.makeText(RegistrationActivity.this, "Please Check your internet connection", Toast.LENGTH_LONG).show();
                 }

             }
         }




     });

}


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void sendPost(String androidID, String pwd , String uname, final String uphone,final String umail, final String orgid, final String credentialsFileName) {

        Log.d("URL", "sendPost: " + mAPIService.toString());
        mAPIService.savePost(androidID, pwd, uname, uphone,umail, orgid).enqueue(new Callback<RegistrationPost>() {

             @Override
            public void onResponse(Call<RegistrationPost> call, Response<RegistrationPost> response) {
                if (response.isSuccessful()) {

                    Log.i("POST", "post submitted to API." + response.body().toString());
                    /*SharedPrefManager.getInstance(RegistrationActivity.this).
                            storeUserInfo(uname, uphone, umail);*/


                     SharedPrefManager.getInstance(RegistrationActivity.this).storeUserInfo(uname,uphone,umail,orgid,pwd);
                     SharedPrefManager.getInstance(RegistrationActivity.this).storeUserPwd(pwd);
//RN2611

                   /* System.out.println("value for umail:" + umail);
                    User user = new User(123,"A@gmail.com","Sagar","chris");

                    int id1 = user.getId();
                    System.out.println("Value of id:" + id1);
                    SharedPrefManager.getInstance(RegistrationActivity.this).saveUser(LoginResponse.getUser());*/


                    Toast.makeText(RegistrationActivity.this, response.body().getReply(), Toast.LENGTH_LONG).show();
                    navigate(uphone, credentialsFileName);
                }

                else {
                    Log.i("POST", "failed");
                }
                try {
                    Log.i("POST", "post submitted to API." + response.body().toString());
                    if (response.errorBody() != null) {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(), jObjError.getString("Reply"), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                    Log.e("POST", "post submitted to API." + response.body().toString());
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegistrationPost> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
                    Toast.makeText(RegistrationActivity.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
                }
                else{
                    Log.i("POST", "post failed."+ t.getMessage());
                }
            }


        });
    }



    public void navigate(String phoneNumber, String credentialsFileName) {
        Intent intent = new Intent(RegistrationActivity.this, OtpActivity.class);
        intent.putExtra("androidID", "");
        intent.putExtra("password", password);
        intent.putExtra("phoneNumber", phoneNumber);
        intent.putExtra("userName", userName);
        intent.putExtra("emailID", emailId);
        intent.putExtra("orgId", orgId);
        intent.putExtra("USER_CREDENTIALS", credentialsFileName);
        startActivity(intent);
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selected_item_id = item.getItemId();
        if (selected_item_id == android.R.id.home)
        {
            Intent intent = new Intent(RegistrationActivity.this, com.kfxlabs.smartsociety.Activity.OtpActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1011) {
            if (resultCode == RESULT_OK) {
                com.google.android.gms.auth.api.credentials.Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                if (credential != null) {
                    credential.getId();  //<-- will need to process phone number string
                }
            }
        }
    }



}



