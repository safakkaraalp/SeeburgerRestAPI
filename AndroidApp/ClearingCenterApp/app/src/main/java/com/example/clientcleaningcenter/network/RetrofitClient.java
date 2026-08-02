package com.example.clientcleaningcenter.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

    private static final String BASE_URL = "http://10.0.2.2:8080/";

    public static ApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)


                    .addConverterFactory(GsonConverterFactory.create())    //GsonConverterFactory wird benutzt, um JSON-Daten automatisch in Java-Objekte umzuwandeln.
                    //In meinem Projekt kommen die Daten vom Backend im JSON-FormatRetrofit
                    // nimmt diese JSON-Antwort und Gson wandelt sie in BpInstance-Objekte um.
                    .build();
        }

        return retrofit.create(ApiService.class);
    }
}