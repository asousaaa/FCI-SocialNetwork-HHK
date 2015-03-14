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

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.tab_home, container, false);
        Activity act=new Activity();


        String name = "",welcome="";

        if(HomeActivity.extras.containsKey("username")){
            name = HomeActivity.extras.getString("username");
            welcome = "Welcome " + name;
        }

        helloTextView = (TextView) V.findViewById(R.id.hellotext);
        String text = welcome;
        helloTextView.setText(text);


        return V;
    }







}