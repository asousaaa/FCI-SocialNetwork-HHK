package com.FCI.HHK.SocialNetwork;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.FCI.HHK.Controllers.Application;
import com.FCI.HHK.Controllers.UserController;
import com.FCI.HHK.SocialNetwork.R;

public class tab_home extends Fragment implements View.OnClickListener  {
    TextView helloTextView;
    Button out ;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.tab_home, container, false);
        out= (Button) V.findViewById(R.id.Logout);
        out.setOnClickListener(this);

        String name = "", welcome = "";

        if (HomeActivity.extras.containsKey("username")) {
            name = HomeActivity.extras.getString("username");
            welcome = "Welcome " + name;
        }

        helloTextView = (TextView) V.findViewById(R.id.hellotext);
        String text = welcome;
        helloTextView.setText(text);


        return V;
    }

    public void onClick(View v) {
        UserController controller = Application.getUserController();
        controller.logout();

    }
}