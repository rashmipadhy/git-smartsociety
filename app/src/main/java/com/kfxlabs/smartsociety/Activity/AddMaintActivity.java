package com.kfxlabs.smartsociety.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Calendar;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.AddDgMaint;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.model.DgValueDetails;
import com.kfxlabs.smartsociety.module.AddDgFuelPost;
import com.kfxlabs.smartsociety.module.AddDgMaintPost;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMaintActivity extends AppCompatActivity {


    EditText tvDate;
    EditText ehours;
    EditText eremark;
    Spinner spinner;
    String mtype;
    String devid;
    String fval;
    String fuel;
    String dgname;
    String uphone;
    String dateApiFormat;
    String app="dg";
    int buttonMutation;
    ProgressDialog progressDialog;
    LoadingDialog loadingDialog;
    Button save;
    com.kfxlabs.smartsociety.api.AddDgMaint mAPIService = Api.getAddDgMaintAPIService(AddMaintActivity.this);
    private int mYear, mMonth, mDay;
    public static String getANDROIDID() {
        return ANDROIDID;
    }

    public static void setANDROIDID(String ANDROIDID) {
        AddMaintActivity.ANDROIDID = ANDROIDID;
    }

    private static String ANDROIDID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_maint);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvDate =  findViewById(R.id.et_maintdate);
        ehours =  findViewById(R.id.et_enginehrs);
        eremark = findViewById(R.id.et_maintremark);
        spinner = findViewById(R.id.et_mainttype);
        save = findViewById(R.id.save_maint);
        buttonMutation = R.drawable.save;
        progressDialog =  new ProgressDialog(AddMaintActivity.this);
        loadingDialog = new LoadingDialog(AddMaintActivity.this);


       ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.strarray, R.layout.custom_spinner);
       staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinner.setAdapter(staticAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mtype = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { mtype="";

            }
        });


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("Add Maintenance Data");
        }

        setANDROIDID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));


        SharedPrefManager manager = new SharedPrefManager(AddMaintActivity.this);
        List<DgValueDetails> detailsList = manager.getDgValueDetailsSingle();
        for(DgValueDetails details: detailsList) {
            devid = details.dgDevId;
            fval= details.fval;
        }
        


        tvDate.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            Toast.makeText(AddMaintActivity.this,"Date fill",Toast.LENGTH_LONG);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddMaintActivity.this,
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

        ehours.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        eremark.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        mAPIService = Api.getAddDgMaintAPIService(AddMaintActivity.this);

        save.setOnClickListener(v -> {
            final String dateInput = tvDate.getText().toString().trim();
            final String mtypeInput = mtype.trim();
            final String eHoursInput = ehours.getText().toString().trim();
            final String eremarkInput = eremark.getText().toString().trim();


            if(dateInput.isEmpty()&&eHoursInput.isEmpty()&&eremarkInput.isEmpty()){
                ehours.onEditorAction(EditorInfo.IME_ACTION_DONE);
                tvDate.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Toast.makeText(AddMaintActivity.this, "Please enter the Details", Toast.LENGTH_LONG).show();
            }
            else if (dateInput.isEmpty()){
                tvDate.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Toast.makeText(AddMaintActivity.this, "Please choose the Date", Toast.LENGTH_LONG).show();
            }
            else if (eHoursInput.isEmpty()){
                ehours.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Toast.makeText(AddMaintActivity.this, "Please enter the engine hours", Toast.LENGTH_LONG).show();
            }
            else {


                boolean networkAvailable = isNetworkAvailable();
                if(networkAvailable) {

                    loadingDialog.startLoadingDialog();
                    loadingDialog.dismiss();
                    addMaintPost(ANDROIDID, devid, dateApiFormat, mtypeInput, eHoursInput,eremarkInput );

                }
                else {
                    Toast.makeText(AddMaintActivity.this, "Please Check your internet connection", Toast.LENGTH_LONG).show();
                }
            }

            setImageButtonEnabled(AddMaintActivity.this, true, save, buttonMutation);

        });

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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void addMaintPost(final String androidID, final String devid, final String date, final String mtype, final String ehours,  final String remarks) {

        Log.d("URL", "sendPost: " + mAPIService.toString());
        mAPIService.savePost(androidID,devid,date,mtype,ehours,remarks).enqueue(new Callback<AddDgMaintPost>() {
            @Override
            public void onResponse(Call<AddDgMaintPost> call, Response<AddDgMaintPost> response) {

                Log.i("POST1", androidID+devid+date+mtype+ehours+remarks );
                System.out.println("androidId: " + androidID);
                System.out.println("devid: " + devid);
                System.out.println("date: " + date);
                System.out.println("mtype: " + mtype);
                System.out.println("ehours: " + ehours);
                System.out.println("remarks: " + remarks);



                if (response.isSuccessful()) {

                    Log.i("POST1", "post submitted to API." + response.body().getReply());
                    switch (response.body().getReply()) {
                        case "OK":
                            finish();
                            break;
                        case "Invalid DevID":
                            Toast.makeText(AddMaintActivity.this, response.body().getReply(), Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<AddDgMaintPost> call, Throwable t) {

                if (t instanceof SocketTimeoutException) {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
                    Toast.makeText(AddMaintActivity.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(AddMaintActivity.this, "Unable to submit post to API.Response gets failed", Toast.LENGTH_LONG).show();
                }
            }        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.general_menu, menu);

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
