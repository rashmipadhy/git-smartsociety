package com.kfxlabs.smartsociety.Activity;

import static com.google.firebase.iid.FirebaseInstanceId.getInstance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.module.OtpPost;
import com.kfxlabs.smartsociety.module.RegistrationPost;

import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {

    private PinView pinView;
    Button btOtpVerify;
    TextView tvResendOtp;
    TextView tvOtpTimer;
    com.kfxlabs.smartsociety.api.OtpVerification mAPIService;
    com.kfxlabs.smartsociety.api.UserRegistration rAPIService ;
    com.kfxlabs.smartsociety.api.FcmToken fAPIService ;
    com.kfxlabs.smartsociety.api.Info InfoAPIService;
    String androidId;
    String phoneNumber,email,orgId;
    boolean resendLinkEnable = false;
    //    public static final String DG_DETAILS = "com.example.motorApplication.DgDetails";
    private static String app = "";
    Handler handle;
    int buttonMutation;
    ProgressDialog progressDialog;
    LoadingDialog loadingDialog;
    private static final String TAG = "OtpActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        final String credentialsFileName = getIntent().getStringExtra("USER_CREDENTIALS");
//        SharedPreferences prefs = getSharedPreferences(DG_DETAILS, MODE_PRIVATE);
//        String jsonObject = prefs.getString("DG_DETAILS", "");

        phoneNumber = getIntent().getStringExtra("phoneNumber");
        email = getIntent().getStringExtra("emailID");
        orgId = getIntent().getStringExtra("orgId");
        //androidId = RegistrationActivity.getANDROIDID();
        androidId = getAndroidId();

        final String userName = RegistrationActivity.userName;
        pinView = findViewById(R.id.etOtp);
        btOtpVerify = findViewById(R.id.btOtpVerify);
        tvResendOtp = findViewById(R.id.tvResendOtp);
        tvOtpTimer =  findViewById(R.id.tvOtpTimer);

        progressDialog =  new ProgressDialog(OtpActivity.this);
        loadingDialog = new LoadingDialog(OtpActivity.this);


        fAPIService = Api.getFcmTokenAPIService(OtpActivity.this);

        mAPIService = Api.getOtpAPIService(OtpActivity.this);
        rAPIService = Api.getUserRegAPIService(OtpActivity.this);
        InfoAPIService = Api.getInfoAPIService();

        tvResendOtp.setMovementMethod(LinkMovementMethod.getInstance());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("Verification");
        }


        pinView.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        runOnUiThread(() -> {
            if (!resendLinkEnable) {
                tvResendOtp.setEnabled(false);
                tvResendOtp.setLinksClickable(false);
                tvResendOtp.setClickable(false);
                tvResendOtp.setTextColor(Color.parseColor("#CCCBCEE6"));
                otpTimer(10000, 1000, true);
            }
            else {
                tvResendOtp.setEnabled(true);
                tvResendOtp.setLinksClickable(true);
                tvResendOtp.setClickable(true);
                tvResendOtp.setTextColor(Color.parseColor("#2719b5"));
            }

        });

        //TODO: read SMS should be a different function so that when the resend button is activated it can be called
        //TODO: resend button creation and call the api when clicked, grey out until the time is over
        // TODO: set a timer for resend button, if otp not entered or read call the userregpost api again

        btOtpVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int otp = Integer.parseInt(etOtp.getText().toString().trim());
                String otp = Objects.requireNonNull(pinView.getText()).toString();

                handle = new Handler(getMainLooper()) {

                };

                pinView.onEditorAction(EditorInfo.IME_ACTION_DONE);
                if(otp.length() == 6) {
                    otpPost(androidId, otp, phoneNumber,credentialsFileName);
                }
                else
                {
                    Toast.makeText(OtpActivity.this, "OTP should be 6 digits", Toast.LENGTH_LONG).show();
                }


            }
        });

        tvResendOtp.setOnClickListener(v -> {
            tvResendOtp.setEnabled(false);
            tvResendOtp.setTextColor(Color.parseColor("#CCCBCEE6"));
            tvResendOtp.setLinksClickable(false);
            runOnUiThread(() -> otpTimer(10000, 1000, true));
            sendPost("",app, userName, phoneNumber,email,orgId);

        });

    }

    private String getAndroidId() {
        return Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void otpPost(final String androidID, String otp, final String uphone, String credentialsFileName) {
        final SharedPreferences.Editor editor = getSharedPreferences(credentialsFileName, MODE_PRIVATE).edit();
        Log.d("URL", "sendPost: " + mAPIService.toString());
        mAPIService.savePost(androidID, otp, uphone).enqueue(new Callback<OtpPost>() {
            @Override
            public void onResponse(Call<OtpPost> call, Response<OtpPost> response) {

                if (response.isSuccessful()) {
                    Log.i("POST", "post submitted to API." + response.body().toString());
                    if (response.body().getReply().equals("OK")) {
                        //editor.putString("user_name", etUserName);
                        editor.putString("phone_number", uphone);
                        editor.putString("email", email);
                        editor.putString("orgId", orgId);
                        editor.apply();
//                        dgInfoPost(androidID,uphone,app,dgDetailsFileName);


                        Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            if(!OtpActivity.this.isFinishing() ){

                                navigate();
                            }
                        },2000);
//                        Toast.makeText(DgOtpActivity.this, response.body().getReply(), Toast.LENGTH_LONG).show();
//                        navigate();
                        Log.d(TAG, "onResponse: success");
                    } else if (response.body().getReply().equals("Invalid PIN")) {
                        Toast.makeText(OtpActivity.this, response.body().getReply(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onResponse: "+ response.toString());
                    }
                } else {
                    Log.d(TAG, "onResponse:" + response.toString());
                }

            }

            @Override
            public void onFailure(Call<OtpPost> call, Throwable t) {
                Log.e("POST", "Unable to submit post to API.Please check the URL");
            }
        });
    }

//    public void dgInfoPost(final String androidID, final String uphone, String app, final String dgDetailsFileName) {
//        final Gson gson = new Gson();
//        Log.d("URL", "sendPost: " + dgInfoAPIService.toString());
//        dgInfoAPIService.savePost(androidID, app, uphone).enqueue(new Callback<DgInfoPost>() {
//            @Override
//            public void onResponse(Call<DgInfoPost> call, Response<DgInfoPost> response) {
//
//                if (response.isSuccessful()) {
//                    Log.i("POST1", "post submitted to API." + response.body().toString());
//                    if(!(response.body().getUname()).isEmpty()) {
//                        String responseStr = gson.toJson(response.body());
//                        SharedPreferences.Editor editor = getSharedPreferences(dgDetailsFileName, MODE_PRIVATE).edit();
//                        editor.clear();
//                        editor.putString("DG_DETAILS", responseStr);
//                        editor.apply();
//                        loadingDialog.startLoadingDialog();
//
//                        Handler handler = new Handler();
//                        handler.postDelayed(() -> {
//                            if(!DgOtpActivity.this.isFinishing() && loadingDialog !=null){
//                                loadingDialog.dismiss();
//                            }
//                        },2000);
////                        navigate();
//                    }
//                }
//                try {
//                    if (response.errorBody() != null) {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        Toast.makeText(getApplicationContext(), jObjError.getString("Reply"), Toast.LENGTH_LONG).show();
//                    }
//                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<DgInfoPost> call, Throwable t) {
//                if (t instanceof SocketTimeoutException) {
//                    Log.e("POST", "Unable to submit post to API.Connection time out");
//                    Toast.makeText(DgOtpActivity.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }

    /*public void fcmtokenPost(final String androidId,final String uphone){
        getInstance().getInstanceId().addOnSuccessListener(OtpActivity.this, task -> {
            if (task!=null){  String token = Objects.requireNonNull(task.getToken());
                fAPIService.savePost(androidId,uphone,token).enqueue(new Callback<FcmTokenPost>() {
                    @Override
                    public void onResponse(Call<FcmTokenPost> call, Response<FcmTokenPost> response) {
                        if (response.isSuccessful()){
                            if(response.body().getReply().equals("OK")){
                         Toast.makeText(OtpActivity.this, response.body().getReply(), Toast.LENGTH_LONG).show();

                                if(isNetworkAvailable()){
                                    navigate();
                                }else{
                                    Toast.makeText(OtpActivity.this, "Please check the internet connection", Toast.LENGTH_SHORT).show();
                                }

                            }else {
                                Toast.makeText(OtpActivity.this, response.body().getReply(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FcmTokenPost> call, Throwable t) {
                        Log.e("POST", "Unable to submit post to API.Please check the URL");
                    }
                });
            }else{
                Toast.makeText(OtpActivity.this, "Unsuccessful response", Toast.LENGTH_LONG).show();
            }
        });
    }*/

    public void sendPost(String androidID,String app, String uname, final String uphone,String umail,String orgId) {

        Log.d("URL", "sendPost: " + rAPIService.toString());
        rAPIService.savePost(androidID,app, uname, uphone,email,orgId).enqueue(new Callback<RegistrationPost>() {
            @Override
            public void onResponse(Call<RegistrationPost> call, Response<RegistrationPost> response) {

                if (response.isSuccessful()) {
                    Log.i("POST", "post submitted to API." + response.body().toString());
//                    Toast.makeText(DgOtpActivity.this, response.body().getReply(), Toast.LENGTH_LONG).show();

                }
                try {
                    if (response.errorBody() != null) {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(), jObjError.getString("Reply"), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegistrationPost> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
                    Toast.makeText(OtpActivity.this, "Unable to submit post to API.Connection time out", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void navigate() {
        Intent intent = new Intent(OtpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        finishAffinity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.general_menu, menu);

        return true;
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selected_item_id = item.getItemId();
        if (selected_item_id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void otpTimer(final long totalTime, final long interval, boolean enable){
        final long seconds = totalTime / interval;
        final long minutes = seconds / 60;
        if(enable)
        {

            new CountDownTimer(totalTime, interval) {
                long m = minutes;
                long s = seconds;
                long second;
                String sec ;
                public void onTick(long totalTime) {

                    if (s % 60 == 0)
                    {
                        second = 60;
                        sec = "00";
                    }
                    else {
                        if (second == 59){
                            m = m -1;
                        }
                        if (s < 60)
                        {
                            second = s;
                        }
                        sec = String.valueOf(second);
                    }
                    if(sec.length()!=2)
                    {
                        sec = "0" + sec;
                    }
                    tvOtpTimer.setText(String.format("%s%s%s", m, ":", sec));
                    s = s - 1;
                    second =  second - 1;
                    //here you can have your logic to set text to edittext
                }

                public void onFinish() {
                    tvOtpTimer.setText(String.format("%s%s%s", "0", ":", "00"));
                    tvResendOtp.setTextColor(Color.parseColor("#2719b5"));
                    tvResendOtp.setEnabled(true);
                    tvResendOtp.setLinksClickable(true);
                    tvResendOtp.setClickable(true);
                    resendLinkEnable =  true;
                }

            }.start();
        }
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
}