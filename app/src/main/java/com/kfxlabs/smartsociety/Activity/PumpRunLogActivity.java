package com.kfxlabs.smartsociety.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.module.PumpRunLogDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PumpRunLogActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PumpRun_Adapter adapter;

    public static String getANDROIDID() {
        return ANDROIDID;
    }
    public static void setANDROIDID(String ANDROIDID) {
        PumpRunLogActivity.ANDROIDID = ANDROIDID;
    }
    private static String ANDROIDID;

    public TextView tvdate;
    public TextView tvinfo;
    public TextView tvdur;
    public TextView tvtime;
    public ImageView imageView;

    LoadingDialog loadingDialog;
    String deviceId = "";

    com.kfxlabs.smartsociety.api.GetPumpRunLog getPumpRunLogService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pump_run_log);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler_view2);
        tvdate = findViewById(R.id.tv_date);
        tvinfo = findViewById(R.id.tv_info);
        tvdur = findViewById(R.id.tv_dur);
        tvtime = findViewById(R.id.tv_time);
        // imageView = findViewById(R.id.fremark_icon);
        loadingDialog = new LoadingDialog(PumpRunLogActivity.this);

        com.kfxlabs.smartsociety.api.GetPumpRunLog mAPIService = Api.getPumpRunLogAPIService(PumpRunLogActivity.this);

        setANDROIDID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));

        getPumpRunLogService =Api.getPumpRunLogAPIService(PumpRunLogActivity.this);

        Log.d("tag"," on create : mAPISservice " + mAPIService);

        if (getIntent().getStringExtra("device_id") != null) {
            deviceId = getIntent().getStringExtra("device_id");
            // setRecyclerView(deviceId);
            getDataFromServer(deviceId);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("Pump Run Log");
        }
    }

    private  void getDataFromServer(String deviceId){
        Log.d("URL", "sendPost" + deviceId);

        getPumpRunLogService.savePost(deviceId).enqueue(new Callback<PumpRunLogDetails>() {
            @Override
            public void onResponse(Call<PumpRunLogDetails> call, Response<PumpRunLogDetails> response) {
                if(response.isSuccessful()){

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(PumpRunLogActivity.this));
                    adapter = new PumpRun_Adapter(PumpRunLogActivity.this,response.body().pumpRunLogList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<PumpRunLogDetails> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Unable to get API.Response gets failed",Toast.LENGTH_LONG).show();

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