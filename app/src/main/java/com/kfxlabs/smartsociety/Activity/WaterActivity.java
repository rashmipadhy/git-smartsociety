package com.kfxlabs.smartsociety.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.model.TankValueDetails;
import com.kfxlabs.smartsociety.module.Tanklist;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaterActivity extends AppCompatActivity {
    Button btntank;
    Button btnbuild;
    CardView b_inlog;
    CardView b_outlog;
    CardView b_quality;
    TextView tv_TankName;
    ImageButton tankplot;
    String selectedDeviceId = "";
    String tankName = "";
    ProgressBar VerticalProgressBar;
    com.kfxlabs.smartsociety.api.GetTanklist mAPIService = Api.getTanklistAPIService(WaterActivity.this);
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        VerticalProgressBar = findViewById(R.id.vprogressbar);
        btntank = findViewById(R.id.clickBtn1);
        btnbuild = findViewById(R.id.clickBtn);
        b_inlog = findViewById(R.id.btn_inlog);
        b_quality = findViewById(R.id.btn_quality);
        b_outlog = findViewById(R.id.btn_outLog);
        tv_TankName = findViewById(R.id.tv_TankName);
        //************************
        tankplot = findViewById(R.id.settingdg);
        //***********************
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);


        FloatingActionButton fab = findViewById(R.id.add_tank);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("Water Management");
        }

        // SetOnRefreshListener on SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                addTankPostRefresh();
            }
        });

     //    setDefaultTank();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WaterActivity.this, AddTankActivity.class);
                startActivity(intent);

                Toast.makeText(WaterActivity.this, "Add Tank", Toast.LENGTH_SHORT).show();
            }
        });


        b_quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WaterActivity.this,"No Logs Available",Toast.LENGTH_SHORT).show();
            }
        });

        b_inlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaterActivity.this, InflowLogActivity.class);
               // intent.putExtra("device_id", selectedDeviceId);
                intent.putExtra("device_id", selectedDeviceId);
                startActivity(intent);
                Toast.makeText(WaterActivity.this, "Inflow Log  ", Toast.LENGTH_SHORT).show();

            }
        });
        //******************************************
        //tankplot.setOnClickListener(new View.OnClickListener() {
        VerticalProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaterActivity.this, TankPlotActivity.class);
                /*Bundle bundle = new Bundle();
                bundle.putString("name", selectedDeviceId);
                bundle.putString("age", tankName);*/
                intent.putExtra("device_id", selectedDeviceId);
                intent.putExtra("tank_name",tankName);
                Log.d("device","device  "+selectedDeviceId);
                Log.d("tankname","name  "+tankName);
                //intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(WaterActivity.this, " Tank Plot  ", Toast.LENGTH_SHORT).show();

            }
        });
        //*************************************************

        b_outlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaterActivity.this, OutflowLogActivity.class);

                intent.putExtra("device_id", selectedDeviceId);
                startActivity(intent);
                Toast.makeText(WaterActivity.this, "Outflow Log  ", Toast.LENGTH_SHORT).show();

            }
        });

        btnbuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(WaterActivity.this, btnbuild);

                SharedPrefManager manager = new SharedPrefManager(WaterActivity.this);
                List<TankValueDetails> tankValueDetailsList = manager.getTankValueDetails();
                List<String> buildingnamedetails = new ArrayList<>();

                int itemId = 0;
                String prevbuildname = " ";
                int counter = 0;

                for (TankValueDetails details : tankValueDetailsList) {
                    String string = getString(details.tank_name);
                    String[] parts = string.split("@");
                    String part2 = parts[1];
                    buildingnamedetails.add(part2);
                    System.out.println("details in buildingname :" + buildingnamedetails.get(counter));
                    counter ++ ;
                }

                Set<String> set = new LinkedHashSet<>();
                set.addAll(buildingnamedetails);
                buildingnamedetails.clear();
                buildingnamedetails.addAll(set);

                for(String buildname : buildingnamedetails ){
                    System.out.println("Updated building name :" + buildname);
                        popupMenu.getMenu().add(0, itemId, 0, buildname);
                        itemId += 1;
                }

                //drop down detail for one DG

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        System.out.println("Menu item:" + menuItem);

                        int counter = 0;
                        for (TankValueDetails details: tankValueDetailsList){

                            String string = getString(details.tank_name);
                            String[] parts = string.split("@");

                            String part2 = parts[1];


                            if (part2.equalsIgnoreCase(String.valueOf(menuItem))){
                                SharedPrefManager manager = SharedPrefManager.getInstance(WaterActivity.this);
                                manager.clearExistingTankValuesSingle();
                                manager.addTankValueSingle(details);
                               // updateTankDetails(details);

                            }

                        }

                        return true;
                    }
                });
                popupMenu.show();

            }


        });

        btntank.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(WaterActivity.this, btntank);

                SharedPrefManager manager = new SharedPrefManager(WaterActivity.this);
                List<TankValueDetails> tankValueDetailsSingleList = manager.getTankValueDetailsSingle();

                String buildingname = " ";

                for (TankValueDetails details: tankValueDetailsSingleList){
                    String string = getString(details.tank_name);
                    String[] parts = string.split("@");

                    buildingname = parts[1];

                }

                SharedPrefManager manager1 = new SharedPrefManager(WaterActivity.this);
                List<TankValueDetails> tankValueDetailsList = manager1.getTankValueDetails();
                int itemId = 0;

                String prevbuildname1 = " ";

                for (TankValueDetails details1 : tankValueDetailsList) {
                    String string = getString(details1.tank_name);
                    String[] parts = string.split("@");

                    String tanknameinlist = parts[0];
                    String buildinginlist = parts[1];

                    if (buildinginlist.equalsIgnoreCase(buildingname))
                    {
                        popupMenu.getMenu().add(0,itemId,0,tanknameinlist);
                        itemId += 1;
                    }
                }

                //drop down detail for one
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked

                        SharedPrefManager manager = new SharedPrefManager(WaterActivity.this);
                        List<TankValueDetails> tankValueDetailsSingleList = manager.getTankValueDetailsSingle();
                        String buildingname = " ";

                        for (TankValueDetails details: tankValueDetailsSingleList){
                            String string = getString(details.tank_name);
                            String[] parts = string.split("@");

                            buildingname = parts[1];
                            System.out.println("under tank, building name:" + buildingname );
                        }

                        String tankname = String.valueOf(menuItem);

                        String tankbuilding = tankname + '@' + buildingname;
                        tankName = tankbuilding;

                        System.out.println("tank building name:"  + tankbuilding);

                        for (TankValueDetails details2 : tankValueDetailsList) {
                            String tankbuildlist = getString(details2.tank_name);

                            if (tankbuildlist.equalsIgnoreCase(tankbuilding))
                            {
                                SharedPrefManager manager1 = SharedPrefManager.getInstance(WaterActivity.this);
                                manager1.clearExistingTankValuesSingle();
                                manager1.addTankValueSingle(details2);
                                updateTankDetails(details2);
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();

            }
        });
        Log.d("tag", "on create : mAPIService" + mAPIService);
        // final String dgDetailsFileName = getIntent().getStringExtra("DG_DETAILS");
        addTankPost();

    }

    private String getString(String tank_name) {

        return tank_name;
    }


    public void addTankPost() {

            Log.d("URL", "sendPost: " + mAPIService.toString());

            mAPIService.savePost().enqueue(new Callback<Tanklist>() {

                @Override
                public void onResponse(Call<Tanklist> call, Response<Tanklist> response) {


                    if (response.isSuccessful()) {

                        Log.i("POST1", "post submitted to API." + response.body().toString());

                        SharedPrefManager manager = SharedPrefManager.getInstance(WaterActivity.this);
                        manager.clearExistingTankValues();

                        Tanklist tanklist = response.body();
                        List<TankValueDetails> tankValueDetailsList = new ArrayList<>();
                        int totalTanks = response.body().devid.size();

                        for(int tankIndex=0; tankIndex < totalTanks; tankIndex++) {
                            TankValueDetails details = new TankValueDetails(
                                    tanklist.tank_name.get(tankIndex),
                                    tanklist.dev_stat.get(tankIndex),
                                    tanklist.tank_level.get(tankIndex),
                                    tanklist.alerts.get(tankIndex),
                                    tanklist.capacity.get(tankIndex),
                                    tanklist.quality.get(tankIndex),
                                    tanklist.doutflow.get(tankIndex),
                                    tanklist.dinflow.get(tankIndex),
                                    tanklist.tank_val.get(tankIndex),
                                    tanklist.devid.get(tankIndex)
                            );

                            if (tankIndex==0){
                                updateTankDetails(details);
                            }
                            tankValueDetailsList.add(details);

                            manager.addTankValue(details);
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
                public void onFailure(Call<Tanklist> call, Throwable t) {
                    if (t instanceof SocketTimeoutException) {
                        Log.e("POST", "Unable to submit post to API.Connection time out");
                        Toast.makeText(WaterActivity.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
                    }else{
                        Log.i("POST", "post failed."+ t.getMessage());
                    }
                }
            });
        }


    public void addTankPostRefresh() {

        Log.d("URL", "sendPost: " + mAPIService.toString());

        mAPIService.savePost().enqueue(new Callback<Tanklist>() {

            @Override
            public void onResponse(Call<Tanklist> call, Response<Tanklist> response) {

                if (response.isSuccessful()) {

                    Log.i("POST1", "post submitted to API." + response.body().toString());

                    SharedPrefManager manager = SharedPrefManager.getInstance(WaterActivity.this);
                    manager.clearExistingTankValues();

                    Tanklist tanklist = response.body();
                    List<TankValueDetails> tankValueDetailsList = new ArrayList<>();
                    int totalTanks = response.body().devid.size();

                    //to get existing Tank
                    SharedPrefManager manager1 = new SharedPrefManager(WaterActivity.this);
                    List<TankValueDetails> tankValueDetailsSingleList = manager.getTankValueDetailsSingle();
                    String buildingname = " ";
                    String buildtank = " ";

                    for (TankValueDetails details: tankValueDetailsSingleList){
                         buildtank = getString(details.tank_name);
                        System.out.println("ixxxx-under tank, building name:" + buildtank );
                    }


                    for(int tankIndex=0; tankIndex < totalTanks; tankIndex++) {
                        TankValueDetails details = new TankValueDetails(
                                tanklist.tank_name.get(tankIndex),
                                tanklist.dev_stat.get(tankIndex),
                                tanklist.tank_level.get(tankIndex),
                                tanklist.alerts.get(tankIndex),
                                tanklist.capacity.get(tankIndex),
                                tanklist.quality.get(tankIndex),
                                tanklist.doutflow.get(tankIndex),
                                tanklist.dinflow.get(tankIndex),
                                tanklist.tank_val.get(tankIndex),
                                tanklist.devid.get(tankIndex)
                        );
// need to add details for the latest dg name

                        if (details.tank_name.equalsIgnoreCase(buildtank)){

                            updateTankDetails(details);
                        }

//                        if (tankIndex==0){
//                            updateTankDetails(details);
//                        }

                        tankValueDetailsList.add(details);

                        manager.addTankValue(details);
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
            public void onFailure(Call<Tanklist> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
                    Toast.makeText(WaterActivity.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
                }else{
                    Log.i("POST", "post failed."+ t.getMessage());
                }
            }
        });
    }



    private void updateTankDetails(TankValueDetails details) {
        selectedDeviceId = details.devid;

        String tankName = getString(details.tank_name);
        String[] parts = tankName.split("@");
        String part1 = parts[0];
        String part2 = parts[1];

        TextView tcap = findViewById(R.id.t_capacity);
        tcap.setLines(2);
        tcap.setSingleLine(false);
        tcap.setText(details.tank_val  +" Litres");

        TextView tloc = findViewById(R.id.tv_tankloc);
        tloc.setText(part2);

        TextView tstatus = findViewById(R.id.t_status);
        tstatus.setText(details.dev_stat);
        if (details.dev_stat.equals("Offline")){
            tstatus.setTextColor(Color.RED);

        }else{ tstatus.setTextColor(Color.BLACK); }

        TextView t_quality = findViewById(R.id.t_quality);
        t_quality.setText(details.capacity);


        TextView t_name = findViewById(R.id.tv_TankName);
        t_name.setText(part1);

        TextView t_alert = findViewById(R.id.tv_alert);
        t_alert.setText(details.alerts);

        ProgressBar progressBar = findViewById(R.id.vprogressbar);

      //  progressBar.setMinValue(0);
        progressBar.setMax(100);
        progressBar.setProgress(Integer.parseInt(details.tank_level));

        TextView tlevel = findViewById(R.id.t_perc);
        tlevel.setText(details.tank_level + "%");
        int tanklevel = Integer.parseInt(details.tank_level);

        if (tanklevel >= 0 && tanklevel <= 30){
            tlevel.setTextColor(Color.RED);

        }

        if (tanklevel >= 31 && tanklevel <= 89){
            tlevel.setTextColor(Color.BLACK);

        }
        if (tanklevel >= 90){
            tlevel.setTextColor(Color.RED);

        }

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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
