package com.example.a470group.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a470group.R;
import com.example.a470group.Route;
import com.example.a470group.Schedule;
import com.example.a470group.Stop;

public class StopDialog {
  @SuppressLint("SetTextI18n")
  public static void makeDialog(Context ctx, Stop myStop,LayoutInflater inflater) {

    AlertDialog.Builder myCustomDialogBuilder = new AlertDialog.Builder(ctx);

    final View view = inflater.inflate(R.layout.dialog_dashboard_stops, null);

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
}
