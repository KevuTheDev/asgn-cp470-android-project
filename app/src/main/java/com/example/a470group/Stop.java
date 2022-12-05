package com.example.a470group;

import com.google.android.gms.maps.model.LatLng;

/**
 * The type Stop.
 */
public class Stop {
  private static double lng;
  private static double lat;
  private String name;
  private LatLng latlng;

  /**
   * Instantiates a new Stop.
   *
   * @param lat  the lat
   * @param lng  the lng
   * @param name the name
   */
  public Stop(double lat,double lng, String name){
    this.name = name;
    this.lng = lng;
    this.lat = lat;
    this.latlng = new LatLng(this.lat, this.lng);
  }

  /**
   * Get lng double.
   *
   * @return the double
   */
  public double getLng(){
    return this.lng;
  }

  /**
   * Get lat double.
   *
   * @return the double
   */
  public double getLat(){
    return this.lat;
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
}
