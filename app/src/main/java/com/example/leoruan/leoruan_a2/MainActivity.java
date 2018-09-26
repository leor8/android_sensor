package com.example.leoruan.leoruan_a2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    RecyclerView recyclerView;
    List<Sensor> sensor_list;

    SensorManager mySensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.sensors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor_list = mySensorManager.getSensorList(Sensor.TYPE_ALL);


        recyclerView.setAdapter(new MyAdapter(this, sensor_list));
    }

    @Override
    public void onSensorChanged(SensorEvent evt) {
        int sensor_type = evt.sensor.getType();
    }

    @Override
    public void onAccuracyChanged(Sensor s, int i) {
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}