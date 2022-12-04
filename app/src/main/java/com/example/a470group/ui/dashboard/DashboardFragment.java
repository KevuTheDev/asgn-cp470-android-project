package com.example.a470group.ui.dashboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.a470group.R;
import com.example.a470group.databinding.FragmentDashboardBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class DashboardFragment extends Fragment {
  //todo : Change variables ot match the content of the class
  protected static final String FRAGMENT_NAME = "DashboardFragment";
  protected static final String GET_JSON = "JSON Start: ";

  private FragmentDashboardBinding binding;
  private ListView dashboardListView;
  private DashboardAdapter dashboardAdapter;
  private ArrayList<String> stopsList = new ArrayList<String>();
  private Dialog alert;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    DashboardViewModel dashboardViewModel =
            new ViewModelProvider(this).get(DashboardViewModel.class);

    binding = FragmentDashboardBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

    /*
    * This will load the bus data from the raw json data
    * After loading data, data wil be passed to the stops fragment to display information
    * */
    Resources res = getResources();

    InputStream is = res.openRawResource(R.raw.stops);

    Scanner scanner = new Scanner(is);

    StringBuilder builder = new StringBuilder();

    while(scanner.hasNextLine()) {
      builder.append(scanner.nextLine());
    }
    parseJson(builder.toString());


    stopsList.add("Stop 1: Bricker Academic");
    stopsList.add("Stop 2: King St. North");
    stopsList.add("Stop 3: University Ave");

    dashboardAdapter = new DashboardAdapter( root.getContext());

    dashboardListView = root.findViewById(R.id.listViewStops);
    dashboardListView.setAdapter(dashboardAdapter);
    dashboardAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/
    dashboardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String[] stopTimes = {"1:00am : Bus 1","2:00am : Bus 1","4:30pm : Bus 5"};
        makeDialog(getContext(), stopsList.get(i), stopTimes);
        Log.d(FRAGMENT_NAME, "!!!!!!!!!!!");
      }
    });

    return root;
  }

  // This loads json to stops
  private void parseJson(String s){

    StringBuilder builder = new StringBuilder();

    try{
      JSONObject root = new JSONObject(s);

      JSONArray allStops = root.getJSONArray("allStops");


      for(int i = 0 ; i < allStops.length(); ++i){
        root = allStops.getJSONObject(i);


        // This return the (Latitude, Longitude) of Stops
        Log.d(FRAGMENT_NAME, "!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        Log.d(FRAGMENT_NAME, root.getString("location"));
        JSONObject location = new JSONObject(root.getString("location"));
        long latitude = location.getLong("latitude");
        long longitude = location.getLong("longitude");
        Log.d(FRAGMENT_NAME, location.getString("latitude"));
        Log.d(FRAGMENT_NAME, location.getString("longitude"));


        // This return the Title of Stops
        String stopTitle = root.getString("title");

        // This return the description of Stops
        String description = root.getString("description");

        // This return the (Schedule and Week) of Stops
        JSONObject schedule = new JSONObject(root.getString("schedule"));
        JSONArray week = schedule.getJSONArray("week");
        //Log.d(FRAGMENT_NAME, week.toString());
        for(int runningTime = 0 ; runningTime < week.length(); ++runningTime) {
          Log.d(FRAGMENT_NAME, week.get(runningTime).toString());

        }

      }

      //Log.d(FRAGMENT_NAME , allStops.getString("location"));
      builder.append(allStops);

    }catch(JSONException e) {
      e.printStackTrace();
    }
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  private class DashboardAdapter extends ArrayAdapter<String> {
    public DashboardAdapter(Context ctx) {
      super(ctx, 0);
    }

    public int getCount(){
      return stopsList.size();
    }

    public String getItem(int position){
      return stopsList.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent){
      LayoutInflater inflater = DashboardFragment.this.getLayoutInflater();
      View result = null ;
      TextView message;

      result = inflater.inflate(R.layout.stops_row, null);
      message = (TextView) result.findViewById(R.id.TextMessage);

      message.setText(   getItem(position)  ); // get the string at position
      return result;
    }
  }

  public void makeDialog(Context ctx, String stopName, String[] stopTimes) {

    AlertDialog.Builder myCustomDialogBuilder = new AlertDialog.Builder(ctx);
    LayoutInflater myInflater = this.getLayoutInflater();
    final View view = myInflater.inflate(R.layout.dialog_dashboard_stops, null);

    myCustomDialogBuilder.setView(view)
            .setNegativeButton("Close",
                    new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                      }
                    });

    LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayoutDialog);

    // Adding the stop times onto the dialog
    for (String s : stopTimes) {
      TextView textView = new TextView(ctx);
      textView.setText(s);
      linearLayout.addView(textView);

      LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
      params.setMargins(75, 5, 75, 5);
      textView.setLayoutParams(params);
    }
    myCustomDialogBuilder.setTitle(stopName); // set title

    Dialog myDialog = myCustomDialogBuilder.create();
    myDialog.show();
  }

  public void onStopClick(View view){
    TextView x = (TextView) view;
    Log.i("butt",x.toString());
    Log.i("butt",x.getText().toString());
  }
}