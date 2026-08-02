package com.example.clientcleaningcenter.repository;

import android.util.Log;

import com.example.clientcleaningcenter.model.BpInstance;
import com.example.clientcleaningcenter.network.ApiService;
import com.example.clientcleaningcenter.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InstanceRepository {

    private final ApiService apiService;

    private static String authToken;

    public InstanceRepository() {
        apiService = RetrofitClient.getApiService();
    }

    public static void setAuthToken(String token) {
        authToken = token;
    }

    public static void clearAuthToken() {
        authToken = null;
    }

    public interface InstanceCallback {
        void onSuccess(List<BpInstance> instances);
        void onError(String message);
    }

    public void loadInstances(InstanceCallback callback) {

        if (authToken == null || authToken.trim().isEmpty()) {
            Log.d("JWT_TEST", "Kein Token vorhanden.");
            callback.onError("JWT Token fehlt. Bitte erneut anmelden.");
            return;
        }

        String bearerToken = "Bearer " + authToken;

        Log.d("JWT_TEST", "Instanzen werden mit Token geladen.");
        Log.d("JWT_TEST", "Authorization Header: " + bearerToken);

        apiService.getAllInstances(bearerToken).enqueue(new Callback<List<BpInstance>>() {
            @Override
            public void onResponse(Call<List<BpInstance>> call, Response<List<BpInstance>> response) {

                Log.d("JWT_TEST", "Instances Response Code: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    Log.d("JWT_TEST", "Instanzen erfolgreich geladen. Anzahl: " + response.body().size());
                    callback.onSuccess(response.body());
                } else if (response.code() == 401) {
                    Log.d("JWT_TEST", "Token wurde vom Backend abgelehnt.");
                    callback.onError("Token ist ungültig oder abgelaufen. Bitte erneut anmelden.");
                } else {
                    Log.d("JWT_TEST", "Fehler beim Laden der Instanzen.");
                    callback.onError("Daten konnten nicht geladen werden. Fehlercode: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<BpInstance>> call, Throwable throwable) {
                Log.d("JWT_TEST", "REST API Verbindung fehlgeschlagen: " + throwable.getMessage());
                callback.onError("Verbindung zur REST API fehlgeschlagen.");
            }
        });
    }
}