package com.FCI.SWE.SocialNetwork;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class tab_home extends Fragment  {
    TextView helloTextView;
    Activity act = new Activity();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.tab_home, container, false);
        Activity act=new Activity();

        String status = HomeActivity.extras.getString("status");
        String name = "",welcome="Hello";

        if(HomeActivity.extras.containsKey("name")){
            name = HomeActivity.extras.getString("name");
            welcome = "Welcome " + name;
        }

        helloTextView = (TextView) V.findViewById(R.id.hellotext);
        String text = status + " ... welcome "+ welcome;
        helloTextView.setText(text);


        return V;
    }







}