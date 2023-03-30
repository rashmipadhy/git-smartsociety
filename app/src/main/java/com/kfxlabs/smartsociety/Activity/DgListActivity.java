package com.kfxlabs.smartsociety.Activity;

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
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kfxlabs.smartsociety.MainActivity;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.model.DgListDetails;
import com.kfxlabs.smartsociety.model.DgMetaDetails;
import com.kfxlabs.smartsociety.model.DgValueDetails;
import com.kfxlabs.smartsociety.module.Dglist;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DgListActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    LoadingDialog loadingDialog;
    Handler handle;
    int buttonMutation;

     com.kfxlabs.smartsociety.api.GetDglist mAPIService = Api.getDglistAPIService(DgListActivity.this);


    public static String ANDROIDID;
    public static String getANDROIDID() {

        return ANDROIDID;
    }

    public static void setANDROIDID(String ANDROIDID) {


        DgListActivity.ANDROIDID = ANDROIDID;
    }

    private static String app;

    //new added lines
    public static String DgName;
    public static String DevId;
    public static String Location;
    public static String DgModel;
    public static String DgMake;
    public static String DevPin;
    public static String DgKva;
    public static String DgEngReading;




    //@SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setANDROIDID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));


     progressDialog =  new ProgressDialog(DgListActivity.this);
     loadingDialog = new LoadingDialog(DgListActivity.this);


        Log.d("tag","on create : mAPIService" + mAPIService);
        addDgPost();


        }


    public void addDgPost() {

        Log.d("URL", "sendPost: " + mAPIService.toString());

        mAPIService.savePost().enqueue(new Callback<Dglist>() {

            @Override
            public void onResponse(Call<Dglist> call, Response<Dglist> response) {


                if (response.isSuccessful()) {

                    Log.i("POST1", "post submitted to API." + response.body().toString());

                    SharedPrefManager manager = SharedPrefManager.getInstance(DgListActivity.this);
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
                        dgValueDetailsList.add(details);

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
            public void onFailure(Call<Dglist> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
                    Toast.makeText(DgListActivity.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
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


    private void saveDgDetailsInSharedPref() {
        DgMetaDetails dgMetaDetails = new DgMetaDetails(DgName, DgMake,DevId,DevPin,Location,DgKva,DgEngReading);
        SharedPrefManager manager = new SharedPrefManager(this);

        manager.addDgMeta(dgMetaDetails);
    }

    public void navigate()
    {
        Intent intent = new Intent(DgListActivity.this, DgActivity.class);
        intent.putExtra("androidID","");
        intent.putExtra("app","");
        intent.putExtra("dgmake",DgMake);
        intent.putExtra("dgloc",Location);
        intent.putExtra("dgname",DgName);
        intent.putExtra("devid",DevId);
        intent.putExtra("devpin",DevPin);
        intent.putExtra("dgreading",DgEngReading);


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
            Intent intent = new Intent(DgListActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}



