package com.kfxlabs.smartsociety.Activity;

import androidx.annotation.NonNull;
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
import com.kfxlabs.smartsociety.module.SettingGet;
import com.kfxlabs.smartsociety.module.SettingPost;
import com.kfxlabs.smartsociety.module.SettingPumpGet;
import com.kfxlabs.smartsociety.module.SettingPumpPost;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PumpSetting extends AppCompatActivity {

    String u_phone,dev_id;
    public static String getANDROIDID() {
        return ANDROIDID;
    }

    public static void setANDROIDID(String ANDROIDID) {
        PumpSetting.ANDROIDID = ANDROIDID;
    }

    private static String ANDROIDID;
    EditText prunmax,troutine,toverhaul,pmake,pmodel,prating;
    Button saveSettings;
    Handler handle;
    int buttonMutation;

    ProgressDialog progressDialog;
    LoadingDialog loadingDialog;
    String deviceId = "";

    com.kfxlabs.smartsociety.api.PostPumpSettings postPumpSettingsAPIService;
    com.kfxlabs.smartsociety.api.GetPumpSettings getPumpSettingsGetAPIService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pump_setting);

        pmake = findViewById(R.id.et_make);
        pmodel = findViewById(R.id.et_model);
        prating = findViewById(R.id.et_kvaratings);
        prunmax = findViewById(R.id.et_maxfuel);
        troutine = findViewById(R.id.et_routineoil);
        toverhaul = findViewById(R.id.et_oiloverheat);
        saveSettings = findViewById(R.id.save_setting);
        buttonMutation = R.drawable.main_button;
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadingDialog = new LoadingDialog(PumpSetting.this);

        com.kfxlabs.smartsociety.api.GetPumpSettings mAPIService = Api.getPumpSettingsGetAPIService(PumpSetting.this);

        setANDROIDID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));

        getPumpSettingsGetAPIService = Api.getPumpSettingsGetAPIService(PumpSetting.this);
        postPumpSettingsAPIService = Api.getPumpSettingsPostAPIService(PumpSetting.this);


        Log.d("tag","on create : mAPIService" + mAPIService);


        loadingDialog.startLoadingDialog();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if(!PumpSetting.this.isFinishing() && loadingDialog !=null){
                loadingDialog.dismiss();
            }
        },2000);

        if (getIntent().getStringExtra("device_id") != null) {
            deviceId = getIntent().getStringExtra("device_id");
            settingPumpGetPost(ANDROIDID,dev_id);

        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("Pump Settings");
        }


        saveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pMakeInput = pmake.getText().toString().trim();
                final String pModelInput = pmodel.getText().toString().trim();
                final String pRatingInput = prating.getText().toString().trim();
                final String prunmaxInput = prunmax.getText().toString().trim();
                final String troutineInput = troutine.getText().toString().trim();
                final String toverhaulInput = toverhaul.getText().toString().trim();

                handle = new Handler(getMainLooper()) {
                    @Override
                    public void handleMessage(@NotNull Message msg) {
                        super.handleMessage(msg);
                    }
                };

                if(pMakeInput.isEmpty()&&pModelInput.isEmpty()&&pRatingInput.isEmpty()&&prunmaxInput.isEmpty()&&troutineInput.isEmpty()&&toverhaulInput.isEmpty()){
                    pmake.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    pmodel.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    prating.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    prunmax.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    troutine.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    toverhaul.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(PumpSetting.this, "Please enter the Details", Toast.LENGTH_LONG).show();
                }
                else if (pMakeInput.isEmpty()){
                    pmake.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(PumpSetting.this, "Please enter the PMake", Toast.LENGTH_LONG).show();
                }
                else if (pModelInput.isEmpty()){
                    pmodel.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(PumpSetting.this, "Please enter the PModel", Toast.LENGTH_LONG).show();
                }
                else if (pRatingInput.isEmpty()){
                    prating.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(PumpSetting.this, "Please enter the KVA Rating", Toast.LENGTH_LONG).show();
                }
                else if (prunmaxInput.isEmpty()){
                    prunmax.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(PumpSetting.this, "Please enter Max Fuel Tank Capacity", Toast.LENGTH_LONG).show();
                }
                else if (toverhaulInput.isEmpty()){
                    toverhaul.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(PumpSetting.this, "Please enter the routine oil change", Toast.LENGTH_LONG).show();
                }
                else if (troutineInput.isEmpty()){
                    troutine.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(PumpSetting.this, "Please enter the oil overheat run limit", Toast.LENGTH_LONG).show();
                }
                else {


                    boolean networkAvailable = isNetworkAvailable();
                    if(networkAvailable) {
                        settingPost(ANDROIDID,dev_id,prunmaxInput,troutineInput,toverhaulInput,pMakeInput,pModelInput,pRatingInput);
                    }
                    else {
                        Toast.makeText(PumpSetting.this, "Please Check your internet connection", Toast.LENGTH_LONG).show();
                    }
                }

                setImageButtonEnabled(PumpSetting.this, true, saveSettings, buttonMutation);

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

    public void settingPumpGetPost(String androidID,String dev_id) {
        Log.d("URL", "sendPost: " + androidID+dev_id);
        getPumpSettingsGetAPIService.savePost(androidID,deviceId).enqueue(new Callback<SettingPumpGet>() {
            @Override
            public void onResponse(Call<SettingPumpGet> call, Response<SettingPumpGet> response) {
                if (response.isSuccessful()) {
                    System.out.println("value for response:");
                    Log.i("POST", "post submitted to API." + response.body().toString());
                    prunmax.setText(response.body().getPrunmax());
                    pmake.setText(response.body().getPmake());
                    pmodel.setText(response.body().getPmodel());
                    prating.setText(response.body().getPrating());
                    troutine.setText(response.body().getTroutine());
                    toverhaul.setText(response.body().getToverhaul());

                }
            }

            @Override
            public void onFailure(Call<SettingPumpGet> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Unable to submit post to API.Response gets failed", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void settingPost(final String androidID, final String devid, final String prunmax, final String troutine, final String toverhaul,final String pmake, final String pmodel, final String prating) {

        Log.d("URL", "sendPost: " + postPumpSettingsAPIService.toString());
        postPumpSettingsAPIService.savePost(androidID,deviceId,prunmax,troutine,toverhaul,pmake,pmodel,prating).enqueue(new Callback<SettingPumpPost>() {
            @Override
            public void onResponse(Call<SettingPumpPost> call, Response<SettingPumpPost> response) {


                Log.i("POST1", ""+androidID+devid+prunmax+troutine+toverhaul+pmake+pmodel+prating);
                if (response.isSuccessful()) {
                    if(response.body().getReply().equalsIgnoreCase("OK")) {

                        loadingDialog.startLoadingDialog();

                        Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            if(!PumpSetting.this.isFinishing() && loadingDialog != null){
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
            public void onFailure(Call<SettingPumpPost> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
                    Toast.makeText(PumpSetting.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(PumpSetting.this, "Unable to submit post to API.Response gets failed", Toast.LENGTH_LONG).show();
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
