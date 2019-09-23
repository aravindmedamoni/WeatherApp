package com.aravind.wheatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText city;
    private TextView result;
    private ImageView imageView;


    //https://api.openweathermap.org/data/2.5/weather?q=Hyderabad&appid=putYourOwnApiKey


    String baseURL = "https://api.openweathermap.org/data/2.5/weather?q=";
    String API = "&appid=putYourOwnApiKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.result_button);
        city = findViewById(R.id.getCity);
        result = findViewById(R.id.result);
        imageView = findViewById(R.id.imageView);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // imageView.setVisibility(View.GONE);
                result.setText("");
                String cityName = city.getText().toString();

                if (!cityName.equals("")){
                    String myURL = baseURL + city.getText().toString()+API;
                    Log.i("TAG","URL: "+myURL);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myURL, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    Log.i("JSON", "JSON: " + jsonObject);

                                    try {
                                        String info = jsonObject.getString("weather");
                                        Log.i("INFO", "INFO: "+ info);

                                        JSONArray ar = new JSONArray(info);

                                        for (int i = 0; i < ar.length(); i++){
                                            JSONObject parObj = ar.getJSONObject(i);

                                            String myWeather = parObj.getString("main");
                                            result.setText(myWeather);
                                            Log.i("ID", "ID: " + parObj.getString("id"));
                                            Log.i("MAIN", "MAIN: " + parObj.getString("main"));

                                            switch(myWeather){
                                                case "cloud":
                                                    imageView.setImageResource(R.drawable.winter);
                                                    break;
                                                case "Clear":
                                                    imageView.setImageResource(R.drawable.winter);
                                                    break;

                                            }
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


//                                try {
//                                    String coor = jsonObject.getString("coord");
//                                    Log.i("COOR", "COOR: " + coor);
//                                    JSONObject co = new JSONObject(coor);
//
//                                    String lon = co.getString("lon");
//                                    String lat = co.getString("lat");
//
//                                    Log.i("LON", "LON: " + lon);
//                                    Log.i("LAT", "LAT: " + lat);
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }


                                }
                            },

                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Log.i("Error", "Something went wrong" + volleyError);

                                }
                            }


                    );
                    MySingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);
                    city.setText("");
                }else {
                    result.setText("");
                    city.setError("Please Enter City Name");
                }
            }
        });
    }

}
