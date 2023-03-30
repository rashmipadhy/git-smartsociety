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
import android.widget.TextView;
import android.widget.Toast;

import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.api.GetPowerLog;
import com.kfxlabs.smartsociety.api.GetPumpPowerLog;
import com.kfxlabs.smartsociety.module.PowerLogDetails;
import com.kfxlabs.smartsociety.module.PumpPowerLogDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PumpPowerLogActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PumpPower_Adapter adapter;
    public static String getANDROIDID() {
        return ANDROIDID;
    }

    public static void setANDROIDID(String ANDROIDID) {
        PumpPowerLogActivity.ANDROIDID = ANDROIDID;
    }

    private static String ANDROIDID;

    public TextView ptvdate;
    public TextView ptvinfo;
    public TextView pdur;
    public TextView time;
    LoadingDialog loadingDialog;
    String deviceId = "";

    GetPumpPowerLog getPumpPowerLogAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pump_power_log);


        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view_pump);
        ptvdate = findViewById(R.id.p_tv_date);
        pdur = findViewById(R.id.p_dur);
        ptvinfo = findViewById(R.id.p_tv_info);
        time = findViewById(R.id.time);

        loadingDialog = new LoadingDialog(PumpPowerLogActivity.this);

        GetPowerLog mAPIService = Api.getPowerLogAPIService(PumpPowerLogActivity.this);

        setANDROIDID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));

        getPumpPowerLogAPIService = Api.getPumpPowerLogAPIService(PumpPowerLogActivity.this);

        Log.d("tag","on create : mAPIService" + mAPIService);

        if (getIntent().getStringExtra("device_id") != null) {
            deviceId = getIntent().getStringExtra("device_id");
            // setRecyclerView(deviceId);
            getDataFromServer(deviceId);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("Power Log");
        }
    }

    private void getDataFromServer(String dev_id) {
        Log.d("URL","sendPost" +dev_id);

        getPumpPowerLogAPIService.savePost(deviceId).enqueue(new Callback<PumpPowerLogDetails>() {
            @Override
            public void onResponse(Call<PumpPowerLogDetails> call, Response<PumpPowerLogDetails> response) {
                if (response.isSuccessful()) {
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(PumpPowerLogActivity.this));
                    adapter = new PumpPower_Adapter(PumpPowerLogActivity.this, response.body().pumppowerLogList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<PumpPowerLogDetails> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to get API.Response gets failed", Toast.LENGTH_LONG).show();

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