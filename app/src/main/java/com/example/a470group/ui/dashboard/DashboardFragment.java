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
    dashboardListView.setAdapter (dashboardAdapter);
    dashboardAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/

    return root;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  private class DashboardAdapter extends ArrayAdapter<String>{
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
      message.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          TextView x = (TextView) v;
          Log.i("butt",x.toString());
          Log.i("butt",x.getText().toString());

          String name = x.getText().toString();
          makeDialog(getContext(),name);
        }
      });
      return result;
    }
  }

  private class DialogListAdapter extends ArrayAdapter<String> {
    public DialogListAdapter(Context ctx) {
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
      message.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          TextView x = (TextView) v;
          Log.i("butt",x.toString());
          Log.i("butt",x.getText().toString());

          String name = x.getText().toString();
          makeDialog(getContext(),name);
        }
      });
      return result;
    }
  }

  public void makeDialog(Context ctx, String stopName){
    String[] stopTime = {"1:00am : Bus 1","2:00am : Bus 1","4:30pm : Bus 5"};

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

    for (String s : stopTime) {
      TextView textView = new TextView(ctx);
      textView.setText(s);
      linearLayout.addView(textView);
    }

    Dialog myDialog = myCustomDialogBuilder.create();
    myDialog.show();
//
//
//    LinearLayout linearLayout = new LinearLayout(ctx);
//    linearLayout.setOrientation(LinearLayout.VERTICAL);
//
//    for (String s : stopTime) {
//      TextView textView = new TextView(ctx);
//      textView.setText(s);
//      linearLayout.addView(textView);
//    }
//
//    AlertDialog.Builder builder2 = new AlertDialog.Builder(ctx);
//    builder2.setTitle(stopName);
//
//    // Add the buttons
//    builder2.setPositiveButton("Close", new DialogInterface.OnClickListener() {
//      public void onClick(DialogInterface dialog, int id) {
//
//      }
//    });
//
//    AlertDialog dialog2 = builder2.create();
//    dialog2.setView(linearLayout);
//    dialog2.show();
  }

  public void onStopClick(View view){
    TextView x = (TextView) view;
    Log.i("butt",x.toString());
    Log.i("butt",x.getText().toString());
  }

}