package com.example.a470group;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.a470group.databinding.ActivityMainScreenBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The type Main screen.
 */
public class MainScreen extends AppCompatActivity {
  private ActivityMainScreenBinding binding;
  public static ArrayList<Stop> stops = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getData();

    binding = ActivityMainScreenBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    BottomNavigationView navView = findViewById(R.id.nav_view);
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
        R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_help)
        .build();
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main_screen);
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    NavigationUI.setupWithNavController(binding.navView, navController);
  }

  private void getData() {

    // Get the longitude and latitude from json
    Resources res = getResources();
    InputStream is = res.openRawResource(R.raw.stopdata2);
    Scanner scanner = new Scanner(is);
    StringBuilder builder = new StringBuilder();

    while(scanner.hasNextLine()) {
      builder.append(scanner.nextLine());
    }
    //parseJson(builder.toString());
    String s = builder.toString();
    try {
      JSONObject rootJson = new JSONObject(s);
      JSONArray allStops = rootJson.getJSONArray("allStops");
      ArrayList<ArrayList<String>> dayList = new ArrayList<ArrayList<String>>(10);

      for (int i = 0; i < allStops.length(); ++i) {
        rootJson = allStops.getJSONObject(i);


        // This return the (Latitude, Longitude) of Stops

        JSONObject location = new JSONObject(rootJson.getString("location"));
        double latitude = location.getDouble("latitude");
        double longitude = location.getDouble("longitude");
//        Log.d(FRAGMENT_NAME, location.getString("latitude"));
//        Log.d(FRAGMENT_NAME, location.getString("longitude"));
        //String stopName = rootJson.getString("desctiption");

        // add markers

        //Stop stopsMarker = new Stop(latitude, longitude, stopName);
        stops.add(new Stop(latitude, longitude, rootJson.getString("title")));

      }
    }catch(JSONException e){
      e.printStackTrace();
    }
  }

}