package com.example.minim2sergigarciaqp2021;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minim2sergigarciaqp2021.server.Badge;
import com.example.minim2sergigarciaqp2021.server.Server;
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

public class BadgesActivity extends AppCompatActivity {

    ImageView moonImage;
    ImageView sunImage;
    ImageView starImage;
    ImageView planetImage;
    TextView moonText;
    TextView sunText;
    TextView starText;
    TextView planetText;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badges);
        moonImage = findViewById(R.id.moonBadgeImage);
        sunImage = findViewById(R.id.sunBadgeImage);
        starImage = findViewById(R.id.starBadgeImage);
        planetImage = findViewById(R.id.planetBadgeImage);
        moonText = findViewById(R.id.moonText);
        sunText = findViewById(R.id.sunText);
        starText = findViewById(R.id.starText);
        planetText = findViewById(R.id.planetText);
        progressBar = findViewById(R.id.badgesProgressBar);

        progressBar.setVisibility(View.INVISIBLE);

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
        Call<List<Badge>> call = service.getBadges();

        call.enqueue(new Callback<List<Badge>>() {
            @Override
            public void onResponse(Call<List<Badge>> call, Response<List<Badge>> response) {
                if (response.isSuccessful()) {
                    List<Badge> list = response.body();
                    String moonURL;
                    String sunURL;
                    String starURL;
                    String planetURL;
                    int i = 0;
                    while (i < list.size()) {
                        if (list.get(i).getName() == "moon") {
                            moonURL = list.get(i).getURL();
                            Picasso.with(getApplicationContext()).load(moonURL).into(moonImage);
                            moonText.setText(list.get(i).getName());
                        }
                        else if (list.get(i).getName() == "sun") {
                            sunURL = list.get(i).getURL();
                            Picasso.with(getApplicationContext()).load(sunURL).into(sunImage);
                            sunText.setText(list.get(i).getName());
                        }
                        else if (list.get(i).getName() == "star") {
                            starURL = list.get(i).getURL();
                            Picasso.with(getApplicationContext()).load(starURL).into(starImage);
                            starText.setText(list.get(i).getName());
                        }
                        else if (list.get(i).getName() == "planet") {
                            planetURL = list.get(i).getURL();
                            Picasso.with(getApplicationContext()).load(planetURL).into(planetImage);
                            planetText.setText(list.get(i).getName());
                        }
                    }
                    moonImage.setVisibility(View.VISIBLE);
                    sunImage.setVisibility(View.VISIBLE);
                    starImage.setVisibility(View.VISIBLE);
                    planetImage.setVisibility(View.VISIBLE);
                    moonText.setVisibility(View.VISIBLE);
                    sunText.setVisibility(View.VISIBLE);
                    starText.setVisibility(View.VISIBLE);
                    planetText.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);

                } else
                    Toast.makeText(getApplicationContext(), "Unexpected error.", Toast.LENGTH_LONG);
            }
            @Override
            public void onFailure(Call<List<Badge>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: Disconnected from server.", Toast.LENGTH_LONG);
            }
        });
    }
}