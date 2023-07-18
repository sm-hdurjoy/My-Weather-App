package com.example.myweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CurrentLocation extends AppCompatActivity {

    private TextView cityNameTVCurrent, temperatureTVcurent, conditionTVcurrent, windSpeedTV;
    private ImageView iconIVcurrent;


    private LocationManager locationManager;
    private int PERMISSION_CODE = 1;
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);

        cityNameTVCurrent = (TextView) findViewById(R.id.idTVCityNameCurrent);
        temperatureTVcurent = (TextView) findViewById(R.id.idTVTemperatureCurrent);
        conditionTVcurrent = (TextView) findViewById(R.id.idTVConditionCurrent);
        iconIVcurrent = (ImageView) findViewById(R.id.idIVIconCurrent);
        windSpeedTV = (TextView) findViewById(R.id.idTVWindSpeedCurrent);


        //cityNameTVCurrent.setText("please");


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CurrentLocation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE);
            return;
        }
        //Toast.makeText(this, "baal", Toast.LENGTH_SHORT).show();
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        //Toast.makeText(this, String.valueOf(latitude), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, String.valueOf(longitude), Toast.LENGTH_SHORT).show();
        String url = "https://api.weatherapi.com/v1/forecast.json?key=bf5f9e0f68274a1b9e2200314222208&q=Dhaka&days=1&aqi=no&alerts=no";

        //String url = "https://api.weatherapi.com/v1/forecast.json?key=bf5f9e0f68274a1b9e2200314222208&q="+latitude+","+longitude+"&days=1&aqi=no&alerts=no";
        //Toast.makeText(this, url, Toast.LENGTH_SHORT).show();

        Fetch fetch = new Fetch(getApplicationContext(), url, new ResponseListener() {
            @Override
            public void onResponse(String data) {
                try {
                    JSONObject response = new JSONObject(data);

                    String cityName = response.getJSONObject("location").getString("name");
                    //Toast.makeText(CurrentLocation.this, cityName, Toast.LENGTH_SHORT).show();
                    cityNameTVCurrent.setText(cityName);

                    String temperature = response.getJSONObject("current").getString("temp_c");
                    String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String conditionIcon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    String windSpeedCurrent = response.getJSONObject("current").getString("wind_kph");

                    temperatureTVcurent.setText(temperature+"°c");
                    conditionTVcurrent.setText(condition);
                    windSpeedTV.setText(windSpeedCurrent+" km/h");

                } catch (Exception e) {}
            }
        });
        fetch.getData();
        //Toast.makeText(this, String.valueOf(longitude), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, cityName, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "This shows your current location", Toast.LENGTH_SHORT).show();

    }
}