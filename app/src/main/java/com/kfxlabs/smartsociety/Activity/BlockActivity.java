package com.kfxlabs.smartsociety.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.kfxlabs.smartsociety.Activity.databinding.ActivityBlockBinding;

import com.kfxlabs.smartsociety.MainActivity;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.model.TankValueDetails;
import com.kfxlabs.smartsociety.module.Tanklist;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlockActivity extends AppCompatActivity {

    BarChart barChart;
    //int[] tank_level1 = new int[10];
    //String[] tankname1 = new String[10];
    //String[] tankStatus1 = new String[10];
    //String[] tankColor1 = new String[10];

    List<Integer> tank_level1 =new ArrayList<>();
    List<String> tankname1 = new ArrayList<>();
    List<String> tankStatus1 = new ArrayList<>();
    List<String> tankColor1 = new ArrayList<>();
    private int arrayIndex=0;

    com.kfxlabs.smartsociety.api.GetTanklist mAPIService = Api.getTanklistAPIService(BlockActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // landscape orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_block);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initializing variable for bar chart.
        barChart = findViewById(R.id.idBarChart);


        Log.d("tag", "on create : mAPIService" + mAPIService);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("Block / Tower View");
        }

        getTankBuildData(tank_level1);


    }

     //public void getTankBuildData(int [] tank_level1) {
     public void getTankBuildData(List<Integer> tank_level1) {
        Log.d("URL", "sendPost: " + mAPIService.toString());

        mAPIService.savePost().enqueue(new Callback<Tanklist>() {

            @Override
            public void onResponse(Call<Tanklist> call, Response<Tanklist> response) {

                if (response.isSuccessful()) {

                    Log.i("POST1", "post submitted to API." + response.body().toString());

                    Tanklist tanklist = response.body();
                    Log.d("tanklist", tanklist.toString());


                    int totalTanks = response.body().devid.size();
                    int i = 0;
                    int j = 0;
                    int k = 0;

                    String tankName ;
                    String tankColor ;
                    String colorPlot= new String("blue");
                    arrayIndex=0;

                    for (int tankIndex = 0; tankIndex < totalTanks; tankIndex++) {

                        Log.d("tankName", tanklist.tank_name.get(tankIndex));
                        Log.d("tankLevel", tanklist.tank_level.get(tankIndex));


                        //String tankName = tanklist.tank_name.get(tankIndex);
                        //String tankColor = tanklist.color.get(tankIndex);
                        //Log.d("tankColor", tankColor);
                        Log.d("tankColor",tanklist.color.get(tankIndex));
                        //if(!tankColor.equals("grey")) {
                        tankColor = tanklist.color.get(tankIndex);
                        if(tankColor.equals(colorPlot)) {
                            tankName = tanklist.tank_name.get(tankIndex);

                            String[] parts = tankName.split("@");
                            //String part1 = parts[0];
                            //String part2 = parts[1];

                            //tank_level1[tankIndex] = Integer.parseInt(tanklist.tank_level.get(tankIndex));
                            tank_level1.add(arrayIndex,Integer.parseInt(tanklist.tank_level.get(tankIndex)));

                            //tankname1[tankIndex] = part1;
                            tankname1.add(arrayIndex,parts[0]);
                            //tankStatus1[tankIndex] = tanklist.dev_stat.get(tankIndex);
                            tankStatus1.add(arrayIndex,tanklist.dev_stat.get(tankIndex));
                            //tankColor1[tankIndex] = tanklist.color.get(tankIndex);
                            tankColor1.add(arrayIndex,tanklist.color.get(tankIndex));

                            arrayIndex++;
                        }

                    }

                    BarDataSet barDataSet1 = new BarDataSet(dataValues1(tank_level1), "Tank Data");
                    //BarDataSet barStatusData = new BarDataSet(dataValues2(tankStatus1), "tankStatus");

                    barDataSet1.setColor(Color.BLUE);
                   //create object of bardata
                    BarData barData = new BarData();
                    barData.addDataSet(barDataSet1);
                    barData.setBarWidth(0.2f);
                    //barData.getGroupWidth(0f,0.1f);
                    barChart.setDrawValueAboveBar(true);
                    barChart.setPinchZoom(false);
                    barChart.setDrawGridBackground(true);
                    barChart.setData(barData);
                    barChart.getDescription().setEnabled(false);

                    // setting text color.
                    barDataSet1.setValueTextColor(Color.BLACK);

                    // setting text size
                    barDataSet1.setValueTextSize(12);
                    // below line is to make visible
                    // range for our bar chart.
                    barChart.fitScreen();
                    barChart.setVisibleXRangeMaximum(8);
                    barChart.invalidate();


                } else {
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
            //private ArrayList<BarEntry> dataValues1(int [] tank_level1) {
            private ArrayList<BarEntry> dataValues1(List<Integer> tank_level1) {
                YAxis yAxis = barChart.getAxisLeft();
                YAxis rightYAxis = barChart.getAxisRight();
                rightYAxis.setEnabled(false);

                yAxis.setLabelCount(5);
                //yAxis.setAxisMinimum(30);
                yAxis.setAxisMaximum(150);
                XAxis xAxis = barChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                //xAxis.setDrawGridLines(false);

                //xAxis.setCenterAxisLabels(false);
                //xAxis.setLabelRotationAngle(-90);
                xAxis.setLabelRotationAngle(0);
                //tankname label
                xAxis.setValueFormatter(new IndexAxisValueFormatter(tankname1));

                xAxis.setGranularity(1f);
                xAxis.setGranularityEnabled(true);

                ArrayList<String> tankStatus1 = new ArrayList<String>();
                ArrayList<BarEntry> dataVals = new ArrayList<BarEntry>();
                for(int i=0;i<tank_level1.size();i++){
                    dataVals.add(new BarEntry(i,tank_level1.get(i)));

                }
                return dataVals;
            }

            @Override
            public void onFailure(Call<Tanklist> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
                    Toast.makeText(BlockActivity.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
                } else {
                    Log.i("POST", "post failed." + t.getMessage());
                }
            }
        });
    }




}

