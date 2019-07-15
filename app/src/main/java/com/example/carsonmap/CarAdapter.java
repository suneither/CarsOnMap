package com.example.carsonmap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Comparator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class CarAdapter extends ArrayAdapter<Car> {
    private ArrayList<Car> carsListFull;
    public CarAdapter(Context ctx,  ArrayList<Car> carsList) {
        super(ctx, 0, carsList);
        carsListFull = new ArrayList<>(carsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.car_layout,parent,false);
        }

        Car currentItem = getItem(position);

        ImageView carImage = convertView.findViewById(R.id.carImage);
        TextView modelText = convertView.findViewById(R.id.modelText);
        TextView plateNrText =  convertView.findViewById(R.id.plateNrText);
        TextView batteryLeftText =  convertView.findViewById(R.id.batteryLeftText);
        TextView kmToGoText =  convertView.findViewById(R.id.kmToGoText);

        if(currentItem != null){
            loadImageFromUrl(carImage,currentItem);
            modelText.setText("Model: " + currentItem.getCarModel().getTitle());
            plateNrText.setText("Plate nr: " + currentItem.getPlateNumber());
            batteryLeftText.setText("Battery left: " + currentItem.getBatteryPercentage());
            kmToGoText.setText(currentItem.getBatteryEstimatedDistance() + "km. left to go");
        }

        return convertView;
    }

    private void loadImageFromUrl(ImageView imageView, Car currentItem) {
        Picasso.with(getContext()).load(currentItem.getCarModel().getPhotoUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    private Filter editTextFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<Car> suggestions = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                suggestions.addAll(carsListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Car car: carsListFull){
                    if(car.getPlateNumber().toLowerCase().contains(filterPattern)){
                        suggestions.add(car);
                    }
                }
            }
            filterResults.values = suggestions;
            filterResults.count = suggestions.size();

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }

    };

    public Filter getCarsFilterByEditText() {
        return editTextFilter;
    }

    private Filter spinnerFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<Car> suggestions = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                suggestions.addAll(carsListFull);
            }else{
                switch(constraint.toString()) {
                    case "Less than 50%":
                        for(Car car: carsListFull){
                            if(car.getBatteryPercentage() < 50){
                                suggestions.add(car);
                            }
                        }
                        break;
                    case "More than 50%":
                        for(Car car: carsListFull){
                            if(car.getBatteryPercentage() > 50){
                                suggestions.add(car);
                            }
                        }
                        break;
                    case "More than 80%":
                        for(Car car: carsListFull){
                            if(car.getBatteryPercentage() > 80){
                                suggestions.add(car);
                            }
                        }
                        break;
                    case "Full":
                        for(Car car: carsListFull){
                            if(car.getBatteryPercentage() == 100){
                                suggestions.add(car);
                            }
                        }
                        break;
                    default:
                        suggestions.addAll(carsListFull);
                }
            }
            filterResults.values = suggestions;
            filterResults.count = suggestions.size();

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    public Filter getCarsFilterBySpinner() {
        return spinnerFilter;
    }
}

