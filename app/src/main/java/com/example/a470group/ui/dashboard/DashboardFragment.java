package com.example.a470group.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.a470group.R;
import com.example.a470group.databinding.FragmentDashboardBinding;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

  private FragmentDashboardBinding binding;
  private ArrayList<String> messages = new ArrayList<String>();
  private ListView ChatList;
  ChatAdapter messageAdapter;


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