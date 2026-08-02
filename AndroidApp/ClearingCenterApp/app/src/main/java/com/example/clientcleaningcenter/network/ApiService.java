package com.example.clientcleaningcenter.network;

import com.example.clientcleaningcenter.model.BpInstance;
import com.example.clientcleaningcenter.model.LoginRequest;
import com.example.clientcleaningcenter.model.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/v1/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("api/v1/instances")
    Call<List<BpInstance>> getAllInstances(@Header("Authorization") String token);
}