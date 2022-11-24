package com.example.a470group;

import com.google.android.gms.maps.model.LatLng;

public class Stop {
  private static double lng;
  private static double lat;
  private String name;
  private LatLng latlng;
  public Stop(double lat,double lng, String name){
    this.name = name;
    this.lng = lng;
    this.lat = lat;
    this.latlng = new LatLng(this.lat, this.lng);
  }
  public double getLng(){
    return this.lng;
  }
  public double getLat(){
    return this.lat;
  }
  public String getName(){
    return this.name;
  }
  public LatLng getLatLng(){
    return this.latlng;
  }
}
