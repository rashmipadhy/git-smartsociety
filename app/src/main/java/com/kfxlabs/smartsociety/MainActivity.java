package com.kfxlabs.smartsociety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.kfxlabs.smartsociety.Activity.AlertActivity;
import com.kfxlabs.smartsociety.Activity.AlertsActivity;
import com.kfxlabs.smartsociety.Activity.AppInfoActivity;
import com.kfxlabs.smartsociety.Activity.BlockActivity;
import com.kfxlabs.smartsociety.Activity.DgActivity;
import com.kfxlabs.smartsociety.Activity.LoginActivity;
import com.kfxlabs.smartsociety.Activity.ProfileActivity;
import com.kfxlabs.smartsociety.Activity.PumpActivity;
import com.kfxlabs.smartsociety.Activity.SettingActivity;
import com.kfxlabs.smartsociety.Activity.WaterActivity;
//import com.kfxlabs.smartsociety.databinding.DailogLayoutBinding;
import com.kfxlabs.smartsociety.storage.SharedPrefManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    SharedPreferences userCredentials;
    Button dgbtn;
    Button waterbtn;
    Button pumpbtn;
    Button energybtn;
    Button blockbtn;
    Button alertbtn;


    static String phoneNumber, userName, email, orgId;
  //   LinearLayout dg_img;
    private DrawerLayout drawer;
//    Animation frombottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        dgbtn = findViewById(R.id.dg_img);
        waterbtn = findViewById(R.id.water_img);
        pumpbtn = findViewById(R.id.pump_img);
        energybtn = findViewById(R.id.energy_img);
        blockbtn = findViewById(R.id.blk_img);
        alertbtn =findViewById(R.id.alert_img);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        dgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, DgActivity.class);
                startActivity(intent);

            }
        });

        waterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WaterActivity.class);
                startActivity(intent);
            }
        });


        energybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "No Data Available", Toast.LENGTH_SHORT).show();

            }


        });

        pumpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PumpActivity.class);
                startActivity(intent);
            }
        });

        blockbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BlockActivity.class);
                startActivity(intent);
            }
        });

        alertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "No Data Available", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, AlertActivity.class);
                startActivity(intent);
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }
    @Override
    public  void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {

            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.nav_bar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.setting_menu) {
            Intent intent = new Intent(MainActivity.this, AppInfoActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Setting Menu", Toast.LENGTH_SHORT).show();

        } else if(item.getItemId() == R.id.bell_menu) {
            Intent intent = new Intent(MainActivity.this, AlertsActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Alerts", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_profile:
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("user_name",userName);
                intent.putExtra("phone_number",phoneNumber);
                intent.putExtra("email",email);
                intent.putExtra("orgId",orgId);
                startActivity(intent);
                break;
            case R.id.nav_notify:

                Toast.makeText(MainActivity.this, "No Notification Available", Toast.LENGTH_SHORT).show();

                break;
            case R.id.nav_logout:

                SharedPrefManager.getInstance(MainActivity.this).storeLoginInfo("False");
                Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                MainActivity.this.finish();
                break;

            case R.id.action_settings:

                Intent intent3 = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "Setting Menu", Toast.LENGTH_SHORT).show();
                MainActivity.this.finish();
                break;

        }

        return true;

    }
}