package com.example.minim2sergigarciaqp2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.minim2sergigarciaqp2021.server.Badge;
import com.example.minim2sergigarciaqp2021.server.Server;
import com.example.minim2sergigarciaqp2021.server.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button profileBtn;
    Button badgesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profileBtn = findViewById(R.id.profileBtn);
        badgesBtn = findViewById(R.id.badgesBtn);
    }

    public void badgesBtn_Click(View v) {
        Intent intent = new Intent(MainActivity.this, BadgesActivity.class);
        startActivity(intent);
    }

    public void profileBtn_Click(View v) {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}