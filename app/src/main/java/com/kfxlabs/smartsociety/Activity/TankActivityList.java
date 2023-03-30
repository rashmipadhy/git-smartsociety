package com.kfxlabs.smartsociety.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.kfxlabs.smartsociety.MainActivity;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.model.DgMetaDetails;
import com.kfxlabs.smartsociety.model.DgValueDetails;
import com.kfxlabs.smartsociety.model.TankMetaDetails;
import com.kfxlabs.smartsociety.model.TankValueDetails;
import com.kfxlabs.smartsociety.module.Dglist;
import com.kfxlabs.smartsociety.module.Tanklist;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TankActivityList extends AppCompatActivity {

    ProgressDialog progressDialog;
    LoadingDialog loadingDialog;
    Handler handle;
    int buttonMutation;

    com.kfxlabs.smartsociety.api.GetTanklist mAPIService = Api.getTanklistAPIService(TankActivityList.this);

    public static String ANDROIDID;
    public static String getANDROIDID() {
        return ANDROIDID;
    }

    public static void setANDROIDID(String ANDROIDID) {

        TankActivityList.ANDROIDID = ANDROIDID;
    }

    private static String app;

    //new added lines
    public static String TName;
    public static String DevId;
    public static String Location;
    public static String TType;
    public static String TCapacity;
    public static String DevPin;
    public static String TDepth;
    public static String TOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tank_list);
        setANDROIDID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));
        progressDialog =  new ProgressDialog(TankActivityList.this);
        loadingDialog = new LoadingDialog(TankActivityList.this);


        Log.d("tag","on create : mAPIService" + mAPIService);
        // final String dgDetailsFileName = getIntent().getStringExtra("DG_DETAILS");
        addTankPost();


    }


    public void addTankPost() {

        Log.d("URL", "sendPost: " + mAPIService.toString());

        System.out.println(" Value for the URL1" + mAPIService.toString());

        mAPIService.savePost().enqueue(new Callback<Tanklist>() {

            @Override
            public void onResponse(Call<Tanklist> call, Response<Tanklist> response) {



                if (response.isSuccessful()) {

                    Log.i("POST1", "post submitted to API." + response.body().toString());

                    SharedPrefManager manager = SharedPrefManager.getInstance(TankActivityList.this);
                    manager.clearExistingTankValues();

                    Tanklist tanklist = response.body();
                    List<TankValueDetails> tankValueDetailsList = new ArrayList<>();
                    int totalTanks = response.body().tank_name.size();
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
                        tankValueDetailsList.add(details);

                        manager.addTankValue(details);
                    }

                    //SharedPrefManager.getInstance(DgListActivity.this).storeDGName(dglist);
                    navigate();
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
                    Toast.makeText(TankActivityList.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
                }else{
                    Log.i("POST", "post failed."+ t.getMessage());
                }
            }
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

    boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        assert wifiNetworkInfo != null;
        return activeNetworkInfo != null && (activeNetworkInfo.isConnected()||wifiNetworkInfo.isConnected());
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

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    private void saveTankDetailsInSharedPref() {
        TankMetaDetails tankMetaDetails = new TankMetaDetails(TName,TType,DevId,DevPin,Location,TCapacity,TDepth,TOffset);
        SharedPrefManager manager = new SharedPrefManager(this);

        manager.addTankMeta(tankMetaDetails);
    }

    public void navigate()
    {
        Intent intent = new Intent(TankActivityList.this, WaterActivity.class);
        intent.putExtra("androidID","");
        intent.putExtra("app","");
        intent.putExtra("tname",TName);
        intent.putExtra("tloc",Location);
        intent.putExtra("ttype",TType);
        intent.putExtra("devid",DevId);
        intent.putExtra("devpin",DevPin);
        intent.putExtra("tcapacity",TCapacity);
        intent.putExtra("tdepth",TDepth);
        intent.putExtra("toffset",TOffset);



        startActivity(intent);
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
            Intent intent = new Intent(TankActivityList.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}



