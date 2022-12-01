package com.example.a470group.ui.home;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a470group.R;
import com.example.a470group.Stop;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeFragment extends Fragment {
  private GoogleMap mMap;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Initialize view
    View view=inflater.inflate(R.layout.fragment_home, container, false);

    // Initialize map fragment
    SupportMapFragment supportMapFragment=(SupportMapFragment)
        getChildFragmentManager().findFragmentById(R.id.google_map);




    // Async map
    supportMapFragment.getMapAsync(new OnMapReadyCallback() {


      @Override
      public void onMapReady(GoogleMap googleMap) {


        // When map is loaded
        mMap = googleMap;
        Stop[] stops = new Stop[4];
        stops[1] = new Stop(43.348709,-80.311033,"Albert / Ballantyne");
        stops[2] = new Stop(43.488146,-80.541394,"Albert / Greenbrier");
        stops[0] = new Stop(43.490533,-80.539508,"Albert / Longwood");
        stops[3] = new Stop(43.487633, -80.541571,"Albert / Quiet");

        addStopMarker(stops[0]);
        addStopMarker(stops[1]);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(stops[0].getLatLng()));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12));
      }
    });
    // Return view
    return view;
  }

  /**
   * Given a stop, will take its data and put a marker onto the map
   * @param stop - A Stop object to convert into a marker on map
   */
  private void addStopMarker(Stop stop) {
    if (stop == null) return;

    mMap.addMarker(new MarkerOptions().position(stop.getLatLng()).title(stop.getName()));
  }
}
