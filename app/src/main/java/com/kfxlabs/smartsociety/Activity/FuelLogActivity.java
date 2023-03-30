package com.kfxlabs.smartsociety.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.api.GetFuelLog;
import com.kfxlabs.smartsociety.module.FuelLogDetails;
import com.kfxlabs.smartsociety.module.SettingGet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuelLogActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Fuel_Adapter adapter;

    public static String getANDROIDID() {
        return ANDROIDID;
    }

    public static void setANDROIDID(String ANDROIDID) {
        FuelLogActivity.ANDROIDID = ANDROIDID;
    }

    private static String ANDROIDID;

    public TextView tvdate;
    public TextView tvtime;
    public TextView tvffill;
    public TextView tvnewlvl;
    public ImageView imageView;
    public TextView fremarktext;
    public RelativeLayout fremarklayout;
    LoadingDialog loadingDialog;
    String selectedDeviceId = "";
    String deviceId = "";

    com.kfxlabs.smartsociety.api.GetFuelLog getFuelLogAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_log);

        recyclerView = findViewById(R.id.recycler_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvdate = findViewById(R.id.tv_date);
        tvtime = findViewById(R.id.tv_time);
        tvffill = findViewById(R.id.ffill);
        tvnewlvl = findViewById(R.id.new_fuel);
        fremarktext = findViewById(R.id.fremark_text_02);
        loadingDialog = new LoadingDialog(FuelLogActivity.this);
        FloatingActionButton fab = findViewById(R.id.add_fuel);

        com.kfxlabs.smartsociety.api.GetFuelLog mAPIService = Api.getFuelLogAPIService(FuelLogActivity.this);

        setANDROIDID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));

        getFuelLogAPIService = Api.getFuelLogAPIService(FuelLogActivity.this);

        Log.d("tag", "on create : mAPIService" + mAPIService);


        if (getIntent().getStringExtra("device_id") != null) {
            deviceId = getIntent().getStringExtra("device_id");
            // setRecyclerView(deviceId);
            getDataFromServer(deviceId);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("DG Fuel Log");
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(FuelLogActivity.this, AddFuel_Activity.class);
                intent.putExtra("device_id", selectedDeviceId);
                startActivity(intent);

                Toast.makeText(FuelLogActivity.this, "Add DG", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getDataFromServer(String dev_id) {
        Log.d("URL", "sendPost" + dev_id);
        System.out.println("dev_id:" + dev_id);
        System.out.println("deviceID:" + deviceId);
        getFuelLogAPIService.savePost(dev_id).enqueue(new Callback<FuelLogDetails>() {
            @Override
            public void onResponse(Call<FuelLogDetails> call, Response<FuelLogDetails> response) {
                if (response.isSuccessful()) {

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(FuelLogActivity.this));
                    adapter = new Fuel_Adapter(FuelLogActivity.this, response.body().fuelLogList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<FuelLogDetails> call, Throwable t) {
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