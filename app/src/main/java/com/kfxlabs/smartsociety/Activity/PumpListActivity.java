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
import com.kfxlabs.smartsociety.model.PumpMetaDetails;
import com.kfxlabs.smartsociety.model.PumpValueDetails;
import com.kfxlabs.smartsociety.module.Pumplist;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PumpListActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    LoadingDialog loadingDialog;
    Handler handle;
    int buttonMutation;

    com.kfxlabs.smartsociety.api.GetPumplist mAPIService = Api.getPumplistAPIService(PumpListActivity.this);

    public static String ANDROIDID;
    public static String getANDROIDID() {

        return ANDROIDID;
    }

    public static void setANDROIDID(String ANDROIDID) {


        PumpListActivity.ANDROIDID = ANDROIDID;
    }

    private static String app;

    //new added lines
    public static String PName;
    public static String DevId;
    public static String Location;
    public static String PModel;
    public static String PMake;
    public static String DevPin;
    public static String PKva;
    public static String PRunmax;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pump_list);

        setANDROIDID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));
        progressDialog =  new ProgressDialog(PumpListActivity.this);
        loadingDialog = new LoadingDialog(PumpListActivity.this);


        Log.d("tag","on create : mAPIService" + mAPIService);
        // final String dgDetailsFileName = getIntent().getStringExtra("DG_DETAILS");
        addPumpPost();


    }


    public void addPumpPost() {

        Log.d("URL", "sendPost: " + mAPIService.toString());

        mAPIService.savePost().enqueue(new Callback<Pumplist>() {

            @Override
            public void onResponse(Call<Pumplist> call, Response<Pumplist> response) {

                if (response.isSuccessful()) {

                    Log.i("POST1", "post submitted to API." + response.body().toString());

                    SharedPrefManager manager = SharedPrefManager.getInstance(PumpListActivity.this);
                    manager.clearExistingPumpValues();

                Pumplist pumplist = response.body();

                    List<PumpValueDetails> pumpValueDetailsList = new ArrayList<>();
                    int totalPumps = response.body().voltages.size();
                    for( int pumpIndex=0; pumpIndex < totalPumps; pumpIndex++) {
                        PumpValueDetails details = new PumpValueDetails(


                                pumplist.devid.get(pumpIndex),
                                pumplist.voltages.get(pumpIndex),
                               // pumplist.uname.get(pumpIndex),
                                pumplist.pwr_stat.get(pumpIndex),
                                pumplist.pump_name.get(pumpIndex),
                                pumplist.pump_stat.get(pumpIndex),
                                pumplist.alerts.get(pumpIndex),
                                pumplist.health.get(pumpIndex),
                                pumplist.dev_stat.get(pumpIndex),
                                pumplist.run_time.get(pumpIndex)
                        );
                        pumpValueDetailsList.add(details);

                        manager.addPumpValue(details);
                    }

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
            public void onFailure(Call<Pumplist> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
                    Toast.makeText(PumpListActivity.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
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

    private void savePumpDetailsInSharedPref() {
        PumpMetaDetails pumpMetaDetails = new PumpMetaDetails(PName, PMake,DevId,DevPin,Location,PKva);
        SharedPrefManager manager = new SharedPrefManager(this);

        manager.addPumpMeta(pumpMetaDetails);
    }

    public void navigate()
    {
        Intent intent = new Intent(PumpListActivity.this, PumpActivity.class);
        intent.putExtra("androidID","");
        intent.putExtra("app","");
        intent.putExtra("pmake",PMake);
        intent.putExtra("ploc",Location);
        intent.putExtra("pname",PName);
        intent.putExtra("devid",DevId);
        intent.putExtra("devpin",DevPin);


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
            Intent intent = new Intent(PumpListActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}



