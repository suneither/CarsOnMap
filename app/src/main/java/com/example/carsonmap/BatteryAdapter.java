package com.example.carsonmap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BatteryAdapter extends ArrayAdapter<Battery> {
    public BatteryAdapter(@NonNull Context context, ArrayList<Battery> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.battery_layout,parent,false);
        }

        TextView textViewBattery = convertView.findViewById(R.id.textViewBattery);
        ImageView imageViewBattery = convertView.findViewById(R.id.imageViewBattery);

        Battery currentItem = getItem(position);

        if(currentItem != null){
            textViewBattery.setText(currentItem.getTitle());
            imageViewBattery.setImageResource(currentItem.getImage());
        }

        return convertView;
    }
}
