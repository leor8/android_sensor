package com.example.leoruan.leoruan_a2;

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
            float norm_Of_g =(float) Math.sqrt(x * x + y * y + z * z);

            // Normalize the accelerometer vector
            x = (x / norm_Of_g);
            y = (y / norm_Of_g);
            z = (z / norm_Of_g);
            int inclination = (int) Math.round(Math.toDegrees(Math.acos(z)));
            Log.d("DEBUG555", ""+inclination);

            if (inclination < 10 || inclination > 175)
            {
                if(!vibrated) {
                    Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
                    v.vibrate(500);
                    vibrated = true;
                }
            } else {
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

}