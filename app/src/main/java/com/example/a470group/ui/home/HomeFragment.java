package com.example.a470group.ui.home;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

  // Register the permissions callback, which handles the user's response to the
  // system permissions dialog. Save the return value, an instance of
  // ActivityResultLauncher, as an instance variable.
  private ActivityResultLauncher<String> requestPermissionLauncher =
          registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
              // Permission is granted. Continue the action or workflow in your
              // app.
            } else {
              Toast.makeText(this.getActivity().getApplicationContext(), "You may use a custom location.", Toast.LENGTH_LONG).show();
              // Explain to the user that the feature is unavailable because the
              // feature requires a permission that the user has denied. At the
              // same time, respect the user's decision. Don't link to system
              // settings in an effort to convince the user to change their
              // decision.
            }
          });



  final int minTime = 200;
  final int minDistance = 10;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Log.d(FRAGMENT_NAME, "onCreateView Created");

    // Initialize view
    View view=inflater.inflate(R.layout.fragment_home, container, false);

    // Initialize map fragment
    SupportMapFragment supportMapFragment=(SupportMapFragment)
        getChildFragmentManager().findFragmentById(R.id.google_map);

    supportMapFragment.getMapAsync(this);

    if (mMap != null) {
      mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    return view;
  }

  @Override
  @SuppressLint("MissingPermission")
  public void onMapReady(@NonNull GoogleMap googleMap) {
    Log.d(FRAGMENT_NAME, "onMapReady Created");
    mMap = googleMap;
    mMap.setOnMyLocationButtonClickListener(this);
    mMap.setOnMyLocationClickListener(this);

    checkLocationPermission();

    // When map is loaded
    Stop[] stops = new Stop[4];
    stops[0] = new Stop(43.491552,-80.537559,"Albert / Weber");
    stops[1] = new Stop(43.490533,-80.539508,"Albert / Longwood");
    stops[2] = new Stop(43.488146,-80.541394,"Albert / Greenbrier");
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

    mMap.addMarker(new MarkerOptions().position(stop.getLatLng()).title(stop.getName()));
  }



  @SuppressLint("MissingPermission")
  private void checkLocationPermission() {
    if(ContextCompat.checkSelfPermission(this.requireActivity().getApplicationContext(),
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this.requireActivity().getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      Log.d(FRAGMENT_NAME, "Permission Granted");
      // You can use the API that requires the permission.

      //performAction(...);
      mMap.setMyLocationEnabled(true);


      mMap.getMyLocation();

    } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
      // In an educational UI, explain to the user why your app requires this
      // permission for a specific feature to behave as expected, and what
      // features are disabled if it's declined. In this UI, include a
      // "cancel" or "no thanks" button that lets the user continue
      // using your app without granting the permission.
      Log.d(FRAGMENT_NAME, "None");

      //showInContextUI(...);
    } else {
      // You can directly ask for the permission.
      // The registered ActivityResultCallback gets the result of this request.
      requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
      Log.d(FRAGMENT_NAME, "hmm");
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
    Toast.makeText(this.getActivity().getApplicationContext(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
  }
}
