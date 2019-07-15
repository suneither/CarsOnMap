package com.example.carsonmap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListController extends Fragment {
    private Spinner batteryListSpinner;
    private EditText plateNrEditText;
    private ListView carsListView;
    private CarAdapter carAdapter;
    FusedLocationProviderClient fusedLocationProviderClient;
    ArrayList<Car> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, savedInstanceState);
    }

    private View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View listView = inflater.inflate(R.layout.fragment_list, container, false);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        batteryListSpinner = listView.findViewById(R.id.batteryListSpinner);
        plateNrEditText = listView.findViewById(R.id.plateNrEditText);
        carsListView = listView.findViewById(R.id.carsListView);

        plateNrEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                carAdapter.getCarsFilterByEditText().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // Populates carsListView with car_layout fragments
        populateCarsListView();

        // populate spinner with fragments of battery
        populateBatteryDropDownList();

        return listView;
    }

    private void populateCarsListView() {
        Call carsCall = new ApiCall().getCarsCall();
        carsCall.enqueue(new Callback<ArrayList<Car>>() {
            @Override
            public void onResponse(Call<ArrayList<Car>> call, Response<ArrayList<Car>> response) {
                arrayList = new ArrayList<>(response.body());

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) { }
                fusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(final Location location) {
                                if (location != null) {
                                    Collections.sort(arrayList, new Comparator<Car>() {
                                        @Override
                                        public int compare( Car o1,  Car o2) {

                                            Location car1 = new Location("");
                                            car1.setLatitude(o1.getCarLocation().getLatitude());
                                            car1.setLongitude(o1.getCarLocation().getLongtitude());

                                            Location car2 = new Location("");
                                            car2.setLatitude(o2.getCarLocation().getLatitude());
                                            car2.setLongitude(o2.getCarLocation().getLongtitude());

                                            o1.setDistanceFromYou(location.distanceTo(car1));
                                            o2.setDistanceFromYou(location.distanceTo(car2));

                                            if(o1.getDistanceFromYou() < o2.getDistanceFromYou()){
                                                return -1;

                                            }
                                            else if(o1.getDistanceFromYou() > o2.getDistanceFromYou()){
                                                return 1;
                                            }
                                            return 0;
                                        }
                                    });
                                }
                            }
                        });

                carAdapter = new CarAdapter(getContext(), arrayList);
                carsListView.setAdapter(carAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Car>> call, Throwable t) {
                Log.d("PopulateListWithCars: ", t.getMessage());
            }
        });
    }

    private void populateBatteryDropDownList(){
        BatteryAdapter batteryAdapter = new BatteryAdapter(getContext(),getBatteryArrayList());

        batteryListSpinner.setAdapter(batteryAdapter);
        batteryListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Populates carsListView with car_layout fragments by selected item
                if(carAdapter != null){
                    Battery battery = (Battery) parent.getItemAtPosition(position);
                    carAdapter.getCarsFilterBySpinner().filter(battery.getTitle());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private ArrayList<Battery> getBatteryArrayList(){
        ArrayList<Battery> batteryArrayList =  new ArrayList<>();
        batteryArrayList.add(new Battery("All",R.mipmap.all,500));
        batteryArrayList.add(new Battery("Less than 50%",R.mipmap.battery_30_white,40));
        batteryArrayList.add(new Battery("More than 50%",R.mipmap.battery_60_white, 60));
        batteryArrayList.add(new Battery("More than 80%",R.mipmap.battery_80_white, 80));
        batteryArrayList.add(new Battery("Full",R.mipmap.battery_full_white, 100));
        return batteryArrayList;
    }
}
