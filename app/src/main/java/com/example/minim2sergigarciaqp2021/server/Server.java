package com.example.minim2sergigarciaqp2021.server;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Server {
    String URL = "http://localhost:8080/";

    @GET("/img/user/{id}")
    Call<User> getUser(@Path("id") String id);

    @GET("img/badges")
    Call<List<Badge>> getBadges();
}
