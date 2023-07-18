package com.example.myweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class SearchCity extends AppCompatActivity {

    private RelativeLayout homeRL ;
    private ImageView backIV, searchIV, iconIV;
    private TextView cityNameTV, conditionTV, temperatureTV, windSpeedTV;
    private RecyclerView weatherRV;
    private TextInputEditText cityEditTIET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);

        backIV = (ImageView) findViewById(R.id.idIVBack);
        searchIV = (ImageView) findViewById(R.id.idIVSearch);
        iconIV = (ImageView) findViewById(R.id.idIVIcon);
        cityNameTV = (TextView) findViewById(R.id.idTVCityName);
        conditionTV = (TextView) findViewById(R.id.idTVCondition);
        temperatureTV = (TextView) findViewById(R.id.idTVTemperature);
        cityEditTIET = (TextInputEditText) findViewById(R.id.idEdtCity);
        windSpeedTV = (TextView) findViewById(R.id.idTVWindSpeed);

        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityName = cityEditTIET.getText().toString();

                String url = "https://api.weatherapi.com/v1/forecast.json?key=bf5f9e0f68274a1b9e2200314222208&q="+cityName+"&days=1&aqi=no&alerts=no";
                Fetch fetch = new Fetch(getApplicationContext(), url, new ResponseListener() {
                    @Override
                    public void onResponse(String data) {
                        //Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                        try {
                            cityNameTV.setText(cityName);

                            JSONObject response = new JSONObject(data);

                            String cityName = response.getJSONObject("location").getString("name");


                            String temperature = response.getJSONObject("current").getString("temp_c");
                            //Toast.makeText(SearchCity.this, temperature, Toast.LENGTH_SHORT).show();
                            temperatureTV.setText(temperature+"°c");

                            String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                            conditionTV.setText(condition);

                            String conditionIcon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                            //Toast.makeText(SearchCity.this, conditionIcon, Toast.LENGTH_SHORT).show();

                            String windSpeed = response.getJSONObject("current").getString("wind_kph");
                            windSpeedTV.setText(windSpeed+" km/h");

                        } catch (Exception e) {
                            //Toast.makeText(SearchCity.this, "Enter a Valid City Name", Toast.LENGTH_SHORT).show();
                            cityNameTV.setText("Enter a Valid City Name");
                            // baal

                        }
                    }
                });
                fetch.getData();
            }
        });

        //Toast.makeText(this, "This is search City page", Toast.LENGTH_SHORT).show();
    }
}