package com.sytays.timetable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int count = 0;
    private long startMillis = 0;

    Toolbar toolbar;
    Spinner spinner;

    Fragment fr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spinner = (Spinner) findViewById(R.id.spinner);

        toolbar.setTitle(getResources().getString(R.string.app_name));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R
                .layout.custom_spinner_item, getResources().getStringArray(R.array.days));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        SimpleDateFormat day_format = new SimpleDateFormat("EEEE", Locale.US);
        String day = day_format.format(new Date());

        Calendar rightNow = Calendar.getInstance();
        int curHour = rightNow.get(Calendar.HOUR_OF_DAY);

        int pos;
        switch (day) {
            case "Monday":
                pos = 0;
                break;
            case "Tuesday":
                pos = 1;
                break;
            case "Wednesday":
                pos = 2;
                break;
            case "Thursday":
                pos = 3;
                break;
            case "Friday":
                pos = 4;
                break;
            case "Saturday":
                pos = 5;
                break;
            default:
                pos = 0;
        }

        if((curHour > 15) && !(day.equals("Sunday"))) {
            pos += 1;
            pos %= 6;
        }

        spinner.setSelection(pos);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(spinner.getSelectedItem().toString()) {
                    case "Monday":
                        fr = new Monday_frag();
                        break;
                    case "Tuesday":
                        fr = new Tuesday_frag();
                        break;
                    case "Wednesday":
                        fr = new Wednesday_frag();
                        break;
                    case "Thursday":
                        fr = new Thursday_frag();
                        break;
                    case "Friday":
                        fr = new Friday_frag();
                        break;
                    case "Saturday":
                        fr = new Saturday_frag();
                        break;
                    default:
                        fr = new Monday_frag();
                }

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction =  fm.beginTransaction();
                fragmentTransaction.replace(R.id.frag_fl, fr);
                fragmentTransaction.commit();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int eventaction = event.getAction();
        if (eventaction == MotionEvent.ACTION_UP) {

            long time= System.currentTimeMillis();

            if (startMillis==0 || (time-startMillis> 3000) ) {
                startMillis=time;
                count=1;
            }
            else{
                count++;
            }

            if (count==5) {
                Toast.makeText(MainActivity.this, "App by STS", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return false;
    }
}
