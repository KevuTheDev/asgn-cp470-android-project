package com.example.a470group;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * The type Stop.
 */
public class Stop {
  private static double lng;
  private static double lat;
  private String name;
  private LatLng latlng;
  private ArrayList<Route> routes;

  /**
   * Instantiates a new Stop.
   *
   * @param lat  the lat
   * @param lng  the lng
   * @param name the name
   */
  public Stop(double lat, double lng, String name, ArrayList<Route> routes){
    this.name = name;
    this.latlng = new LatLng(lat, lng);
    this.routes = routes;
  }

  /**
   * Get lng double.
   *
   * @return the double
   */
  public double getLng(){
    return this.latlng.longitude;
  }

  /**
   * Get lat double.
   *
   * @return the double
   */
  public double getLat(){
    return this.latlng.latitude;
  }

  /**
   * Get name string.
   *
   * @return the string
   */
  public String getName(){
    return this.name;
  }

  /**
   * Get lat lng lat lng.
   *
   * @return the lat lng
   */
  public LatLng getLatLng(){
    return this.latlng;
  }

  public ArrayList<Route> getRoutes() { return this.routes; }

  @NonNull
  public String toString() {
    StringBuilder output = new StringBuilder("Stop: " + this.name + " | " + getLat() + ", " + getLng() + "\n");
    for (Route route : routes) {
      output.append(routes.toString()).append("\n");
    }

    return output.toString();
  }
}
