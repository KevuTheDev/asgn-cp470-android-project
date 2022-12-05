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
  protected static final String ACTIVITY_NAME = "MainScreen";
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

      for (int i = 0; i < allStops.length(); ++i) {
        rootJson = allStops.getJSONObject(i);

        String title = rootJson.getString("title");

        // LOCATION
        JSONObject location = new JSONObject(rootJson.getString("location"));
        double latitude = location.getDouble("latitude");
        double longitude = location.getDouble("longitude");

        // ROUTES
        JSONArray rootRoutes = rootJson.getJSONArray("routes");
        ArrayList<Route> routes = new ArrayList<Route>();

        // looping routes
        for (int j = 0; j < rootRoutes.length(); j++) {
          JSONObject aRoute = rootRoutes.getJSONObject(j);
          int aRouteID = aRoute.getInt("stop_route_id");

          // SCHEDULES
          JSONArray rootSchedules = aRoute.getJSONArray("schedule");
          ArrayList<Schedule> schedules = new ArrayList<Schedule>();

          for (int k = 0; k < rootSchedules.length(); k++) {
            JSONObject aSchedule = rootSchedules.getJSONObject(k);
            String aSchedule_day = aSchedule.getString("day");
            String aSchedule_arrival = aSchedule.getString("arrival_time");

            Schedule outSchedule = new Schedule(aSchedule_day, aSchedule_arrival);
            schedules.add(outSchedule);
          }

          // Store schedules onto routes

          Route outRoute = new Route(aRouteID, schedules);
          routes.add(outRoute);
        }

        //Stop stopsMarker = new Stop(latitude, longitude, stopName);
        stops.add(new Stop(latitude, longitude, title, routes));
      }
    }catch(JSONException e){
      e.printStackTrace();
    }
  }

}