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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.module.FuelLogDetails;
import com.kfxlabs.smartsociety.module.MaintLogDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaintainanceLogActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Maint_Adapter adapter;

    public static String getANDROIDID() {
        return ANDROIDID;
    }
    public static void setANDROIDID(String ANDROIDID) {
        MaintainanceLogActivity.ANDROIDID = ANDROIDID;
    }
    private static String ANDROIDID;

    public TextView tvdate;
    public TextView tvtype;
    public TextView tvuid;
    public TextView tvenghrs;
    public ImageView imageView;
    public TextView fremarktext;
    public RelativeLayout fremarklayout;
    LoadingDialog loadingDialog;
    String deviceId = "";
    String selectedDeviceId = "";


    com.kfxlabs.smartsociety.api.GetMaintLog getMaintLogAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintainance_log);

        recyclerView = findViewById(R.id.recycler_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvdate = findViewById(R.id.tv_date);
        tvtype = findViewById(R.id.tv_mtype);
        tvenghrs = findViewById(R.id.tv_engineHrs);
      //  tvuid = findViewById(R.id.uid);
      //  imageView = findViewById(R.id.fremark_icon);
        fremarktext = findViewById(R.id.fremark_text1);
      //  fremarklayout = findViewById(R.id.fremarklayout);
        loadingDialog = new LoadingDialog(MaintainanceLogActivity.this);
        FloatingActionButton fab = findViewById(R.id.add_maint);

        com.kfxlabs.smartsociety.api.GetMaintLog mAPIService = Api.getMaintLogAPIService(MaintainanceLogActivity.this);

        setANDROIDID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));

        getMaintLogAPIService = Api.getMaintLogAPIService(MaintainanceLogActivity.this);

        Log.d("tag","on create : mAPIService" + mAPIService);

        if (getIntent().getStringExtra("device_id") != null) {
            deviceId = getIntent().getStringExtra("device_id");
            getDataFromServer(deviceId);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("DG Maintenance Log");
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MaintainanceLogActivity.this, AddMaintActivity.class);
                intent.putExtra("device_id", selectedDeviceId);
                startActivity(intent);

                Toast.makeText(MaintainanceLogActivity.this, "Add Maint", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void getDataFromServer(String dev_id) {
        System.out.println("Maintain dev id :" + dev_id);
        Log.d("URL","sendPost" +dev_id);
        getMaintLogAPIService.savePost(dev_id).enqueue(new Callback<MaintLogDetails>() {
            @Override
            public void onResponse(Call<MaintLogDetails> call, Response<MaintLogDetails> response) {
                if (response.isSuccessful()) {
                    System.out.println("response is successfull" + response);

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MaintainanceLogActivity.this));
                    adapter = new Maint_Adapter(MaintainanceLogActivity.this, response.body().maintLogList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<MaintLogDetails> call, Throwable t) {
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