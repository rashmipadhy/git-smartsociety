package com.kfxlabs.smartsociety.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.model.DgValueDetails;
import com.kfxlabs.smartsociety.model.PumpValueDetails;
import com.kfxlabs.smartsociety.model.TankValueDetails;
import com.kfxlabs.smartsociety.module.Pumplist;
import com.kfxlabs.smartsociety.module.SwitchPumpPost;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PumpActivity extends AppCompatActivity {

    Button btn_pump;
    Button btn_build;
    Context context;
    CardView run_btn;
    CardView schedule_btn;
    CardView power_log;
    String voltages,vcutoff;
    ImageButton settingpump;
    ImageView switchPump;
    public int PumpState = 0;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView tv_red,tv_yellow,tv_blue,tvVcutoff,runtime,tv_dev_status;
    int red,yellow,blue;
    String selectedDeviceId = "";
    com.kfxlabs.smartsociety.api.GetPumplist mAPIService = Api.getPumplistAPIService(PumpActivity.this);
    com.kfxlabs.smartsociety.api.GetPumplist getSettingAPIService;
    com.kfxlabs.smartsociety.api.SwitchPump SwitchPumpAPIService;
    PieChart pieChart;
    PieData pieData;
    LoadingDialog loadingDialog;
    Context alertContext;
    List<PieEntry> pieEntryList = new ArrayList<>();
    public Activity activity;

    public static String ANDROIDID;

    public static String getANDROIDID() {

        return ANDROIDID;
    }

    public static void setANDROIDID(String ANDROIDID) {
        PumpActivity.ANDROIDID = ANDROIDID;
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pump);

        setANDROIDID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));

        run_btn = findViewById(R.id.r_btn);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        schedule_btn = findViewById(R.id.s_btn);
        power_log = findViewById(R.id.p_btn);
        btn_pump = findViewById(R.id.selectP);
        btn_build = findViewById(R.id.selectB);
        settingpump = findViewById(R.id.settingpump);
        tv_red = findViewById(R.id.tv_red);
        tv_blue = findViewById(R.id.tv_blue);
        tv_yellow = findViewById(R.id.tv_yellow);
        tv_dev_status = findViewById(R.id.tv_devStatus);
        runtime =findViewById(R.id.tv_runtime);
      //  pieChart = findViewById(R.id.pc_runtime);
        switchPump = findViewById(R.id.img_switchMotor);
        getSettingAPIService = Api.getPumplistAPIService(PumpActivity.this);
        SwitchPumpAPIService = Api.getSwitchPumpAPIService(PumpActivity.this);
        FloatingActionButton fab = findViewById(R.id.add_pump);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);



        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("Pump Monitoring");
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                addPumpPostRefresh();
            }
        });

       // setDefaultPump();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PumpActivity.this, AddPumpActivity.class);
                startActivity(intent);

                Toast.makeText(PumpActivity.this, "Add Pump", Toast.LENGTH_SHORT).show();
            }
        });


        settingpump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PumpActivity.this, PumpSetting.class);
                intent.putExtra("device_id", selectedDeviceId);
                startActivity(intent);

                Toast.makeText(PumpActivity.this, "Setting Activity", Toast.LENGTH_SHORT).show();
            }
        });

        btn_build.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(PumpActivity.this, btn_build);

                SharedPrefManager manager = new SharedPrefManager(PumpActivity.this);
                List<PumpValueDetails> pumpValueDetailsList = manager.getPumpValueDetails();
                List<String> buildingnamedetails = new ArrayList<>();

                int itemId = 0;
                String prebuildname = " ";
                int counter = 0;

                for (PumpValueDetails details : pumpValueDetailsList) {
                    String string = getString(details.pName);
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
                    //---------------------
                    popupMenu.getMenu().add(0, itemId, 0, buildname);
                    //----------------------------
                    itemId += 1;
                }

                //drop down detail for one Pump

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
                        System.out.println("Menu item:" + menuItem);
                        int counter = 0;

                        for (PumpValueDetails details : pumpValueDetailsList) {

                            String string = getString(details.pName);
                            String[] parts = string.split("@");
                            String part2 = parts[1];

                            if (part2.equalsIgnoreCase(String.valueOf(menuItem))) {
                            SharedPrefManager manager = SharedPrefManager.getInstance(PumpActivity.this);
                            manager.clearExistingPumpValuesSingle();
                            manager.addPumpValueSingle(details);

                           // updatePumpDetails(details);
                        }
                    }
                        return true;
                    }
                });

                popupMenu.show();

            }
        });
        btn_pump.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(PumpActivity.this, btn_pump);

                SharedPrefManager manager = new SharedPrefManager(PumpActivity.this);
                List<PumpValueDetails> pumpValueDetailsSingleList = manager.getPumpValueDetailsSingle();

                String buildingname = " ";

                for (PumpValueDetails details : pumpValueDetailsSingleList) {
                    String string = getString(details.pName);
                    String[] parts = string.split("@");

                    buildingname = parts[1];

                }
                    //popupMenu.getMenu().add(data1);
                SharedPrefManager manager1 = new SharedPrefManager(PumpActivity.this);
                List<PumpValueDetails> pumpValueDetailsList = manager1.getPumpValueDetails();
                int itemId = 0;

                for (PumpValueDetails details : pumpValueDetailsList){
                    String string = getString(details.pName);
                    String[] parts = string.split("@");

                    String pumpnamelist = parts[0];
                    String buildinglist = parts[1];

                    if (buildinglist.equalsIgnoreCase(buildingname))
                    {
                        popupMenu.getMenu().add(0, itemId, 0,pumpnamelist);
                        itemId += 1;

                    }
                }

                //drop down detail for one
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked

                        SharedPrefManager manager = new SharedPrefManager(PumpActivity.this);
                        List<PumpValueDetails> pumpValueDetailsSingleList = manager.getPumpValueDetailsSingle();
                        String buildingname = " ";

                        for (PumpValueDetails details: pumpValueDetailsSingleList){
                            String string = getString(details.pName);
                            String[] parts = string.split("@");

                            buildingname = parts[1];
                            System.out.println("under tank, building name:" + buildingname );
                        }

                        String pumpname = String.valueOf(menuItem);

                        String pumpbuilding = pumpname + '@' + buildingname;

                        System.out.println("tank building name:"  + pumpbuilding);

                        for (PumpValueDetails details2 : pumpValueDetailsList) {
                            String tankbuildlist = getString(details2.pName);

                            if (tankbuildlist.equalsIgnoreCase(pumpbuilding))
                            {
                                SharedPrefManager manager1 = SharedPrefManager.getInstance(PumpActivity.this);
                                manager1.clearExistingPumpValuesSingle();
                                manager1.addPumpValueSingle(details2);
                                updatePumpDetails(details2);
                            }
                        }
                        return true;
                    }
                });

                popupMenu.show();

            }
        });

        run_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PumpActivity.this,PumpRunLogActivity.class) ;
                intent.putExtra("device_id", selectedDeviceId);
                startActivity(intent);
                Toast.makeText(PumpActivity.this,"Run Log",Toast.LENGTH_LONG).show();
            }
        });



        schedule_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PumpActivity.this,PumpScheduleActivity.class) ;
                intent.putExtra("device_id", selectedDeviceId);
                startActivity(intent);
                Toast.makeText(PumpActivity.this,"Schedule pump",Toast.LENGTH_LONG).show();
            }
        });

        power_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PumpActivity.this,PumpPowerLogActivity.class) ;
                intent.putExtra("device_id", selectedDeviceId);
                startActivity(intent);
                Toast.makeText(PumpActivity.this,"Power Log",Toast.LENGTH_LONG).show();
            }
        });


        Log.d("tag", "on create : mAPIService" + mAPIService);
        addPumpPost();


    }

    private String getString(String pName) {

        return pName;
    }

    public void addPumpPost() {

        Log.d("URL", "sendPost: " + mAPIService.toString());

        mAPIService.savePost().enqueue(new Callback<Pumplist>() {

            @Override
            public void onResponse(Call<Pumplist> call, Response<Pumplist> response) {

                if (response.isSuccessful()) {

                    Log.i("POST1", "post submitted to API." + response.body().toString());

                    SharedPrefManager manager = SharedPrefManager.getInstance(PumpActivity.this);
                    manager.clearExistingPumpValues();

                    Pumplist pumplist = response.body();
                    List<PumpValueDetails> pumpValueDetailsList = new ArrayList<>();
                    int totalPumps = response.body().voltages.size();

                    for(int pumpIndex=0; pumpIndex < totalPumps; pumpIndex++) {
                        PumpValueDetails details = new PumpValueDetails(
                                pumplist.devid.get(pumpIndex),
                                pumplist.voltages.get(pumpIndex),
                                pumplist.pwr_stat.get(pumpIndex),
                                pumplist.pump_name.get(pumpIndex),
                                pumplist.pump_stat.get(pumpIndex),
                                pumplist.alerts.get(pumpIndex),
                                pumplist.health.get(pumpIndex),
                                pumplist.dev_stat.get(pumpIndex),
                                pumplist.run_time.get(pumpIndex)
                        );

                        if (pumpIndex==0){
                            updatePumpDetails(details);
                        }
                        pumpValueDetailsList.add(details);

                        manager.addPumpValue(details);
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
            public void onFailure(Call<Pumplist> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
                    Toast.makeText(PumpActivity.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
                }else{
                    Log.i("POST", "post failed."+ t.getMessage());
                }
            }
        });
    }

    public void addPumpPostRefresh() {

        Log.d("URL", "sendPost: " + mAPIService.toString());

        mAPIService.savePost().enqueue(new Callback<Pumplist>() {

            @Override
            public void onResponse(Call<Pumplist> call, Response<Pumplist> response) {

                if (response.isSuccessful()) {

                    Log.i("POST1", "post submitted to API." + response.body().toString());

                    SharedPrefManager manager = SharedPrefManager.getInstance(PumpActivity.this);
                    manager.clearExistingPumpValues();

                    Pumplist pumplist = response.body();
                    List<PumpValueDetails> pumpValueDetailsList = new ArrayList<>();
                    int totalPumps = response.body().voltages.size();

                    //to get existing Pump
                    SharedPrefManager manager1 = new SharedPrefManager(PumpActivity.this);
                    List<PumpValueDetails> pumpValueDetailsList1 = manager.getPumpValueDetailsSingle();
                    String buildingname = " ";
                    String buildpump = " ";

                    for (PumpValueDetails details: pumpValueDetailsList1){
                        buildpump = getString(details.pName);
                        System.out.println("ixxxx-under tank, building name:" + buildpump );
                    }


                    for(int pumpIndex=0; pumpIndex < totalPumps; pumpIndex++) {
                        PumpValueDetails details = new PumpValueDetails(
                                pumplist.devid.get(pumpIndex),
                                pumplist.voltages.get(pumpIndex),
                                // pumplist.uname.get(pumpIndex),
                                pumplist.pwr_stat.get(pumpIndex),
                                pumplist.pump_name.get(pumpIndex),
                                pumplist.pump_stat.get(pumpIndex),
                                pumplist.alerts.get(pumpIndex),
                                // pumplist.cname.get(pumpIndex),
                                pumplist.health.get(pumpIndex),
                                pumplist.dev_stat.get(pumpIndex),
                                pumplist.run_time.get(pumpIndex)
                        );
                        // need to add details for the latest dg name
                        if (details.pName.equalsIgnoreCase(buildpump)){
                            updatePumpDetails(details);
                        }

                        pumpValueDetailsList.add(details);

                        manager.addPumpValue(details);
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
            public void onFailure(Call<Pumplist> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
                    Toast.makeText(PumpActivity.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
                }else{
                    Log.i("POST", "post failed."+ t.getMessage());
                }
            }
        });
    }

    private void updatePumpDetails(PumpValueDetails details) {
        selectedDeviceId = details.pDevId;

        TextView pruntime = findViewById(R.id.tv_runtime);
        pruntime.setText(details.pRuntime);

        String pumpName = getString(details.pName);
        String[] parts = pumpName.split("@");
        String part1 = parts[0];
        String part2 = parts[1];

        System.out.println("pump satis" + details.pStatus);
        TextView pname = findViewById(R.id.tv_PumpName);
        pname.setText(part1);

        TextView ploc = findViewById(R.id.tv_pumploc);
        ploc.setText(part2);

        TextView pstatus = findViewById(R.id.tv_status);
        pstatus.setText(details.pStatus);

        TextView palert = findViewById(R.id.tv_alert);
        palert.setText(details.pAlert);

        TextView pdevstatus = findViewById(R.id.tv_devStatus);
        pdevstatus.setText(details.pDevstatus);
        if (details.pDevstatus.equals("Offline")){
            pdevstatus.setTextColor(Color.RED);

        }else{ pdevstatus.setTextColor(Color.BLACK); }


        TextView phealth = findViewById(R.id.tv_health);
        phealth.setText(details.phealth);

        if (details.pStatus.equals("off")) {
            switchPump.setImageResource(R.drawable.pump_off);
        } else if (details.pStatus.equals("on")) {
            switchPump.setImageResource(R.drawable.pump_on);
        }

        voltages = details.pVoltage;
        System.out.println("voltages: " + voltages);

        if (voltages != null && !voltages.isEmpty()) {
            String temp_voltages = voltages.substring(1, voltages.length() - 1);
            String[] volt_arr = temp_voltages.split(",");

            if (!volt_arr[0].isEmpty()) {
                red = Integer.parseInt(volt_arr[0]);
            }
            if (!volt_arr[1].isEmpty()) {
                yellow = Integer.parseInt(volt_arr[1].replace(" ", ""));
            }
            if (!volt_arr[2].isEmpty()) {
                blue = Integer.parseInt(volt_arr[2].replace(" ", ""));
            }


            Log.i("red", red + "");
            Log.i("blue", blue + "");
            Log.i("yellow", yellow + "");

            if (!volt_arr[0].isEmpty()) {
                if (red < 100) {
                    View view = this.findViewById(R.id.img_volt1);
                    view.setBackground(getDrawable(R.drawable.grey_o));
                } else {
                    View view = this.findViewById(R.id.img_volt1);
                    view.setBackground(getDrawable(R.drawable.r_phase_o));
                }
            }

            if (!volt_arr[1].isEmpty()) {
                if (yellow < 100) {
                    View view = this.findViewById(R.id.img_volt2);
                    view.setBackground(getDrawable(R.drawable.grey_o));
                    // holder.yellow.setBackgroundResource(R.drawable.grey_o);
                } else {
                    // holder.yellow.setBackgroundResource(R.drawable.y_phase_o);
                    View view = this.findViewById(R.id.img_volt2);
                    view.setBackground(getDrawable(R.drawable.y_phase_o));
                }
            }

            if (!volt_arr[2].isEmpty()) {
                if (blue < 100) {
                    //holder.blue.setBackgroundResource(R.drawable.grey_o);
                    View view = this.findViewById(R.id.img_volt3);
                    view.setBackground(getDrawable(R.drawable.grey_o));
                } else {
                    //holder.blue.setBackgroundResource(R.drawable.b_phase_o);
                    View view = this.findViewById(R.id.img_volt3);
                    view.setBackground(getDrawable(R.drawable.b_phase_o));
                }
            }
        }


        if (vcutoff != null && !vcutoff.isEmpty()) {
            if (vcutoff.equalsIgnoreCase("0")) {
                tvVcutoff.setText("Normal Voltage");
            } else if (vcutoff.equalsIgnoreCase("1")) {
                tvVcutoff.setText("Low Voltage");
            } else if (vcutoff.equalsIgnoreCase("2")) {
                tvVcutoff.setText("High Voltage");
            }
        }

        if (details.pRuntime != null && !details.pRuntime.isEmpty()) {
            String runTimeVal = details.pRuntime;
            String temp_runTimeVal = runTimeVal.substring(1, runTimeVal.length() - 1);
            String[] volt_arr = temp_runTimeVal.split(",");
            float perc = 0.0f;
            Log.i("value", volt_arr.length + "");
            for (int index = 0; index < volt_arr.length; index++) {
                if (volt_arr[index].equalsIgnoreCase("0")) {
                    perc = perc + 8.333333f;
                    if (index + 1 < volt_arr.length) {
                        int j = index + 1;
                        if (volt_arr[j].equalsIgnoreCase("0")) {
                            continue;
                        } else {
                            pieEntryList.add(new PieEntry(perc));
                            perc = 0;
                        }
                    } else {
                        pieEntryList.add(new PieEntry(perc));
                        perc = 0;
                    }

                } else if (volt_arr[index].equalsIgnoreCase("1")) {
                    perc = perc + 8.333333f;
                    if (index + 1 < volt_arr.length) {
                        int j = index + 1;
                        if (volt_arr[j].equalsIgnoreCase("1")) {
                            continue;
                        } else {
                            pieEntryList.add(new PieEntry(perc));
                            perc = 0;
                        }
                    } else {
                        pieEntryList.add(new PieEntry(perc));
                        perc = 0;
                    }
                }
            }
        }

        runtime.setText(details.pRuntime);


        switchPump.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (details.pStatus.equals("off")){
                    PumpState = 0;
                    switchPumpPost(ANDROIDID,details.pDevId,switchPump);
                }else {
                    if (details.pStatus.equals("on")){
                        PumpState = 1;
                        switchPumpPost(ANDROIDID,details.pDevId,switchPump);

                    }

                }
            }
        });

    }


   public void switchPumpPost(final String androidId,final String devid, final ImageView switchPump) {
        String state = "";
        if (PumpState == 0) {
            state = "on";
            switchPump.setImageResource(R.drawable.pump_on);
        } else if (PumpState == 1) {
            state = "off";
            switchPump.setImageResource(R.drawable.pump_off);
        }
        Log.d("URL", "sendPost: " + SwitchPumpAPIService.toString());

     final String finalState = state;
        SwitchPumpAPIService.savePost(androidId, devid, state).enqueue(new Callback<SwitchPumpPost>() {
            @Override
            public void onResponse(Call<SwitchPumpPost> call, Response<SwitchPumpPost> response) {

                if (response.isSuccessful()) {
                    System.out.println("enter in pump post successful");
                    Log.i("Switch", "post submitted to API." + response.body().toString());
                    if ("OK".equals(response.body().getReply())) {
                        if (PumpState == 0) {
                            switchPump.setImageResource(R.drawable.pump_on);
                        } else if (PumpState == 1) {
                            switchPump.setImageResource(R.drawable.pump_off);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SwitchPumpPost> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
               //   Toast.makeText(context  , "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
                }
                Toast.makeText(context, "Please check the internet connection", Toast.LENGTH_SHORT).show();
            }
        });
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
            Intent intent = new Intent(PumpActivity.this, DgAlertsActivity.class);
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
