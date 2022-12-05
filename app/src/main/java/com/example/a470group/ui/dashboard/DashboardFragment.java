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

/**
 * The type Dashboard fragment.
 */
public class DashboardFragment extends Fragment {
  /**
   * The constant FRAGMENT_NAME.
   */
//todo : Change variables ot match the content of the class
  protected static final String FRAGMENT_NAME = "DashboardFragment";
  /**
   * The constant GET_JSON.
   */
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
    //parseJson(builder.toString());
    String s = builder.toString();


    try{
      JSONObject rootJson = new JSONObject(s);

      JSONArray allStops = rootJson.getJSONArray("allStops");

      ArrayList<ArrayList<String>> dayList = new ArrayList<ArrayList<String>>(10);

      for(int i = 0 ; i < allStops.length(); ++i){
        rootJson = allStops.getJSONObject(i);


        // This return the (Latitude, Longitude) of Stops

        Log.d(FRAGMENT_NAME, rootJson.getString("location"));
        JSONObject location = new JSONObject(rootJson.getString("location"));
        long latitude = location.getLong("latitude");
        long longitude = location.getLong("longitude");
//        Log.d(FRAGMENT_NAME, location.getString("latitude"));
//        Log.d(FRAGMENT_NAME, location.getString("longitude"));


        // This return the Title of Stops
        String stopTitle = rootJson.getString("title");
        stopsList.add(stopTitle);
        Log.d(FRAGMENT_NAME, stopTitle);

        // Get the stop route id


        JSONObject schedule = new JSONObject(rootJson.getString("schedule"));
        JSONArray week = schedule.getJSONArray("week");
        Log.d(FRAGMENT_NAME, "-----------------------------");
        Log.d(FRAGMENT_NAME, week.toString());
        int count = 0 ;

        String[] weekendName = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        ArrayList<String> daySchedule = new ArrayList<String>(7);

        for(int runningTime = 0 ; runningTime < week.length(); ++runningTime) {

          String info = weekendName[count] + " : " + week.get(runningTime).toString();
          daySchedule.add(info);

          //Log.d(FRAGMENT_NAME, info);

          ///////

          count += 1 ;
        }
        dayList.add(daySchedule);
        Log.d(FRAGMENT_NAME, "DayList: " +dayList.toString() + "<<<------------- (1)");

      }
      Log.d(FRAGMENT_NAME, dayList.toString() + "<<<------------- (2)");
      dashboardAdapter = new DashboardAdapter( root.getContext());

      dashboardListView = root.findViewById(R.id.listViewStops);
      dashboardListView.setAdapter(dashboardAdapter);
      dashboardAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/
      dashboardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
          Log.i(FRAGMENT_NAME, dayList.toString());
          makeDialog(getContext(), stopsList.get(i), dayList.get(i));
          Log.d(FRAGMENT_NAME, "!!!!!!!!!!!");
        }
      });

      //Log.d(FRAGMENT_NAME , allStops.getString("location"));
      builder.append(allStops);

    }catch(JSONException e) {
      e.printStackTrace();
    }

    return root;
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  private class DashboardAdapter extends ArrayAdapter<String> {
    /**
     * Instantiates a new Dashboard adapter.
     *
     * @param ctx the ctx
     */
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

  /**
   * Make dialog.
   *
   * @param ctx       the ctx
   * @param stopName  the stop name
   * @param stopTimes the stop times
   */
  public void makeDialog(Context ctx, String stopName, ArrayList<String> stopTimes) {

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

  /**
   * On stop click.
   *
   * @param view the view
   */
  public void onStopClick(View view){
    TextView x = (TextView) view;
    Log.i("butt",x.toString());
    Log.i("butt",x.getText().toString());
  }
}