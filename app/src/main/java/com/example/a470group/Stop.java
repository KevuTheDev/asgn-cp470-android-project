package com.example.a470group;

import com.google.android.gms.maps.model.LatLng;

public class Stop {
  private static double lng;
  private static double lat;
  private String name;
  public Stop(double lng,double lat, String name){
    this.name = name;
    this.lng = lng;
    this.lat = lat;

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
    return new LatLng(this.lng,this.lat);
  }
}
