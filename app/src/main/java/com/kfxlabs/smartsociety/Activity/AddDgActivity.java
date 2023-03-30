package com.kfxlabs.smartsociety.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kfxlabs.smartsociety.MainActivity;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;
import com.kfxlabs.smartsociety.model.DgMetaDetails;
import com.kfxlabs.smartsociety.module.AddDgPost;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDgActivity extends AppCompatActivity {

    public static String ANDROIDID;
    //new added lines
    public static String DgName;
    public static String DevId;
    public static String Location;
    public static String DgModel;
    public static String DgMake;
    public static String DevPin;
    public static String DgKva;
    public static String DgEngReading;
    private static String app;
    Button btAddDg;
    EditText etDgName,etDevId,etDevPin,etLocation,etDgMake,etDgModel,etDgKva,etDgEngReading;
    String phoneNumber;
    ProgressDialog progressDialog;
    LoadingDialog loadingDialog;
    Handler handle;
    int buttonMutation;
    com.kfxlabs.smartsociety.api.AddDg mAPIService = Api.getAddDgAPIService(AddDgActivity.this);

    public static String getANDROIDID() {

        return ANDROIDID;
    }

    public static void setANDROIDID(String ANDROIDID) {


        AddDgActivity.ANDROIDID = ANDROIDID;
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

    //@SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dg);

        setANDROIDID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btAddDg = findViewById(R.id.dgbtAddMotor);
        etDgName = findViewById(R.id.dgetMotorName);
        etDevId = findViewById(R.id.dgetSimNumber);
        etDevPin = findViewById(R.id.dgetSecretKey);
        etLocation = findViewById(R.id.dgetlocation);
        etDgModel = findViewById(R.id.dget_model);
        etDgMake = findViewById(R.id.dget_make);
        etDgKva = findViewById(R.id.dget_kva);
        etDgEngReading = findViewById(R.id.dget_initial_reading);
        progressDialog =  new ProgressDialog(AddDgActivity.this);
        buttonMutation = R.drawable.background_01;
        loadingDialog = new LoadingDialog(AddDgActivity.this);


        Log.d("tag","on create : mAPIService" + mAPIService);

        final String dgDetailsFileName = getIntent().getStringExtra("DG_DETAILS");
        final String credentialsFileName = getIntent().getStringExtra("USER_CREDENTIALS");

        SharedPreferences userCredentials = getSharedPreferences(credentialsFileName, MODE_PRIVATE);
        phoneNumber =  userCredentials.getString("phone_number", "");

        etDgName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        etDevId.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        etDevPin.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        etLocation.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        etDgModel.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        etDgMake.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        etDgKva.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        etDgEngReading.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("ADD DG");
        }

        btAddDg.setOnClickListener(v -> {



                DgName = etDgName.getText().toString().trim();
                DevId = etDevId.getText().toString().trim();
                DevPin = etDevPin.getText().toString().trim();
                Location = etLocation.getText().toString().trim();
                DgMake = etDgMake.getText().toString().trim();
                DgModel = etDgModel.getText().toString().trim();
                DgKva = etDgKva.getText().toString().trim();
                DgEngReading = etDgEngReading.getText().toString().trim();


        SharedPreferences prefs = getSharedPreferences(dgDetailsFileName, MODE_PRIVATE);

                String devIdStored = null;
                String dgNameStored = null;

                if (DgName.isEmpty() && DevId.isEmpty() && DevPin.isEmpty() && Location.isEmpty()&& DgMake.isEmpty() && DgModel.isEmpty() && DgKva.isEmpty() && DgEngReading.isEmpty()) {
                    etDgName.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    etDevId.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    etLocation.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    etDevPin.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    etDgMake.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    etDgModel.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    etDgKva.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    etDgEngReading.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(AddDgActivity.this, "Please enter the Details", Toast.LENGTH_LONG).show();
                }
                else if (DgName.isEmpty()) {
                    etDgName.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(AddDgActivity.this, "Motor name cannot be empty", Toast.LENGTH_LONG).show();
                }
                else if (DevId.isEmpty()) {
                    etDevId.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(getApplicationContext(), "Unit No cannot be empty", Toast.LENGTH_LONG).show();
                }
                else if (DevPin.isEmpty()) {
                    etDevPin.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(getApplicationContext(), "Unit Pin cannot be empty", Toast.LENGTH_LONG).show();
                }
                else if (Location.isEmpty()) {
                    etLocation.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(getApplicationContext(), "Location cannot be empty", Toast.LENGTH_LONG).show();
                }
                else if (DgMake.isEmpty()) {
                    etDgMake.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(getApplicationContext(), "Dg Make cannot be empty", Toast.LENGTH_LONG).show();
                }
                else if (DgModel.isEmpty()) {
                    etDgModel.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(getApplicationContext(), "Dg Model cannot be empty", Toast.LENGTH_LONG).show();
                }
                else if (DgKva.isEmpty()) {
                    etDgKva.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(getApplicationContext(), "Dg KVA cannot be empty", Toast.LENGTH_LONG).show();
                }
                else if (DgEngReading.isEmpty()) {
                    etDgKva.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(getApplicationContext(), "Dg Initial Engine Reading cannot be empty", Toast.LENGTH_LONG).show();
                }
                else if (DgName.equalsIgnoreCase(dgNameStored) || DevId.equals(devIdStored)) //makes motor name and sim number unique
                {
                    etDgName.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    Toast.makeText(AddDgActivity.this, "Dg already exists.Please Enter new details", Toast.LENGTH_LONG).show();
                }
                else {


                    boolean networkAvailable = isNetworkAvailable();
                    if(networkAvailable) {
                  loadingDialog.startLoadingDialog();

                        Handler handler = new Handler();
                        handler.postDelayed(() -> {

                            if(!AddDgActivity.this.isFinishing() && loadingDialog !=null){
                                loadingDialog.dismiss();
                                addDgPost(ANDROIDID,app, phoneNumber,Location, DevPin, DevId, DgName,DgMake,DgModel,DgKva,DgEngReading, dgDetailsFileName);
                            }
                        },200);

          }
                    else {
                        Toast.makeText(AddDgActivity.this, "Please Check your internet connection", Toast.LENGTH_LONG).show();
                    }

                }
                setImageButtonEnabled(AddDgActivity.this, true, btAddDg, buttonMutation);

        });
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

    public void addDgPost(final String androidID,final String app, String uphone,String location, String devpin, String devid, String dgname,String dgmake,String dgmodel,String dgkva,String dgInitialEngReading, final String dgDetailsFilesName) {

        Log.d("URL", "sendPost: " + mAPIService.toString());

        mAPIService.savePost(location,  dgname, devid, devpin,dgmake,dgmodel,dgkva,dgInitialEngReading).enqueue(new Callback<AddDgPost>() {
            @Override
            public void onResponse(Call<AddDgPost> call, Response<AddDgPost> response) {

                if (response.isSuccessful()) {

                    Log.i("POST1", "post submitted to API." + response.body().toString());
                    SharedPrefManager.getInstance(AddDgActivity.this).storeDgInfo(androidID,app,uphone,location,devpin,devid,dgname,dgmake,dgmodel,dgkva,dgInitialEngReading,dgDetailsFilesName);
                    Toast.makeText(AddDgActivity.this, response.body().getReply(), Toast.LENGTH_LONG).show();
                    navigate();


                }else {
                    Log.i("POST", "failed");
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

                public void onFailure(Call<AddDgPost> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
                    Toast.makeText(AddDgActivity.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
                }else{
                    Log.i("POST", "post failed."+ t.getMessage());
                }
            }
        });
    }


    private void saveDgDetailsInSharedPref() {
        DgMetaDetails dgMetaDetails = new DgMetaDetails(DgName, DgMake,DevId,DevPin,Location,DgKva,DgEngReading);
        SharedPrefManager manager = new SharedPrefManager(this);

        manager.addDgMeta(dgMetaDetails);
    }

    public void navigate()
    {
        Intent intent = new Intent(AddDgActivity.this, DgActivity.class);
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
        menuInflater.inflate(R.menu.nav_bar_01, menu);

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
