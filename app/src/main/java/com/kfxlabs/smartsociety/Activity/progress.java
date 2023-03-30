package com.kfxlabs.smartsociety.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.kfxlabs.smartsociety.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
public class progress extends AppCompatActivity {

    Button buttonStart;
    ProgressBar vProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        buttonStart = (Button)findViewById(R.id.start);
        vProgressBar = (ProgressBar)findViewById(R.id.vprogressbar);
        new asyncTaskUpdateProgress().execute();

    }

    public class asyncTaskUpdateProgress extends AsyncTask<Void, Integer, Void> {

        int progress;

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
           // buttonStart.setClickable(true);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            progress = 0;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            vProgressBar.setProgress(values[20]);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            while(progress<100){
                progress++;
                publishProgress(progress);
                SystemClock.sleep(100);
            }
            return null;
        }

    }

}