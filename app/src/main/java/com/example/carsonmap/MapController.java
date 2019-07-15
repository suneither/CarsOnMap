package com.example.carsonmap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class MapController extends Fragment implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private View mView;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mMapView = view.findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Set locations for cars
        PopulateMapWithCars();
        // Set location for user
        GetCurrentLocation();
    }

    private void PopulateMapWithCars(){
        Call carsCall = new ApiCall().getCarsCall();
        carsCall.enqueue(new Callback<ArrayList<Car>>() {
            @Override
            public void onResponse(Call<ArrayList<Car>> call, Response<ArrayList<Car>> response) {
                ArrayList<Car> carList = response.body();
                for (Car car: carList) {
                    AddMarker(new LatLng(car.getCarLocation().getLatitude(), car.getCarLocation().getLongtitude()),car.getCarModel().getTitle());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Car>> call, Throwable t) {
                Log.d("PopulateMapWithCars: ", t.getMessage());
            }
        });
    }

    public void AddMarker(LatLng latLng, String title) {
        mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(title));
    }

    private void GetCurrentLocation(){
        if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            MoveToCurrentLocation(new LatLng(location.getLatitude(),location.getLongitude()));
                        }
                    }
                });
    }

    private void MoveToCurrentLocation(LatLng currentLocation){
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14.0f));
    }


}
