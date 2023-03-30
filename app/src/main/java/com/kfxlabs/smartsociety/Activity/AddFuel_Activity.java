package com.kfxlabs.smartsociety.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.Calendar;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.model.DgMetaDetails;
import com.kfxlabs.smartsociety.model.DgValueDetails;
import com.kfxlabs.smartsociety.module.AddDgFuelPost;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;


import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFuel_Activity extends AppCompatActivity {

    EditText tvDate;
    EditText tvTime;
    String devid;
    String fuel;
    String tFuel;
    String perc;
    String dgname;
    String uphone;
    String dateApiFormat;
    EditText et_ffill;
    EditText et_fuellvl;
    EditText et_remark;
    int buttonMutation;
    ProgressDialog progressDialog;
    LoadingDialog loadingDialog;
    RadioGroup radioGroup;
    RadioButton checkedRadioButton,lRadioButton;
    Button save;
    Handler handle;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String selectedDeviceId = "";
    String fval;
    com.kfxlabs.smartsociety.api.AddDgFuel mAPIService = Api.getAddDgFuelAPIService(AddFuel_Activity.this);
    com.kfxlabs.smartsociety.api.GetDglist dgAPIService = Api.getDglistAPIService(AddFuel_Activity.this);

    private static String ANDROIDID;
    public static String getANDROIDID() {

        return ANDROIDID;
    }

    public static void setANDROIDID(String ANDROIDID) {


        AddFuel_Activity.ANDROIDID = ANDROIDID;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fuel2);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("Add Refuel Data");
        }



        setANDROIDID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));


