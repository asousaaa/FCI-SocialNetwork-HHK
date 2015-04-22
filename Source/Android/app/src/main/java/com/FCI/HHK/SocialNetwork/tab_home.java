package com.FCI.HHK.SocialNetwork;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.FCI.HHK.Controllers.Application;
import com.FCI.HHK.Controllers.UserController;
import com.FCI.HHK.SocialNetwork.R;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class tab_home extends Fragment implements View.OnClickListener {
    Button helloTextView;
    ImageButton out;
    TableLayout ll;
    Button bt[] = new Button[100];

    @Override
    public void onCreate(Bundle savedInstanceState) {

        UserController controller = Application.getUserController();
        controller.home_tab = tab_home.this;
        super.onCreate(savedInstanceState);

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        UserController controller = Application.getUserController();

        View V = inflater.inflate(R.layout.tab_home, container, false);
        out = (ImageButton) V.findViewById(R.id.Logout);
        out.setId(0);
        out.setOnClickListener(this);

        String name = "";

        if (HomeActivity.extras.containsKey("username")) {
            name = HomeActivity.extras.getString("username");

        }

        helloTextView = (Button) V.findViewById(R.id.hellotext);
        String text = name;
        helloTextView.setText(text);
        helloTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Application.getAppContext(),
                        NewsFeedActivity.class);

                startActivity(intent);
            }
        });

        if (controller.bundle != null)
            if (controller.bundle.get("Notification_service").equals("Notification")) {
                ll = (TableLayout) V.findViewById(R.id.notcation);
                int size = Integer.valueOf((String) controller.bundle.get("Notification_arraysize"));

                try {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse((String) controller.bundle.get("Notification_array"));
                    JSONArray object = (JSONArray) obj;
                    for (int i = 1; i <= size; i++) {
                        JSONObject ret = (JSONObject) object.get(i - 1);

                        bt[i] = new Button(getActivity());
                        System.out.println("enter" + ret.get("not_id") + " " + ret.get("type"));
                        bt[i].setTextSize(Float.parseFloat("25"));
                        bt[i].setId(Integer.valueOf(ret.get("not_id").toString()));
                        bt[i].setGravity(Gravity.CENTER);
                        int color = Color.argb(100, 80, 180, 255);
                        bt[i].setBackgroundColor(color);

                        bt[i].setText(ret.get("type").toString());
                        //  bt[i].setId(Integer.valueOf(ret.get("not_id").toString()));
                        ll.addView(bt[i]);

                        bt[i].setOnClickListener(this);

                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        return V;
    }

    public void onClick(View v) {
        System.out.println(v.getId());
        if (v.getId() == 0) {
            UserController controller = Application.getUserController();
            controller.logout();
        } else {

            Button b = (Button) v.findViewById(v.getId());
            ll.removeView(b);
            System.out.println(b.getText());
        }
    }


}
