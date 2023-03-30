package com.kfxlabs.smartsociety.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kfxlabs.smartsociety.MainActivity;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.model.DgValueDetails;
import com.kfxlabs.smartsociety.model.TankValueDetails;
import com.kfxlabs.smartsociety.module.Dglist;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import kotlinx.coroutines.Delay;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DgActivity extends AppCompatActivity {

    TextView fuellevel;
    Button btn_building;
    Button btn_unit;
    CardView f_btn;
    CardView p_btn;
    CardView m_btn;
    CardView r_btn;
    ImageButton settingdg;
    com.kfxlabs.smartsociety.api.GetDglist mAPIService = Api.getDglistAPIService(DgActivity.this);
    com.kfxlabs.smartsociety.api.GetDglist getSettingAPIService;
    String selectedDeviceId = "";
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dg);
        settingdg = findViewById(R.id.settingdg);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        p_btn = findViewById(R.id.power_btn);
        f_btn = findViewById(R.id.fuel_btn);
        r_btn = findViewById(R.id.run_btn);
        m_btn = findViewById(R.id.maint_btn);
        fuellevel = findViewById(R.id.headingText);
        btn_unit = findViewById(R.id.clickBtn1);
        btn_building = findViewById(R.id.clickBtn);
        getSettingAPIService = Api.getDglistAPIService(DgActivity.this);
        FloatingActionButton fab = findViewById(R.id.add_dg);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("DG Monitoring");
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                addDgPostRefresh();
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DgActivity.this, AddDgActivity.class);
                startActivity(intent);
                Toast.makeText(DgActivity.this, "Add DG", Toast.LENGTH_SHORT).show();
            }
        });

        settingdg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DgActivity.this, DgSettings.class);
                intent.putExtra("device_id", selectedDeviceId);
                startActivity(intent);
                Toast.makeText(DgActivity.this, "Setting Activity", Toast.LENGTH_SHORT).show();
            }
        });

        btn_building.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(DgActivity.this, btn_building);

                SharedPrefManager manager = new SharedPrefManager(DgActivity.this);
                List<DgValueDetails> dgValueDetailsList = manager.getDgValueDetails();
                List<String> buildingnamedetails = new ArrayList<>();
                int itemId = 0;

                for (DgValueDetails details : dgValueDetailsList) {
                    String string = getString(details.dgName);
                    String[] parts = string.split("@");
                    String part1 = parts[1];
                    buildingnamedetails.add(part1);
                }
                Set<String> set = new LinkedHashSet<>();
                set.addAll(buildingnamedetails);
                buildingnamedetails.clear();
                buildingnamedetails.addAll(set);

                for(String buildname : buildingnamedetails ){
                    popupMenu.getMenu().add(0,itemId,0,buildname);
                    itemId += 1;
                }

                //drop down detail for one DG
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
                    for(DgValueDetails details : dgValueDetailsList){
                        String string = getString(details.dgName);
                        String[] parts = string.split("@");

                        String part1 = parts[1];

                        if(part1.equalsIgnoreCase(String.valueOf(menuItem))){
                            SharedPrefManager manager = SharedPrefManager.getInstance(DgActivity.this);
                            manager.clearExistingDGValuesSingle();
                            manager.addDgValueSingle(details);

                            updateDgDetails(details);
                        }
                    }
                        return true;
                    }
                });
                popupMenu.show();

            }
        });


        f_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DgActivity.this, FuelLogActivity.class);
                intent.putExtra("device_id", selectedDeviceId);
                startActivity(intent);
                Toast.makeText(DgActivity.this, "Fuel Log", Toast.LENGTH_SHORT).show();


            }
        });

        p_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DgActivity.this, PowerLogActivity.class);
                intent.putExtra("device_id", selectedDeviceId);
                startActivity(intent);
                Toast.makeText(DgActivity.this, "Power Log", Toast.LENGTH_SHORT).show();


            }
        });


        r_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DgActivity.this, RunLogActivity.class);
                intent.putExtra("device_id", selectedDeviceId);
                startActivity(intent);
                Toast.makeText(DgActivity.this, "Run Log", Toast.LENGTH_SHORT).show();

            }
        });

        m_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DgActivity.this, MaintainanceLogActivity.class);
                intent.putExtra("device_id", selectedDeviceId);
                startActivity(intent);
                Toast.makeText(DgActivity.this, "Maintenance Log", Toast.LENGTH_SHORT).show();


            }
        });

        btn_unit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(DgActivity.this, btn_unit);

                SharedPrefManager manager = new SharedPrefManager(DgActivity.this);
                List<DgValueDetails> dgValueDetailsSingleList = manager.getDgValueDetailsSingle();

                String buildingname = " ";

                for (DgValueDetails details : dgValueDetailsSingleList) {
                    String string = getString(details.dgName);
                    String[] parts = string.split("@");

                    buildingname = parts[1];

                }

                SharedPrefManager manager1 = new SharedPrefManager(DgActivity.this);
                List<DgValueDetails> dgValueDetailsList = manager1.getDgValueDetails();
                int itemId = 0;
                for (DgValueDetails details1 : dgValueDetailsList){
                    String string = getString(details1.dgName);
                    String[] parts = string.split("@");

                    String dgnamelist = parts[0];
                    String buildinglist = parts[1];

                    if (buildinglist.equalsIgnoreCase(buildingname))
                    {
                                popupMenu.getMenu().add(0, itemId, 0,dgnamelist);
                    itemId += 1;

                }
                }
                //drop down detail for one DG
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
                        DgValueDetails details = dgValueDetailsList.get(menuItem.getItemId());

                        SharedPrefManager manager = SharedPrefManager.getInstance(DgActivity.this);
                        manager.clearExistingDGValuesSingle();
                        manager.addDgValueSingle(details);

                        updateDgDetails(details);

                        return true;
                    }
                });
                popupMenu.show();

            }
        });


        Log.d("tag", "on create : mAPIService" + mAPIService);
        addDgPost();


    }

    private String getString(String dgName) {

        return dgName;
    }


    public void addDgPost() {

        Log.d("URL", "sendPost: " + mAPIService.toString());

        mAPIService.savePost().enqueue(new Callback<Dglist>() {

            @Override
            public void onResponse(Call<Dglist> call, Response<Dglist> response) {


                if (response.isSuccessful()) {

                    Log.i("POST1", "post submitted to API." + response.body().toString());

                    SharedPrefManager manager = SharedPrefManager.getInstance(DgActivity.this);
                    manager.clearExistingDGValues();

                    Dglist dglist = response.body();
                    List<DgValueDetails> dgValueDetailsList = new ArrayList<>();
                    int totalDgs = response.body().f_perc.size();
                    for(int dgIndex=0; dgIndex < totalDgs; dgIndex++) {
                        DgValueDetails details = new DgValueDetails(dglist.dg_name.get(dgIndex),
                                dglist.devid.get(dgIndex),
                                dglist.tmaint.get(dgIndex),
                                dglist.f_perc.get(dgIndex),
                                dglist.dg_stat.get(dgIndex),
                                dglist.dev_stat.get(dgIndex),
                                dglist.pwr_stat.get(dgIndex),
                                dglist.tfuel.get(dgIndex),
                                dglist.f_val.get(dgIndex)


                        );

                        if (dgIndex==0){
                            updateDgDetails(details);
                        }
                        dgValueDetailsList.add(details);

                        manager.addDgValue(details);
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
            public void onFailure(Call<Dglist> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
                    Toast.makeText(DgActivity.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
                }else{
                    Log.i("POST", "post failed."+ t.getMessage());
                }
            }
        });
    }


    public void addDgPostRefresh() {

        Log.d("URL", "sendPost: " + mAPIService.toString());


        mAPIService.savePost().enqueue(new Callback<Dglist>() {

            @Override
            public void onResponse(Call<Dglist> call, Response<Dglist> response) {

                if (response.isSuccessful()) {

                    Log.i("POST1", "post submitted to API." + response.body().toString());

                    SharedPrefManager manager = SharedPrefManager.getInstance(DgActivity.this);
                    manager.clearExistingDGValues();

                    Dglist dglist = response.body();
                    List<DgValueDetails> dgValueDetailsList = new ArrayList<>();
                    int totalDgs = response.body().f_perc.size();
                    //to get existing DG
                    SharedPrefManager manager1 = new SharedPrefManager(DgActivity.this);
                    List<DgValueDetails> dgValueDetailsSingle = manager1.getDgValueDetailsSingle();
                    String buildingname = " ";
                    String builddg = " ";

                    for (DgValueDetails details: dgValueDetailsSingle){
                        builddg = getString(details.dgName);
                    }

                    for(int dgIndex=0; dgIndex < totalDgs; dgIndex++) {
                        DgValueDetails details = new DgValueDetails(dglist.dg_name.get(dgIndex),
                                dglist.devid.get(dgIndex),
                                dglist.tmaint.get(dgIndex),
                                dglist.f_perc.get(dgIndex),
                                dglist.dg_stat.get(dgIndex),
                                dglist.dev_stat.get(dgIndex),
                                dglist.pwr_stat.get(dgIndex),
                                dglist.tfuel.get(dgIndex),
                                dglist.f_val.get(dgIndex)


                        );

                        // need to add details for the latest dg name
                        if (details.dgName.equalsIgnoreCase(builddg)){

                            updateDgDetails(details);
                        }

                        dgValueDetailsList.add(details);

                        manager.addDgValue(details);
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
            public void onFailure(Call<Dglist> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
                    Toast.makeText(DgActivity.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
                }else{
                    Log.i("POST", "post failed."+ t.getMessage());
                }
            }
        });
    }


    private void updateDgDetails(DgValueDetails details) {
        selectedDeviceId = details.dgDevId;

        String dgname = getString(details.dgName);
        String[] parts = dgname.split("@");
        String part1 = parts[0];
        String part2 = parts[1];

        TextView tPwr = findViewById(R.id.tv_pwr);
        tPwr.setText(details.devStatus);
        if (details.devStatus.equals("Offline")){
            tPwr.setTextColor(Color.RED);

        }else{ tPwr.setTextColor(Color.BLACK); }

        TextView fleft = findViewById(R.id.f_left);
        fleft.setText(details.fval + " Litres");

        TextView dgLoc = findViewById(R.id.tv_dgloc);
        dgLoc.setText(part2);

        TextView dgstatus = findViewById(R.id.text1);
        dgstatus.setText(details.dgStatus);

        TextView tFuel =findViewById(R.id.text4);
        System.out.println("tfule :" + details.tFuel);
        System.out.println("devid:" + details.dgName);
        System.out.println("devid:" + details.fval);
        System.out.println("devid:" + details.dgDevId);
        System.out.println("devid:" + details.pwrStatus);
        System.out.println("devid:" + details.tMaint);
        System.out.println("devid:" + details.dgValue);


        tFuel.setText(details.tFuel + " Mins");

        TextView dgName =findViewById(R.id.tv_dgName);
        dgName.setText(part1);

        HalfGauge halfGauge = findViewById(R.id.dg_fuelgauge);


        Range range = new Range();
        range.setColor(Color.parseColor("#ce0000"));
        range.setFrom(0.0);
        range.setTo(30.0);


        Range range1 = new Range();
        range1.setColor(Color.parseColor("#ebb134"));

        range1.setFrom(30.0);
        range1.setTo(60.0);

        Range range2 = new Range();
        range2.setColor(Color.parseColor("#00b20b"));
        range2.setFrom(60.0);
        range2.setTo(100.0);

        //add color ranges to gauge
        halfGauge.addRange(range);
        halfGauge.addRange(range1);
        halfGauge.addRange(range2 );

        //set min max and current value
        halfGauge.setMinValue(0.0);
        halfGauge.setMaxValue(100.0);

        // Your actual value
        halfGauge.setValue(details.dgValue);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.nav_bar_02, menu);

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
        else if(item.getItemId() == R.id.bell_menu) {
            Intent intent = new Intent(DgActivity.this, DgAlertsActivity.class);
            intent.putExtra("device_id", selectedDeviceId);
            startActivity(intent);
            Toast.makeText(this, "Alerts", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
