package com.example.leoruan.leoruan_a2;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    RecyclerView recyclerView;
    List<Sensor> sensor_list;
    Sensor lSensor;
    Sensor accSensor;
    MediaPlayer mp = null;

    SensorManager mySensorManager;

    Boolean played = false;
    Boolean vibrated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.sensors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor_list = mySensorManager.getSensorList(Sensor.TYPE_ALL);

        lSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        recyclerView.setAdapter(new MyAdapter(this, sensor_list));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();
        if(type == Sensor.TYPE_LIGHT) {
            float sensor_val = event.values[0];
            if(sensor_val == 0 && !played) {

                try {
                    mp = MediaPlayer.create(this, R.raw.beep);
                    played = true;
                    mp.start();
                } catch (Exception e){
                    Toast.makeText(this, "There is an error with playing sound", Toast.LENGTH_SHORT).show();
                }

            } else if (sensor_val > 0) {

                played = false;
            }
        }

        if(type == Sensor.TYPE_ACCELEROMETER){
            double x = event.values.clone()[0];
            double y = event.values.clone()[1];
            double z = event.values.clone()[2];
            Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
            float normalizer =(float) Math.sqrt(x * x + y * y + z * z);

            x = (x / normalizer);
            y = (y / normalizer);
            z = (z / normalizer);
            int device_incline = (int) Math.round(Math.toDegrees(Math.acos(z)));

            if (device_incline < 10 || device_incline > 175)
            {
                if(!vibrated) {
                    Toast.makeText(this, "device flat - beep", Toast.LENGTH_LONG).show();
                    try {
                        v.vibrate(5000);
                        vibrated = true;
                    } catch (Exception e) {
                        Toast.makeText(this, "There is no vibration option for your phone.", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                v.cancel();
                vibrated = false;
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
        mySensorManager.registerListener((SensorEventListener) this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mySensorManager.unregisterListener(this);
    }

    public void status_start(View view) {

    }

}