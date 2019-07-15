package com.example.carsonmap;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL = "https://development.espark.lt/api/mobile/public/";

    @GET("availablecars")
    Call<ArrayList<Car>> getCarsCall();
}