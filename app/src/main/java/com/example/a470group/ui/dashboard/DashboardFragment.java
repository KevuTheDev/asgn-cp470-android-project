package com.example.a470group.ui.dashboard;

import android.annotation.SuppressLint;
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

import com.example.a470group.MainScreen;
import com.example.a470group.R;
import com.example.a470group.Route;
import com.example.a470group.Schedule;
import com.example.a470group.Stop;
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
  private Dialog alert;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    DashboardViewModel dashboardViewModel =
            new ViewModelProvider(this).get(DashboardViewModel.class);

    binding = FragmentDashboardBinding.inflate(inflater, container, false);
    View root = binding.getRoot();


      dashboardAdapter = new DashboardAdapter( root.getContext());

      dashboardListView = root.findViewById(R.id.listViewStops);
      dashboardListView.setAdapter(dashboardAdapter);
      dashboardAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/
      dashboardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

          makeDialog(getContext(), MainScreen.stops.get(i));
          Log.d(FRAGMENT_NAME, "!!!!!!!!!!!");
        }
      });


    return root;
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  private class DashboardAdapter extends ArrayAdapter<Stop> {
    /**
     * Instantiates a new Dashboard adapter.
     *
     * @param ctx the ctx
     */
    public DashboardAdapter(Context ctx) {
      super(ctx, 0);
    }

    public int getCount(){
      return MainScreen.stops.size();
    }

    public Stop getItem(int position){
      return MainScreen.stops.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent){
      LayoutInflater inflater = DashboardFragment.this.getLayoutInflater();
      View result = null ;
      TextView message;

      result = inflater.inflate(R.layout.stops_row, null);
      message = (TextView) result.findViewById(R.id.TextMessage);

      message.setText(   getItem(position).getName()  ); // get the string at position
      return result;
    }
  }

  /**
   * Make dialog.
   *
   * @param ctx       the ctx
   */
  @SuppressLint("SetTextI18n")
  public void makeDialog(Context ctx, Stop myStop) {

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

    //  Adding the stop times onto the dialog
    for (Route r : myStop.getRoutes()) {
      TextView textView = new TextView(ctx);
      textView.setText("Stop ID: " + r.getStopID());
      linearLayout.addView(textView);

      LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
      params.setMargins(75, 5, 75, 5);
      textView.setLayoutParams(params);

      for (Schedule s: r.getSchedules()) {
        TextView textView1 = new TextView(ctx);
        textView1.setText(s.getDay() + ":\n" + s.getArrivalTime());
        linearLayout.addView(textView1);

        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) textView1.getLayoutParams();
        params1.setMargins(75, 5, 75, 5);
        textView1.setLayoutParams(params1);
      }
    }
    myCustomDialogBuilder.setTitle(myStop.getName()); // set title

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