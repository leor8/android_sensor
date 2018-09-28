package com.example.leoruan.leoruan_a2;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Sensor_Detail extends Activity implements View.OnClickListener, SensorEventListener{
    TextView name;
    TextView curr_val;
    TextView max_range;
    Intent received;
    String sensor_name;
    float sensor_val;

    SensorManager sensor_info_getter;
    Sensor curr_sensor;
    List<Sensor> all_sensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_activity);

        name = findViewById(R.id.textView);
        name.setOnClickListener(this);

        curr_val = findViewById(R.id.textView4);
        max_range = findViewById(R.id.textView6);


        received = getIntent();

        sensor_name = received.getStringExtra("SENSOR_NAME");

        sensor_info_getter = (SensorManager) getSystemService(SENSOR_SERVICE);
        all_sensors = sensor_info_getter.getSensorList(Sensor.TYPE_ALL);


        for(int i = 0; i < all_sensors.size(); i++){
            if(all_sensors.get(i).getName().equals(sensor_name)){
//                Log.d("DEBUG555", "" + all_sensors.get(i).getName());
                curr_sensor = all_sensors.get(i);
            }
        }

        name.setText(curr_sensor.getName());
        max_range.setText("" + curr_sensor.getMaximumRange());



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        sensor_val = event.values[0];
        curr_val.setText("" + sensor_val);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sensor_info_getter.registerListener((SensorEventListener) this, curr_sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sensor_info_getter.unregisterListener(this);
    }

    @Override
    public void onClick(View v) {
    }
}
