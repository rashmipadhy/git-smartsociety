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
import com.kfxlabs.smartsociety.api.GetInflowLog;
import com.kfxlabs.smartsociety.module.InflowLogDetails;
import com.kfxlabs.smartsociety.module.OutflowLogDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InflowLogActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Inflow_Adapter adapter;
    public static String getANDROIDID() {
        return ANDROIDID;
    }

    public static void setANDROIDID(String ANDROIDID) {
        InflowLogActivity.ANDROIDID = ANDROIDID;
    }

    private static String ANDROIDID;

    public TextView ptvdate;
    public TextView ptvtime;
    public TextView plitres;

    LoadingDialog loadingDialog;
    String deviceId = "";

    GetInflowLog getInflowLogAPIService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inflow_log);

        recyclerView = findViewById(R.id.recycler_view_p);
        ptvdate = findViewById(R.id.p_tv_date);
        plitres = findViewById(R.id.p_litres);
        ptvtime = findViewById(R.id.p_tv_time);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadingDialog = new LoadingDialog(InflowLogActivity.this);

        GetInflowLog mAPIService = Api.getInflowLogAPIService(InflowLogActivity.this);


        setANDROIDID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));

        getInflowLogAPIService = Api.getInflowLogAPIService(InflowLogActivity.this);

        Log.d("tag","on create : mAPIService" + mAPIService);


        if (getIntent().getStringExtra("device_id") != null) {

            deviceId = getIntent().getStringExtra("device_id");
            System.out.println("deviceid:" + deviceId);
            getDataFromServer(deviceId);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("Inflow Log");
        }
    }

    private void getDataFromServer(String dev_id) {

        Log.d("URL","sendPost" +dev_id);
        getInflowLogAPIService.savePost(dev_id).enqueue(new Callback<InflowLogDetails>() {
            @Override
            public void onResponse(Call<InflowLogDetails> call, Response<InflowLogDetails> response) {
                if (response.isSuccessful()) {
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(InflowLogActivity.this));
                    adapter = new Inflow_Adapter(InflowLogActivity.this, response.body().inflowLogList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<InflowLogDetails> call, Throwable t) {
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
