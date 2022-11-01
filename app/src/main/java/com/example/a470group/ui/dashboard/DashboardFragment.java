package com.example.a470group.ui.dashboard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.a470group.R;
import com.example.a470group.databinding.FragmentDashboardBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

  private FragmentDashboardBinding binding;
  private ArrayList<String> messages = new ArrayList<String>();
  private ListView ChatList;
  ChatAdapter messageAdapter;


  public void makeDialog(Context ctx, String stopName){
    String[] stopTime = {"1:00","2:00","4:30"};
    LinearLayout linearLayout = new LinearLayout(ctx);
    linearLayout.setOrientation(LinearLayout.VERTICAL);
    for( int i = 0; i <stopTime.length; i++ )
    {
      TextView textView = new TextView(ctx);
      textView.setText(stopTime[i]);
      linearLayout.addView(textView);
    }


    AlertDialog.Builder builder2 = new AlertDialog.Builder(ctx);
    builder2.setTitle(stopName);

    // Add the buttons
    builder2.setPositiveButton("Close", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {

      }
    });



    AlertDialog dialog2 = builder2.create();
    dialog2.setView(linearLayout);

    dialog2.show();
  }

  public void onStopClick(View view){
    TextView x = (TextView) view;
    Log.i("butt",x.toString());
    Log.i("butt",x.getText().toString());


  }

  private class ChatAdapter extends ArrayAdapter<String>{
    public ChatAdapter(Context ctx) {
      super(ctx, 0);
    }
    public int getCount(){
      return messages.size();
    }
    public String getItem(int position){
      return messages.get(position);
    }
    public View getView(int position, View convertView, ViewGroup parent){
      LayoutInflater inflater =DashboardFragment.this.getLayoutInflater();
      View result = null ;
      TextView message;



      result = inflater.inflate(R.layout.chat_row_incoming, null);
      message = (TextView)result.findViewById(R.id.TextMessage);


      message.setText(   getItem(position)  ); // get the string at position
      message.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          onStopClick(v);
          TextView x = (TextView) v;

          String name = x.getText().toString();
          makeDialog(getContext(),name);

        }
      });
      return result;
    }
  }

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    DashboardViewModel dashboardViewModel =
        new ViewModelProvider(this).get(DashboardViewModel.class);

    binding = FragmentDashboardBinding.inflate(inflater, container, false);
    View root = binding.getRoot();


    messages.add("Stop 1: Bricker Academic");
    messages.add("Stop 2: King St. North");
    messages.add("Stop 3: University Ave");

    messageAdapter =new ChatAdapter( root.getContext());

    ChatList = root.findViewById(R.id.stops);
    ChatList.setAdapter (messageAdapter);
    messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/




    return root;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}