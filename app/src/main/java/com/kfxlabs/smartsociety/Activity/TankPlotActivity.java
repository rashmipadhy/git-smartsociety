package com.kfxlabs.smartsociety.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.gson.Gson;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.api.GetTankPlot;
import com.kfxlabs.smartsociety.model.TankPlotDetails;
import com.kfxlabs.smartsociety.module.TankPlot;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TankPlotActivity extends AppCompatActivity {

    String deviceId = "";
    String selectedDeviceId = "";
    String tankname = "";
    RecyclerView recyclerView;
    public static String getANDROIDID() {
        return ANDROIDID;
    }
    public static void setANDROIDID(String ANDROIDID) {
        TankPlotActivity.ANDROIDID = ANDROIDID;
    }
    private static String ANDROIDID;
    GetTankPlot getTankPlotAPIService;
    //com.kfxlabs.smartsociety.api.GetTankPlot mAPIService = Api.getTankPlotAPIService(TankPlotActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tank_plot);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LineChart lineChart = findViewById(R.id.lineChart);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);

        GetTankPlot mAPIService = Api.getTankPlotAPIService(TankPlotActivity.this);
        setANDROIDID(Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID));
        getTankPlotAPIService = Api.getTankPlotAPIService(TankPlotActivity.this);
        Log.d("tag", "on create: mAPIService: " + mAPIService);

        if ( getIntent().getStringExtra("device_id") != null) {
            deviceId = getIntent().getStringExtra("device_id");
            tankname = getIntent().getStringExtra("tank_name");
            getDataFromServer(deviceId,tankname); // API Call
            //Log.d("deviceId", "ID: "+deviceId);
            //Log.d("tankname","TANKNAME: "+tankname);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle(tankname);
        }



    }

    private void getDataFromServer(String dev_id,String tankname) {
        Log.d("URL","sendPost" +dev_id);
        getTankPlotAPIService.savePost(deviceId).enqueue(new Callback<TankPlot>() {
            @Override
            public void onResponse(Call<TankPlot> call, Response<TankPlot> response) {
                if (response.isSuccessful()) {
                    Log.i("POST1", "post submitted to API." + response.body().toString());
                    Log.d("rest data", "data"+new Gson().toJson(response.body()));

                    SharedPrefManager manager = SharedPrefManager.getInstance(TankPlotActivity.this);
                    manager.clearExistingTankPlotValues();
                    TankPlot tankPlot = response.body();
                    List<TankPlotDetails> tankPlotDetailsList = new ArrayList<>();
                    int totalDgs = response.body().x.size();
                    for(int i=0; i < totalDgs; i++) {
                        TankPlotDetails details = new TankPlotDetails(
                                tankPlot.y.get(i),
                                tankPlot.x.get(i)
                        );
                        //Log.d("details", "details"+details);
                        tankPlotDetailsList.add(details);
                        manager.addTankPlot(details);
                    }
                    updatePlotDetails(tankPlot,tankname);


                }else {
                    Log.i("POSTA", "failed");
                }
                try {
                    if (response.errorBody() != null) {
                        Toast.makeText(getApplicationContext(), "Error occurred.Try again.", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();


                }}

            @Override
            public void onFailure(Call<TankPlot> call, Throwable t) {

                if (t instanceof SocketTimeoutException) {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
                    Toast.makeText(TankPlotActivity.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
                }else{
                    Log.i("POST", "post failed."+ t.getMessage());
                }
            }

        });

    }

    private void updatePlotDetails(TankPlot tankPlot, String tankname) {
        selectedDeviceId = deviceId;

        LineChart lineChart = findViewById(R.id.lineChart);
        //ArrayList<Entry> tanklevel = new ArrayList<>();

        List<Entry> lineEntries = getDataSet(tankPlot);
        //LineDataSet lineDataSet = new LineDataSet(lineEntries, "Tank Level");
        //LineDataSet lineDataSet = new LineDataSet(lineEntries, deviceId);
        Log.i("tankname", "TankName: "+ tankname);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String currentDateandTime = sdf.format(new Date());
        LineDataSet lineDataSet = new LineDataSet(lineEntries, currentDateandTime);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setHighlightEnabled(true);
        lineDataSet.setLineWidth(2);
        lineDataSet.setColor(Color.BLUE);
        lineDataSet.setCircleColor(Color.BLACK);
        lineDataSet.setCircleRadius(1);
        //lineDataSet.setCircleHoleRadius(6);
        //lineDataSet.setDrawCubic(true);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER); // for cubic line chart
        lineDataSet.setDrawFilled(true);

        //lineDataSet.setFillColor(ContextCompat.getColor(context,R.color.green));
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHighlightIndicators(true);
        lineDataSet.setHighLightColor(Color.RED);
        lineDataSet.setValueTextSize(0f);  //deactivate to display the XAxis value
        lineDataSet.setValueTextColor(Color.DKGRAY);
        lineDataSet.setMode(LineDataSet.Mode.LINEAR);


        LineData lineData = new LineData(lineDataSet);
        lineChart.getDescription().setTextSize(9);
        lineChart.getDescription().setEnabled(false);
        lineChart.setDrawMarkers(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.animateY(1000);
        lineChart.getXAxis().setGranularityEnabled(true);
        lineChart.getXAxis().setGranularity(10000000);
        lineChart.setData(lineData);

        ArrayList<String> xAxisLabel = new ArrayList<>();
        //xAxisLabel.add("Tank level");


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //xAxis.setValueFormatter(new MyXAxisValueFormatter());
        IAxisValueFormatter MyXAxisValueFormatter = new  MyXAxisValueFormatter();
        xAxis.setValueFormatter(MyXAxisValueFormatter); // displaying the time
        xAxisLabel.add("Tank level");
        //xAxis.setDrawValues(false);

        /*xAxis.setAxisMaximum(2400);
        xAxis.setAxisMinimum(0000);
        xAxis.setGranularity(0100);*/

        xAxis.setGranularity(1f);
        ArrayList<String> yAxisLabel = new ArrayList<>();
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(50);
        yAxis.setAxisMaximum(110);
        yAxis.setGranularity(5);

        lineChart.getAxisRight().setEnabled(false);

        lineChart.invalidate();

    }

    private List<Entry> getDataSet(TankPlot tankPlot) {
        List<Entry> lineEntries = new ArrayList<>();

        int totalDgs = tankPlot.x.size();
        //Log.d("date","date: "+tankPlot.x);
        long x = 0;
        int y = 0;

        for(int i=0; i < totalDgs ; i++) {
            TankPlotDetails details = new TankPlotDetails(tankPlot.y.get(i),tankPlot.x.get(i));
            x = details.x_list;
            long unixSeconds = details.x_list;
            y = details.y_list;
            lineEntries.add(new Entry(x,y));
        }
        System.out.println(lineEntries);

        return lineEntries;
    }
}

    //    System.out.println("xxxx"+details.x_list);

        /*BarChart barChart = findViewById(R.id.lineChart);
        ArrayList<BarEntry> tanklevel = new ArrayList<>();

        int totalDgs = tankPlot.x.size();
      int x = 0;
        for(int i=0; i < totalDgs; i++) {
            TankPlotDetails details = new TankPlotDetails(
                    tankPlot.y.get(i),
                    tankPlot.x.get(i)
            );
     x = x + 5;
       //     float xd = details.x_list;

//            int x = (int) xd;
            System.out.println("x:" + x);
            int y = details.y_list;
            tanklevel.add(new BarEntry(x,y));
            }


        BarDataSet barDataSet = new BarDataSet(tanklevel,"Tank Level");
        barDataSet.setColor(Color.RED);
        barDataSet.setValueTextSize(16f);
        barDataSet.setValueTextColor(Color.BLACK);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(7);
        xAxis.setCenterAxisLabels(true);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("bar chart");
        barChart.animateY(2000);

}}
*/
