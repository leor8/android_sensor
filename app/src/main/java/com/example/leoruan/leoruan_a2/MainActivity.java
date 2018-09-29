package com.example.leoruan.leoruan_a2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    RecyclerView recyclerView;
    List<Sensor> sensor_list;
    Sensor lSensor;

    SensorManager mySensorManager;

    Boolean played = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.sensors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor_list = mySensorManager.getSensorList(Sensor.TYPE_ALL);

        lSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        recyclerView.setAdapter(new MyAdapter(this, sensor_list));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();
        if(type == Sensor.TYPE_LIGHT) {
            float sensor_val = event.values[0];
            if(sensor_val == 0 && !played) {
                ToneGenerator beep = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
//                beep.startTone(ToneGenerator.TONE_CDMA_PIP,150);
//                beep.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 150);

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor s, int i) {
    }


    @Override
    protected void onResume() {
        super.onResume();
        mySensorManager.registerListener((SensorEventListener) this, lSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mySensorManager.unregisterListener(this);
    }

}