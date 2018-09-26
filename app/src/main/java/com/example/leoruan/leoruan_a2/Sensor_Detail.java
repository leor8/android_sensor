package com.example.leoruan.leoruan_a2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Sensor_Detail extends Activity implements View.OnClickListener{
    TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_activity);

        test = findViewById(R.id.textView);
        test.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
