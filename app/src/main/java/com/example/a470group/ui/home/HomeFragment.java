package com.example.a470group.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a470group.R;
import com.example.a470group.Stop;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeFragment extends Fragment implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback{
  protected static final String FRAGMENT_NAME = "HomeFragment";
  private static final int DEFAULT_ZOOM = 15;
  private GoogleMap mMap;

  final int minTime = 200;
  final int minDistance = 10;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Log.d(FRAGMENT_NAME, "onCreateView Created");

    // Initialize view
    View view=inflater.inflate(R.layout.fragment_first, container, false);

    // Initialize map fragment
    SupportMapFragment supportMapFragment=(SupportMapFragment)
        getChildFragmentManager().findFragmentById(R.id.google_map);

    supportMapFragment.getMapAsync(this);

    // Return view
    return view;
  }

  @Override
  @SuppressLint("MissingPermission")
  public void onMapReady(@NonNull GoogleMap googleMap) {
    Log.d(FRAGMENT_NAME, "onMapReady Created");
    mMap = googleMap;

    mMap.setOnMyLocationButtonClickListener(this);
    mMap.setOnMyLocationClickListener(this);

    //mMap.setMyLocationEnabled(true);
    checkLocationPermission();

    // When map is loaded
    Stop[] stops = new Stop[4];
    stops[0] = new Stop(43.491552, -80.537559,"Albert / Weber");
    stops[1] = new Stop(43.490533,-80.539508,"Albert / Longwood");
    stops[2] = new Stop(43.488146, -80.541394,"Albert / Greenbrier");
    stops[3] = new Stop(43.487633,-80.541571,"Albert / Quiet");

    // add markers
    addStopMarker(stops[0]);
    addStopMarker(stops[1]);
    addStopMarker(stops[2]);
    addStopMarker(stops[3]);

    // Zoom to the given latlng,
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stops[2].getLatLng(), DEFAULT_ZOOM));
  }

  /**
   * Given a stop, will take its data and put a marker onto the map
   * @param stop - A Stop object to convert into a marker on map
   */
  private void addStopMarker(Stop stop) {
    if (stop == null) return;

    MarkerOptions b = new MarkerOptions();
    b.snippet("hello");

    mMap.addMarker(new MarkerOptions().position(stop.getLatLng()).title(stop.getName()));
  }

  private void checkLocationPermission() {
    // checks if device has minimum sdk version
    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        return;

    // checks if device have valid permissions for use
    if(ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      Log.d(FRAGMENT_NAME, "YES");
    }else {
      // otherwise will ask the device user for permissions

      requestPermissions(
              new String[]{Manifest.permission
                      .ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

      // a boolean function to detect if the permission is not given
      // this function can be used to bring up a rationale
      // https://developer.android.com/reference/androidx/core/app/ActivityCompat#shouldShowRequestPermissionRationale(android.app.Activity,%20java.lang.String)
      shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
    }
  }

  @Override
  public boolean onMyLocationButtonClick() {
    Toast.makeText(this.getActivity().getApplicationContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
    // Return false so that we don't consume the event and the default behavior still occurs
    // (the camera animates to the user's current position).
    return false;
  }

  @Override
  public void onMyLocationClick(@NonNull Location location) {

  }
}
