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
import com.kfxlabs.smartsociety.model.PumpMetaDetails;
import com.kfxlabs.smartsociety.model.TankMetaDetails;
import com.kfxlabs.smartsociety.module.AddPumpPost;
import com.kfxlabs.smartsociety.module.AddTankPost;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTankActivity extends AppCompatActivity {

    Button btAddTank;
    EditText etTName,etDevId,etDevPin,etTLocation,etTType,etTCapacity,etTDepth,etTOffset;
    String phoneNumber;
    ProgressDialog progressDialog;
    LoadingDialog loadingDialog;
    Handler handle;
    int buttonMutation;

    com.kfxlabs.smartsociety.api.AddTank mAPIService = Api.getAddTankAPIService(AddTankActivity.this);
    public static String ANDROIDID;
    public static String getANDROIDID() {

        return ANDROIDID;
    }

    public static void setANDROIDID(String ANDROIDID) {


        AddTankActivity.ANDROIDID = ANDROIDID;
    }
    public static String TName;
    public static String DevId;
    public static String Location;
    public static String TCapacity;
    public static String TType;
    public static String DevPin;
    public static String TDepth;
    public static String TOffset;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tank);
        setANDROIDID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btAddTank = findViewById(R.id.tankbtAddMotor);
        etTName = findViewById(R.id.t_name);
        etDevId = findViewById(R.id.t_devid);
        etDevPin = findViewById(R.id.t_upin);
        etTLocation = findViewById(R.id.tankLoc);
        etTCapacity = findViewById(R.id.t_capacity);
        etTOffset = findViewById(R.id.t_offset);
        etTDepth = findViewById(R.id.t_depth);
        etTType = findViewById(R.id.t_type);
        progressDialog =  new ProgressDialog(AddTankActivity.this);
        buttonMutation = R.drawable.background_01;
        loadingDialog = new LoadingDialog(AddTankActivity.this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("Add Tank");
        }
        Log.d("tag","on create : mAPIService" + mAPIService);

        // Log.d("tag","on create : mAPIService" + IApiService);
        final String tankDetailsFileName = getIntent().getStringExtra("TANK_DETAILS");
        final String credentialsFileName = getIntent().getStringExtra("USER_CREDENTIALS");

        SharedPreferences userCredentials = getSharedPreferences(credentialsFileName, MODE_PRIVATE);
        phoneNumber =  userCredentials.getString("phone_number", "");

        //final Gson gson = new Gson();


        etTName.setOnFocusChangeListener((v, hasFocus) -> {
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
        etTLocation.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        etTCapacity.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        etTType.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        etTDepth.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        etTOffset.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("ADD TANK");
        }

        btAddTank.setOnClickListener(v -> {

            TName = etTName.getText().toString().trim();
            DevId = etDevId.getText().toString().trim();
            DevPin = etDevPin.getText().toString().trim();
            Location = etTLocation.getText().toString().trim();
            TCapacity = etTCapacity.getText().toString().trim();
            TType = etTType.getText().toString().trim();
            TDepth = etTDepth.getText().toString().trim();
            TOffset = etTOffset.getText().toString().trim();

            SharedPreferences prefs = getSharedPreferences(tankDetailsFileName, MODE_PRIVATE);


            String devIdStored = null;
            String tankNameStored = null;


            if (TName.isEmpty() && DevId.isEmpty() && DevPin.isEmpty() && Location.isEmpty()&& TCapacity.isEmpty() && TDepth.isEmpty() && TType.isEmpty()) {
                etTName.onEditorAction(EditorInfo.IME_ACTION_DONE);
                etDevId.onEditorAction(EditorInfo.IME_ACTION_DONE);
                etTLocation.onEditorAction(EditorInfo.IME_ACTION_DONE);
                etDevPin.onEditorAction(EditorInfo.IME_ACTION_DONE);
                etTCapacity.onEditorAction(EditorInfo.IME_ACTION_DONE);
                etTType.onEditorAction(EditorInfo.IME_ACTION_DONE);
                etTDepth.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Toast.makeText(AddTankActivity.this, "Please enter the Details", Toast.LENGTH_LONG).show();
            }
            else if (TName.isEmpty()) {
                etTName.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Toast.makeText(AddTankActivity.this, "Tank name cannot be empty", Toast.LENGTH_LONG).show();
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
                etTLocation.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Toast.makeText(getApplicationContext(), "Location cannot be empty", Toast.LENGTH_LONG).show();
            }
            else if (TCapacity.isEmpty()) {
                etTCapacity.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Toast.makeText(getApplicationContext(), "Tank Make cannot be empty", Toast.LENGTH_LONG).show();
            }
            else if (TType.isEmpty()) {
                etTType.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Toast.makeText(getApplicationContext(), "Tank Model cannot be empty", Toast.LENGTH_LONG).show();
            }
            else if (TDepth.isEmpty()) {
                etTDepth.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Toast.makeText(getApplicationContext(), "Tank Model cannot be empty", Toast.LENGTH_LONG).show();
            }


            else if (TName.equalsIgnoreCase(tankNameStored) || DevId.equals(devIdStored)) //makes motor name and sim number unique
            {
                etTName.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Toast.makeText(AddTankActivity.this, "Tank already exists.Please Enter new details", Toast.LENGTH_LONG).show();
            }
            else {

                boolean networkAvailable = isNetworkAvailable();
                if(networkAvailable) {
                    loadingDialog.startLoadingDialog();

                    Handler handler = new Handler();
                    handler.postDelayed(() -> {

                        if(!AddTankActivity.this.isFinishing() && loadingDialog !=null){
                            loadingDialog.dismiss();
                            addTankPost(ANDROIDID,Location, DevPin, DevId, TName,TType,TCapacity, TDepth, TOffset, tankDetailsFileName);
                        }
                    },200);

                }
                else {
                    Toast.makeText(AddTankActivity.this, "Please Check your internet connection", Toast.LENGTH_LONG).show();
                }

            }
            setImageButtonEnabled(AddTankActivity.this, true, btAddTank, buttonMutation);

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

    public void addTankPost(final String androidID,String location, String devpin, String devid, String tname,String ttype,String tcapacity,String tdepth, String toffset,final String tankDetailsFilesName) {

        Log.d("URL", "sendPost: " + mAPIService.toString());

        mAPIService.savePost(location,tname,devid,devpin,ttype,tcapacity,tdepth,toffset).enqueue(new Callback<AddTankPost>() {
            @Override
            public void onResponse(Call<AddTankPost> call, Response<AddTankPost> response) {

                System.out.println("value for response:" + response);
                System.out.println("value for call:" + call);

                if (response.isSuccessful()) {

                    Log.i("POST1", "post submitted to API." + response.body().toString());

                    System.out.println("value from response:" + response.body().getReply());
                    SharedPrefManager.getInstance(AddTankActivity.this).storeTankInfo(androidID,location,devpin,devid,tname,ttype,tcapacity,tdepth,toffset,tankDetailsFilesName);
                    Toast.makeText(AddTankActivity.this, response.body().getReply(), Toast.LENGTH_LONG).show();
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

            public void onFailure(Call<AddTankPost> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
                    Toast.makeText(AddTankActivity.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
                }else{
                    Log.i("POST", "post failed."+ t.getMessage());
                }
            }
        });
    }


    private void saveTankDetailsInSharedPref() {
        TankMetaDetails tankMetaDetails = new TankMetaDetails(TName,TType,DevId,DevPin,Location,TCapacity,TDepth,TOffset);
        SharedPrefManager manager = new SharedPrefManager(this);

        manager.addTankMeta(tankMetaDetails);
    }

    public void navigate()
    {
        Intent intent = new Intent(AddTankActivity.this, WaterActivity.class);
        intent.putExtra("androidID","");
        intent.putExtra("app","");
        intent.putExtra("tcapacity",TCapacity);
        intent.putExtra("ploc",Location);
        intent.putExtra("pname",TName);
        intent.putExtra("devid",DevId);
        intent.putExtra("devpin",DevPin);
        intent.putExtra("ttype",TType);
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
            Intent intent = new Intent(AddTankActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}




