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
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.kfxlabs.smartsociety.MainActivity;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.api.GetAlerts;
import com.kfxlabs.smartsociety.api.GetDgAlerts;
import com.kfxlabs.smartsociety.model.Alerts_Details;
import com.kfxlabs.smartsociety.module.Alerts;
import com.kfxlabs.smartsociety.module.DgAlerts;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DgAlertsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DgAlerts_Adapter adapter;

    public static String getANDROIDID() {
        return ANDROIDID;
    }

    public static void setANDROIDID(String ANDROIDID) {
        DgAlertsActivity.ANDROIDID = ANDROIDID;
    }

    private static String ANDROIDID;

    public TextView a_tvdate;
    public TextView a_tvtime;
    public TextView a_alerts;

    GetDgAlerts getDgAlertsAPIService;
    com.kfxlabs.smartsociety.api.GetDgAlerts mAPIService = Api.getDgAlertsAPIService(DgAlertsActivity.this);
    String deviceId = "";


    public static String Date;
    public static String Alerts;
    public static String Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dg_alerts);
        recyclerView = findViewById(R.id.recycler_view_a);

        a_tvdate = findViewById(R.id.a_tv_date);
        a_alerts = findViewById(R.id.a_tv_alerts);
        a_tvtime = findViewById(R.id.a_tv_time);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setANDROIDID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));

        getDgAlertsAPIService = Api.getDgAlertsAPIService(DgAlertsActivity.this);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("Alerts");
        }


        Log.d("tag", "on create : mAPIService" + mAPIService);


        if (getIntent().getStringExtra("device_id") != null) {
            deviceId = getIntent().getStringExtra("device_id");
            System.out.println("ruche: " + deviceId);
            DgAlertGet(deviceId);
        }
    }

    public void DgAlertGet(String deviceId) {
        Log.d("URL", "sendPost: " + mAPIService.toString());

        mAPIService.savePost(deviceId).enqueue(new Callback<DgAlerts>() {

            @Override
            public void onResponse(Call<DgAlerts> call, Response<DgAlerts> response) {
                if (response.isSuccessful()){

                    Log.i("POST1", "post submitted to API." + response.body().toString());

                    SharedPrefManager manager = SharedPrefManager.getInstance(DgAlertsActivity.this);
                    manager.clearExistingDGValues();

                    DgAlerts alertlist = response.body();
                    List<Alerts_Details> alertValueDetailsList = new ArrayList<>();
                    int totalDgs = response.body().alerts.size();
                    for(int alertIndex=0; alertIndex < totalDgs; alertIndex++) {
                        Alerts_Details details = new Alerts_Details(

                                alertlist.date.get(alertIndex),
                                alertlist.alerts.get(alertIndex),
                                alertlist.time.get(alertIndex)

                        );

                        alertValueDetailsList.add(details);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(DgAlertsActivity.this));
                        adapter = new DgAlerts_Adapter(DgAlertsActivity.this,alertValueDetailsList);
                        recyclerView.setAdapter(adapter);

                    }
            }else {
                    Log.i("POSTA", "failed");
                }
                try {
                    if (response.errorBody() != null) {
                        Toast.makeText(getApplicationContext(), "Error occurred.Try again.", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }




            @Override
            public void onFailure(Call<DgAlerts> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
                    Toast.makeText(DgAlertsActivity.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
                }else{
                    Log.i("POST", "post failed."+ t.getMessage());
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


