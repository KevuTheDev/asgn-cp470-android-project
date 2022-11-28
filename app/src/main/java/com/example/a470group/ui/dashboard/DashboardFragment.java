package com.example.a470group.ui.dashboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
  //todo : Change variables ot match the content of the class
  protected static final String FRAGMENT_NAME = "DashboardFragment";
  private FragmentDashboardBinding binding;
  private ListView dashboardListView;
  private DashboardAdapter dashboardAdapter;
  private ArrayList<String> stopsList = new ArrayList<String>();

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    DashboardViewModel dashboardViewModel =
            new ViewModelProvider(this).get(DashboardViewModel.class);

    binding = FragmentDashboardBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

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
      }
    });

    return root;
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