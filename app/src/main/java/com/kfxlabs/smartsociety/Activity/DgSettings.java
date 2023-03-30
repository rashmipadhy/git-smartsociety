package com.kfxlabs.smartsociety.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.model.DgValueDetails;
import com.kfxlabs.smartsociety.module.Dglist;
import com.kfxlabs.smartsociety.module.SettingGet;
import com.kfxlabs.smartsociety.module.SettingPost;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DgSettings extends AppCompatActivity {

    String u_phone,dev_id;
    public static String getANDROIDID() {
        return ANDROIDID;
    }

    public static void setANDROIDID(String ANDROIDID) {
        DgSettings.ANDROIDID = ANDROIDID;
    }

    private static String ANDROIDID;
    EditText fmax,falert,fload,troutine,toverhaul,toverheat,dgmake,dgmodel,dgrating;
    Button saveSettings;
    Handler handle;
    int buttonMutation;

    ProgressDialog progressDialog;
    LoadingDialog loadingDialog;
    String deviceId = "";

    com.kfxlabs.smartsociety.api.PostSetting postSettingAPIService;
    com.kfxlabs.smartsociety.api.GetSetting getSettingGetAPIService;

    // @SuppressLint({"HardwareIds"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dg_settings);


        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dgmake = findViewById(R.id.et_make);
        dgmodel = findViewById(R.id.et_model);
        dgrating = findViewById(R.id.et_kvaratings);
        fmax = findViewById(R.id.et_maxfuel);
        falert = findViewById(R.id.et_fuelalert);
        fload = findViewById(R.id.et_fuelconsmp);
        troutine = findViewById(R.id.et_routineoil);
        toverhaul = findViewById(R.id.et_majormaint);
        toverheat = findViewById(R.id.et_oiloverheat);
        saveSettings = findViewById(R.id.save_setting);
        buttonMutation = R.drawable.main_button;

        progressDialog =  new ProgressDialog(DgSettings.this);
        loadingDialog = new LoadingDialog(DgSettings.this);






        com.kfxlabs.smartsociety.api.GetSetting mAPIService = Api.getSettingGetAPIService(DgSettings.this);


        setANDROIDID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));

        getSettingGetAPIService = Api.getSettingGetAPIService(DgSettings.this);
        postSettingAPIService = Api.getSettingPostAPIService(DgSettings.this);

        Log.d("tag","on create : mAPIService" + mAPIService);

        loadingDialog.startLoadingDialog();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if(!DgSettings.this.isFinishing() && loadingDialog !=null){
                loadingDialog.dismiss();
            }
        },2000);

        if (getIntent().getStringExtra("device_id") != null) {
            deviceId = getIntent().getStringExtra("device_id");
            settingGetPost(ANDROIDID,dev_id);

        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("DG Settings");
        }


        saveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String dgMakeInput = dgmake.getText().toString().trim();
                final String dgModelInput = dgmodel.getText().toString().trim();
                final String dgRatingInput = dgrating.getText().toString().trim();
                final String fmaxInput = fmax.getText().toString().trim();
                final String falertInput = falert.getText().toString().trim();
                final String floadInput = fload.getText().toString().trim();
                final String troutineInput = troutine.getText().toString().trim();
                final String toverheatInput = toverheat.getText().toString().trim();
                final String toverhaulInput = toverhaul.getText().toString().trim();

                handle = new Handler(getMainLooper()) {
                    @Override
                    public void handleMessage(@NotNull Message msg) {
                        super.handleMessage(msg);
                    }
                };

                if(dgMakeInput.isEmpty()&&dgModelInput.isEmpty()&&dgRatingInput.isEmpty()&&floadInput.isEmpty()&&falertInput.isEmpty()&&fmaxInput.isEmpty()&&troutineInput.isEmpty()&&toverhaulInput.isEmpty()&&toverheatInput.isEmpty()){
                    dgmake.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    dgmodel.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    dgrating.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    fmax.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    falert.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    fload.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    troutine.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    toverheat.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    toverhaul.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(DgSettings.this, "Please enter the Details", Toast.LENGTH_LONG).show();
                }
                else if (dgMakeInput.isEmpty()){
                    dgmake.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(DgSettings.this, "Please enter the DgMake", Toast.LENGTH_LONG).show();
                }
                else if (dgModelInput.isEmpty()){
                    dgmodel.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(DgSettings.this, "Please enter the DgModel", Toast.LENGTH_LONG).show();
                }
                else if (dgRatingInput.isEmpty()){
                    dgrating.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(DgSettings.this, "Please enter the KVA Rating", Toast.LENGTH_LONG).show();
                }
                else if (fmaxInput.isEmpty()){
                    fmax.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(DgSettings.this, "Please enter Max Fuel Tank Capacity", Toast.LENGTH_LONG).show();
                }
                else if (falertInput.isEmpty()){
                    falert.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(DgSettings.this, "Please enter fuel low alert", Toast.LENGTH_LONG).show();
                }
                else if (floadInput.isEmpty()){
                    fload.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(DgSettings.this, "Please enter fuel consumption", Toast.LENGTH_LONG).show();
                }
                else if (toverhaulInput.isEmpty()){
                    toverhaul.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(DgSettings.this, "Please enter the routine oil change", Toast.LENGTH_LONG).show();
                }
                else if (toverheatInput.isEmpty()){
                    toverheat.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(DgSettings.this, "Please enter the major maintenance interval", Toast.LENGTH_LONG).show();
                }
                else if (troutineInput.isEmpty()){
                    troutine.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(DgSettings.this, "Please enter the oil overheat run limit", Toast.LENGTH_LONG).show();
                }
                else {


                    boolean networkAvailable = isNetworkAvailable();
                    if(networkAvailable) {
                        settingPost(ANDROIDID,dev_id,fmaxInput,falertInput,floadInput,troutineInput,toverhaulInput,toverheatInput,dgMakeInput,dgModelInput,dgRatingInput);
                    }
                    else {
                        Toast.makeText(DgSettings.this, "Please Check your internet connection", Toast.LENGTH_LONG).show();
                    }
                }

                setImageButtonEnabled(DgSettings.this, true, saveSettings, buttonMutation);

            }
        });

    }


   public static void setImageButtonEnabled(Context ctxt, boolean enabled,
                                             Button item, int iconResId) {

        item.setEnabled(enabled);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable originalIcon = ctxt.getResources().getDrawable(iconResId);
        Drawable icon = enabled ? originalIcon : convertDrawableToGrayScale(originalIcon);
        item.setBackground(icon);
    }

    public static Drawable convertDrawableToGrayScale(Drawable drawable) {
        if (drawable == null)
            return null;

        Drawable res = drawable.mutate();
        res.setColorFilter(Color.parseColor("#802719b5"), PorterDuff.Mode.SRC_IN);
        return res;
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

    public void settingGetPost(String androidID,String dev_id) {
        Log.d("URL", "sendPost: " + androidID+dev_id);
        getSettingGetAPIService.savePost(androidID,deviceId).enqueue(new Callback<SettingGet>() {
            @Override
            public void onResponse(Call<SettingGet> call, Response<SettingGet> response) {
                if (response.isSuccessful()) {
                    Log.i("POST", "post submitted to API." + response.body().toString());
                    fmax.setText(response.body().getFmax());
                    fload.setText(response.body().getFload());
                    falert.setText(response.body().getFalert());
                    dgmake.setText(response.body().getDgmake());
                    dgmodel.setText(response.body().getDgmodel());
                    dgrating.setText(response.body().getDgrating());
                    troutine.setText(response.body().getTroutine());
                    toverhaul.setText(response.body().getToverhaul());
                    toverheat.setText(response.body().getToverheat());
                }
            }

            @Override
            public void onFailure(Call<SettingGet> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to submit post to API.Response gets failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void settingPost(final String androidID, final String devid, final String fmax, final String falert, final String fload, final String troutine, final String toverhaul, final String toverheat, final String dgmake, final String dgmodel, final String dgrating) {

        Log.d("URL", "sendPost: " + postSettingAPIService.toString());
        postSettingAPIService.savePost(androidID,deviceId,fmax,falert,fload,troutine,toverhaul,toverheat,dgmake,dgmodel,dgrating).enqueue(new Callback<SettingPost>() {
            @Override
            public void onResponse(Call<SettingPost> call, Response<SettingPost> response) {


                Log.i("POST1", ""+androidID+devid+fmax+falert+fload+troutine+toverhaul+toverheat+dgmake+dgmodel+dgrating);
                if (response.isSuccessful()) {
                    if(response.body().getReply().equalsIgnoreCase("OK")) {

                        loadingDialog.startLoadingDialog();

                        Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            if(!DgSettings.this.isFinishing() && loadingDialog != null){
                                loadingDialog.dismiss();
                                finish();
                            }
                        },2000);
                    }
                }else{
                    try {
                        if (response.errorBody() != null) {
                            Toast.makeText(getApplicationContext(), "Error occurred.Try again.", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onFailure(Call<SettingPost> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
                    Toast.makeText(DgSettings.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(DgSettings.this, "Unable to submit post to API.Response gets failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.nav_bar_01, menu);

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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

