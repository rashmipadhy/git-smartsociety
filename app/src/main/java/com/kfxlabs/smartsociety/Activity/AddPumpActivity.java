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
import com.kfxlabs.smartsociety.model.PumpMetaDetails;
import com.kfxlabs.smartsociety.module.AddDgPost;
import com.kfxlabs.smartsociety.module.AddPumpPost;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPumpActivity extends AppCompatActivity {

    Button btAddPump;
    EditText etPumpName,etDevId,etDevPin,etLocation,etPMake,etPModel;
    String phoneNumber;
    ProgressDialog progressDialog;
    LoadingDialog loadingDialog;
    Handler handle;
    int buttonMutation;

    com.kfxlabs.smartsociety.api.AddPump mAPIService = Api.getAddPumpAPIService(AddPumpActivity.this);
    public static String ANDROIDID;
    public static String getANDROIDID() {

        return ANDROIDID;
    }

    public static void setANDROIDID(String ANDROIDID) {


        AddDgActivity.ANDROIDID = ANDROIDID;
    }
    public static String PName;
    public static String DevId;
    public static String Location;
    public static String PModel;
    public static String PMake;
    public static String DevPin;
    public static String PRating;
    public static String PRunmax;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pump);

        setANDROIDID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btAddPump = findViewById(R.id.dgbtAddMotor);
        etPumpName = findViewById(R.id.pumpName);
        etDevId = findViewById(R.id.p_unumber);
        etDevPin = findViewById(R.id.p_pin);
        etLocation = findViewById(R.id.p_location);
        etPModel = findViewById(R.id.p_model);
        etPMake = findViewById(R.id.dget_make);
        progressDialog =  new ProgressDialog(AddPumpActivity.this);
        buttonMutation = R.drawable.background_01;
        loadingDialog = new LoadingDialog(AddPumpActivity.this);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("Add Pump");
        }
        Log.d("tag","on create : mAPIService" + mAPIService);

        // Log.d("tag","on create : mAPIService" + IApiService);
        final String pumpDetailsFileName = getIntent().getStringExtra("DG_DETAILS");
        final String credentialsFileName = getIntent().getStringExtra("USER_CREDENTIALS");

        SharedPreferences userCredentials = getSharedPreferences(credentialsFileName, MODE_PRIVATE);
        phoneNumber =  userCredentials.getString("phone_number", "");


        etPumpName.setOnFocusChangeListener((v, hasFocus) -> {
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
        etPModel.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });
        etPMake.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("ADD PUMP");
        }

        btAddPump.setOnClickListener(v -> {



            PName = etPumpName.getText().toString().trim();
            DevId = etDevId.getText().toString().trim();
            DevPin = etDevPin.getText().toString().trim();
            Location = etLocation.getText().toString().trim();
            PMake = etPMake.getText().toString().trim();
            PModel = etPModel.getText().toString().trim();


            SharedPreferences prefs = getSharedPreferences(pumpDetailsFileName, MODE_PRIVATE);


            String devIdStored = null;
            String dgNameStored = null;


            if (PName.isEmpty() && DevId.isEmpty() && DevPin.isEmpty() && Location.isEmpty()&& PMake.isEmpty() && PModel.isEmpty()) {
                etPumpName.onEditorAction(EditorInfo.IME_ACTION_DONE);
                etDevId.onEditorAction(EditorInfo.IME_ACTION_DONE);
                etLocation.onEditorAction(EditorInfo.IME_ACTION_DONE);
                etDevPin.onEditorAction(EditorInfo.IME_ACTION_DONE);
                etPMake.onEditorAction(EditorInfo.IME_ACTION_DONE);
                etPModel.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Toast.makeText(AddPumpActivity.this, "Please enter the Details", Toast.LENGTH_LONG).show();
            }
            else if (PName.isEmpty()) {
                etPumpName.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Toast.makeText(AddPumpActivity.this, "Motor name cannot be empty", Toast.LENGTH_LONG).show();
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
            else if (PMake.isEmpty()) {
                etPMake.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Toast.makeText(getApplicationContext(), "Dg Make cannot be empty", Toast.LENGTH_LONG).show();
            }
            else if (PModel.isEmpty()) {
                etPModel.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Toast.makeText(getApplicationContext(), "Dg Model cannot be empty", Toast.LENGTH_LONG).show();
            }


            else if (PName.equalsIgnoreCase(dgNameStored) || DevId.equals(devIdStored)) //makes motor name and sim number unique
            {
                etPumpName.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Toast.makeText(AddPumpActivity.this, "Dg already exists.Please Enter new details", Toast.LENGTH_LONG).show();
            }
            else {

                boolean networkAvailable = isNetworkAvailable();
                if(networkAvailable) {
                    loadingDialog.startLoadingDialog();

                    Handler handler = new Handler();
                    handler.postDelayed(() -> {

                        if(!AddPumpActivity.this.isFinishing() && loadingDialog !=null){
                            loadingDialog.dismiss();
                            addPumpPost(ANDROIDID,Location, DevPin, DevId, PName,PMake,PModel, PRating, PRunmax, pumpDetailsFileName);
                        }
                    },200);

                }
                else {
                    Toast.makeText(AddPumpActivity.this, "Please Check your internet connection", Toast.LENGTH_LONG).show();
                }

            }
            setImageButtonEnabled(AddPumpActivity.this, true, btAddPump, buttonMutation);

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

    public void addPumpPost(final String androidID,String location, String devpin, String devid, String pname,String pmake,String pmodel,String prating,String prunmax, final String pumpDetailsFilesName) {

        Log.d("URL", "sendPost: " + mAPIService.toString());

        mAPIService.savePost(location,pname,devid,devpin,pmake,pmodel,prating,prunmax).enqueue(new Callback<AddPumpPost>() {
            @Override
            public void onResponse(Call<AddPumpPost> call, Response<AddPumpPost> response) {

                System.out.println("value for response:" + response);
                System.out.println("value for call:" + call);

                if (response.isSuccessful()) {

                    Log.i("POST1", "post submitted to API." + response.body().toString());

                    System.out.println("value from response:" + response.body().getReply());
                    SharedPrefManager.getInstance(AddPumpActivity.this).storePumpInfo(androidID,location,devpin,devid,pname,pmake,pmodel,pumpDetailsFilesName);
                    Toast.makeText(AddPumpActivity.this, response.body().getReply(), Toast.LENGTH_LONG).show();
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

            public void onFailure(Call<AddPumpPost> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.e("POST", "Unable to submit post to API.Connection time out");
                    Toast.makeText(AddPumpActivity.this, "Unable to submit post to API.Connection timed out", Toast.LENGTH_LONG).show();
                }else{
                    Log.i("POST", "post failed."+ t.getMessage());
                }
            }
        });
    }


    private void savePumpDetailsInSharedPref() {
        PumpMetaDetails pumpMetaDetails = new PumpMetaDetails(PName, PMake,DevId,DevPin,Location,PRating);
        SharedPrefManager manager = new SharedPrefManager(this);

        manager.addPumpMeta(pumpMetaDetails);
    }

    public void navigate()
    {
        Intent intent = new Intent(AddPumpActivity.this, PumpActivity.class);
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
            Intent intent = new Intent(AddPumpActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}




