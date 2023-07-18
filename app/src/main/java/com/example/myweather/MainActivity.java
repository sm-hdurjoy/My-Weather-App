package com.example.myweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button currentLocation, searchCity, todayWeather;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentLocation = (Button) findViewById(R.id.idBtnCurrentLocation);
        searchCity = (Button) findViewById(R.id.idBtnSearchCity);
        todayWeather = (Button) findViewById(R.id.idBtnTodayWeather);

        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentL = (String) currentLocation.getText().toString();
                Intent currentLIntent  = new Intent(getApplicationContext(), CurrentLocation.class);
                currentLIntent.putExtra("current_key", currentL);
                startActivity(currentLIntent);
            }
        });

        searchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchC = (String) searchCity.getText().toString();
                Intent searchCIntent = new Intent(getApplicationContext(), SearchCity.class);
                searchCIntent.putExtra("search_city", searchC);
                startActivity(searchCIntent);
            }
        });

        todayWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todayW = (String) searchCity.getText().toString();
                Intent todayWIntent = new Intent(getApplicationContext(), TodayWeather.class);
                todayWIntent.putExtra("today_weather", todayW);
                startActivity(todayWIntent);
            }
        });
    }
}