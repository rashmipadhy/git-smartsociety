package com.kfxlabs.smartsociety.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.kfxlabs.smartsociety.R;
import com.kfxlabs.smartsociety.api.Api;

public class SettingActivity extends AppCompatActivity {

    EditText etUrl;
    EditText etPortNo;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        etUrl = findViewById(R.id.etUrl);
        etPortNo = findViewById(R.id.etPortNo);
        toolbar = findViewById(R.id.toolbar);

        etUrl.setText(Api.BASE_URL);
        etPortNo.setText(Api.PORT_NO);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle("SETTINGS");
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selected_item_id = item.getItemId();
        if(selected_item_id == R.id.action_settings_save)
        {
            String url = etUrl.getText().toString().trim();
            String portNo = etPortNo.getText().toString().trim();
            Api.FULL_URL = String.format("%s%s", url, portNo);
            Toast.makeText(SettingActivity.this, "Successfully saved", Toast.LENGTH_LONG).show();
        }
        if (selected_item_id == android.R.id.home)
        {
//            Intent intent = new Intent(SettingsActivity.this, com.example.motorApplication.motor.MotorScreen.class);
//            startActivity(intent);
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);


    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(getCurrentFocus()!=null){
            InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