//new code added

        SharedPrefManager manager = new SharedPrefManager(AddFuel_Activity.this);
        List<DgValueDetails> detailsList = manager.getDgValueDetailsSingle();
        for(DgValueDetails details: detailsList) {
            devid = details.dgDevId;
            fval= details.fval;
        }


        dgname=getIntent().getStringExtra("dgname");

        tvDate =findViewById(R.id.et_refueldate);
        tvTime = findViewById(R.id.et_refueltime);
        et_ffill = findViewById(R.id.et_refuelfill);
        et_fuellvl = findViewById(R.id.et_fuellvl);
        et_remark = findViewById(R.id.et_refuelremark);
        save = findViewById(R.id.save_refuel);
        buttonMutation = R.drawable.save;
        progressDialog =  new ProgressDialog(AddFuel_Activity.this);
        loadingDialog = new LoadingDialog(AddFuel_Activity.this);
        radioGroup =findViewById(R.id.rg_fuellvl);
        checkedRadioButton =radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        lRadioButton =findViewById(R.id.rb_litres);

        et_fuellvl.setEnabled(false);

        if(lRadioButton.isChecked()){
            perc="0";
            et_fuellvl.setEnabled(true);
        }


        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton cRadioButton =group.findViewById(checkedId);  boolean checked = cRadioButton.isChecked();

            if(checkedId==R.id.rb_litres){
                perc="0";
            }
            else if(checkedId==R.id.rb_perc){
                perc="1";
            }

            if(checked && !perc.isEmpty()){
                et_fuellvl.setEnabled(true);
            }

        });

        tvDate.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            Toast.makeText(AddFuel_Activity.this,"Date fill",Toast.LENGTH_LONG);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddFuel_Activity.this,
                    (view, year, monthOfYear, dayOfMonth) -> {

                        String temp_mnth = (monthOfYear+1)+"";
                        String temp_day = dayOfMonth+"";
                        String temp_year = year+"";
                        temp_year = temp_year.substring(2,4);

                        if(temp_mnth.length()==1){
                            temp_mnth = 0+temp_mnth;
                        }

                        if(temp_day.length()==1){
                            temp_day = 0+temp_day;
                        }

                        tvDate.setText(String.format("%s/%s/%s", temp_day, temp_mnth, temp_year));
                        dateApiFormat = temp_day+""+temp_mnth+""+temp_year;

                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        tvTime.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog

            TimePickerDialog timePickerDialog = new TimePickerDialog(AddFuel_Activity.this,android.R.style.Theme_Holo_Light_Dialog,
                    (view, hourOfDay, minute) -> {

                        String temp_hrs=hourOfDay+"";
                        String temp_mins=minute+"";

                        if(temp_hrs.length()>1){
                            temp_hrs=hourOfDay+"";
                        }else{
                            temp_hrs=0+""+hourOfDay;
                        }

                        if(temp_mins.length()>1){
                            temp_mins=minute+"";
                        }else{
                            temp_mins=0+""+minute;
                        }

                        tvTime.setText(String.format("%s:%s", temp_hrs, temp_mins));
                    }, mHour, mMinute, true);
            timePickerDialog.show();

        });

        et_ffill.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        et_fuellvl.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        et_remark.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        mAPIService = Api.getAddDgFuelAPIService(AddFuel_Activity.this);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String dateInput = tvDate.getText().toString().trim();
                final String timeInput = tvTime.getText().toString().trim();
                final String fuelFillInput = et_ffill.getText().toString().trim();
                final String fuelLevelInput = et_fuellvl.getText().toString().trim();
                final String fuelRemarkInput = et_remark.getText().toString().trim();

                handle = new Handler(getMainLooper()) {
                    @Override
                    public void handleMessage(@NotNull Message msg) {
                        super.handleMessage(msg);
                    }
                };

                if (dateInput.isEmpty() && timeInput.isEmpty() && fuelFillInput.isEmpty()) {
                    et_ffill.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    tvDate.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    tvTime.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(AddFuel_Activity.this, "Please enter the Details", Toast.LENGTH_LONG).show();
                } else if (dateInput.isEmpty()) {
                    tvDate.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(AddFuel_Activity.this, "Please choose the Date", Toast.LENGTH_LONG).show();
                } else if (timeInput.isEmpty()) {
                    tvTime.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(AddFuel_Activity.this, "Please choose the Time", Toast.LENGTH_LONG).show();
                } else if (fuelFillInput.isEmpty()) {
                    et_ffill.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(AddFuel_Activity.this, "Please enter the fuel to filled ", Toast.LENGTH_LONG).show();
                } else {


                    boolean networkAvailable = isNetworkAvailable();


                    if (perc.equals("0") && !(fuelLevelInput.isEmpty()) && (Integer.parseInt(fuelLevelInput) > Integer.parseInt(fval))){
                        et_fuellvl.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        Toast.makeText(AddFuel_Activity.this, "Exceeded the fuel capacity ", Toast.LENGTH_LONG).show();
                    } else if (perc.equals("1") && !(fuelLevelInput.isEmpty()) && (Integer.parseInt(fuelLevelInput) > Integer.parseInt("100"))) {

                        et_fuellvl.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        Toast.makeText(AddFuel_Activity.this, "Exceeded the fuel capacity ", Toast.LENGTH_LONG).show();
                    } else if (networkAvailable) {
                        loadingDialog.startLoadingDialog();
                        Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            if(!AddFuel_Activity.this.isFinishing() && loadingDialog != null){
                                loadingDialog.dismiss();
                                loadingDialog.dismiss();
                            addFuelPost(ANDROIDID, devid, dateApiFormat, timeInput, fuelFillInput, fuelLevelInput, fuelRemarkInput, perc);
                            }


                        },200);
                    }
                    else {

                        Toast.makeText(AddFuel_Activity.this, "Please Check your internet connection", Toast.LENGTH_LONG).show();
                    }
                }

                Intent intent = new Intent(AddFuel_Activity.this, FuelLogActivity.class);
                intent.putExtra("device_id", selectedDeviceId);

            }
        });

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

    public static void setImageButtonEnabled(Context ctxt, boolean enabled,
                                             Button item, int iconResId) {

        item.setEnabled(enabled);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable originalIcon = ctxt.getResources().getDrawable(iconResId);
        Drawable icon = enabled ? originalIcon : convertDrawableToGrayScale(originalIcon);
        item.setBackground(icon);
    }

    public static Drawable convertDrawableToGrayScale(Drawable drawable) {
        if (drawable == null)
            return null;

        Drawable res = drawable.mutate();
        res.setColorFilter(Color.parseColor("#802719b5"), PorterDuff.Mode.SRC_IN);
        return res;
    }

    boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        assert wifiNetworkInfo != null;
        return activeNetworkInfo != null && (activeNetworkInfo.isConnected()||wifiNetworkInfo.isConnected());
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void addFuelPost(final String androidID,final String devid, final String date, final String time, final String ffill, final String fuel, final String remarks,String perc) {

        Log.d("URL", "sendPost: " + mAPIService.toString());

        mAPIService.savePost(androidID,devid, date, time, ffill,fuel,remarks,perc).enqueue(new Callback<AddDgFuelPost>() {

            @Override
            public void onResponse(Call<AddDgFuelPost> call, Response<AddDgFuelPost> response) {


                Log.i("POST1", androidID+devid+date+time+ffill+fuel+remarks );

                if (response.isSuccessful()) {

                    Log.i("POST1", "post submitted to API." + response.body().getReply());
                    switch (response.body().getReply()) {
                        case "OK":
                            finish();
                            break;
                        case "Invalid PIN":
                        case "Invalid Device ID":
                            Toast.makeText(AddFuel_Activity.this, response.body().getReply(), Toast.LENGTH_LONG).show();
                            break;
                    }
                }else{
                    try {
                        if (response.errorBody() != null) {
//                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getApplicationContext(), "Error occurred.Try again.", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onFailure(Call<AddDgFuelPost> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
                    Toast.makeText(AddFuel_Activity.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(AddFuel_Activity.this, "Unable to submit post to API.Response gets failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.general_menu, menu);

        return true;
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
