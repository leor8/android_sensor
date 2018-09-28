package com.example.leoruan.leoruan_a2;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Sensor> sensor_list;

    public MyAdapter(Context context, List<Sensor> sensor_list) {
        this.context = context;
        this.sensor_list = sensor_list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.view_item, parent, false);
        Item item = new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        ((Item)holder).item_text.setText(sensor_list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return sensor_list.size();
    }

    public class Item extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView item_text;
        Context context;
        public Item(View view){
            super(view);
            item_text = view.findViewById(R.id.item);
            view.setOnClickListener(this);
            context = view.getContext();
        }

        @Override
        public void onClick(View v) {
            String curr_sensor = item_text.getText().toString();
            Intent i = new Intent(context, Sensor_Detail.class);
            i.putExtra("SENSOR_NAME", curr_sensor);
            context.startActivity(i);
        }
    }

}