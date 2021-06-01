package com.example.minim2sergigarciaqp2021;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minim2sergigarciaqp2021.server.Badge;
import com.example.minim2sergigarciaqp2021.server.Server;
import com.example.minim2sergigarciaqp2021.server.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    TextView idIn;
    Button searchBtn;
    ImageView avatarImage;
    TextView usernameOut;
    TextView nameOut;
    ImageView moonImage;
    ImageView sunImage;
    ImageView starImage;
    ImageView planetImage;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        idIn = findViewById(R.id.idIn);
        searchBtn = findViewById(R.id.searchBtn);
        avatarImage = findViewById(R.id.avatarImage);
        usernameOut = findViewById(R.id.usernameOut);
        nameOut = findViewById(R.id.nameOut);
        moonImage = findViewById(R.id.moonImage);
        sunImage = findViewById(R.id.sunImage);
        starImage = findViewById(R.id.starImage);
        planetImage = findViewById(R.id.planetImage);
        progressBar = findViewById(R.id.profileProgressBar);

        avatarImage.setVisibility(View.INVISIBLE);
        usernameOut.setVisibility(View.INVISIBLE);
        nameOut.setVisibility(View.INVISIBLE);
        moonImage.setVisibility(View.INVISIBLE);
        sunImage.setVisibility(View.INVISIBLE);
        starImage.setVisibility(View.INVISIBLE);
        planetImage.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void searchBtn_Click(View v) {
        progressBar.setVisibility(View.VISIBLE);
        doApiCall();
    }

    private void doApiCall() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        String URL = "http://localhost:8080/";
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Server service = retrofit.create(Server.class);
        Call<User> call = service.getUser(String.valueOf(idIn.getText()));

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    User user = response.body();
                    usernameOut.setText(user.getUsername());
                    nameOut.setText(user.getName() + " " + user.getSurname());
                    Picasso.with(getApplicationContext()).load(user.getAvatar_URL()).into(avatarImage);
                    if(user.getBadges() != null) {
                        Call<List<Badge>> call2 = service.getBadges();

                        call2.enqueue(new Callback<List<Badge>>() {
                            @Override
                            public void onResponse(Call<List<Badge>> call, Response<List<Badge>> response) {
                                if (response.isSuccessful()) {
                                    List<Badge> list = response.body();
                                    String moonURL = null;
                                    String sunURL = null;
                                    String starURL = null;
                                    String planetURL = null;
                                    int i = 0;
                                    while (i < list.size()) {
                                        if (list.get(i).getName() == "moon")
                                            moonURL = list.get(i).getURL();
                                        else if (list.get(i).getName() == "sun")
                                            sunURL = list.get(i).getURL();
                                        else if (list.get(i).getName() == "star")
                                            starURL = list.get(i).getURL();
                                        else if (list.get(i).getName() == "planet")
                                            planetURL = list.get(i).getURL();
                                    }
                                    for (String badge : user.getBadges()) {
                                        if (badge.equals("moon"))
                                            Picasso.with(getApplicationContext()).load(moonURL).into(moonImage);
                                        else if (badge.equals("sun"))
                                            Picasso.with(getApplicationContext()).load(sunURL).into(sunImage);
                                        else if (badge.equals("star"))
                                            Picasso.with(getApplicationContext()).load(starURL).into(starImage);
                                        else if (badge.equals("planet"))
                                            Picasso.with(getApplicationContext()).load(planetURL).into(planetImage);
                                    }
                                    avatarImage.setVisibility(View.VISIBLE);
                                    usernameOut.setVisibility(View.VISIBLE);
                                    nameOut.setVisibility(View.VISIBLE);
                                    moonImage.setVisibility(View.VISIBLE);
                                    sunImage.setVisibility(View.VISIBLE);
                                    starImage.setVisibility(View.VISIBLE);
                                    planetImage.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                } else
                                    Toast.makeText(getApplicationContext(), "Unexpected error.", Toast.LENGTH_LONG);
                            }

                            @Override
                            public void onFailure(Call<List<Badge>> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "EUnexpected error.", Toast.LENGTH_LONG);
                            }
                        });
                    }
                }
                else Toast.makeText(getApplicationContext(), "Error: No such user with that ID.", Toast.LENGTH_LONG);
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: Not connected to server.", Toast.LENGTH_LONG);
            }
        });
    }
}