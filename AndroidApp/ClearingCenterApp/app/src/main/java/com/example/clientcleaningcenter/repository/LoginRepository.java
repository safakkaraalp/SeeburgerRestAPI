package com.example.clientcleaningcenter.repository;

import android.util.Log;

import com.example.clientcleaningcenter.model.LoginRequest;
import com.example.clientcleaningcenter.model.LoginResponse;
import com.example.clientcleaningcenter.network.ApiService;
import com.example.clientcleaningcenter.network.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    private final ApiService apiService;

    public LoginRepository() {
        apiService = RetrofitClient.getApiService();
    }

    public interface LoginCallback {
        void onSuccess(String token);
        void onError(String message);
    }

    public void login(String username, String password, LoginCallback callback) {
        LoginRequest loginRequest = new LoginRequest(username, password);

        Log.d("JWT_TEST", "Login request wird gesendet.");
        Log.d("JWT_TEST", "Username: " + username);

        apiService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                Log.d("JWT_TEST", "Login Response Code: " + response.code());

                if (response.isSuccessful() && response.body() != null) {

                    String token = response.body().getToken();

                    Log.d("JWT_TEST", "Login erfolgreich.");
                    Log.d("JWT_TEST", "Token erhalten: " + token);

                    callback.onSuccess(token);

                } else {

                    Log.d("JWT_TEST", "Login fehlgeschlagen.");
                    Log.d("JWT_TEST", "HTTP Status Code: " + response.code());

                    try {
                        if (response.errorBody() != null) {
                            String errorText = response.errorBody().string();
                            Log.d("JWT_TEST", "Backend Fehlermeldung: " + errorText);
                        }
                    } catch (IOException e) {
                        Log.d("JWT_TEST", "Fehlerbody konnte nicht gelesen werden.");
                    }

                    if (response.code() == 401) {
                        callback.onError("Benutzername oder Passwort falsch.");
                    } else {
                        callback.onError("Login fehlgeschlagen. Fehlercode: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable throwable) {

                Log.d("JWT_TEST", "Login Verbindung fehlgeschlagen.");
                Log.d("JWT_TEST", "Fehler: " + throwable.getMessage());

                callback.onError("Verbindung zur REST API fehlgeschlagen.");
            }
        });
    }
}