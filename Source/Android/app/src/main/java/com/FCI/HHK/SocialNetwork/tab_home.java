package com.FCI.HHK.SocialNetwork;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    TextView helloTextView;
    Button out ;
    TableLayout ll;
    Button bt[] = new Button[100];

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

        if (HomeActivity.extras.getString("service").equals("Notification")) {
            ll = (TableLayout) V.findViewById(R.id.notcation);
            int size = Integer.valueOf(HomeActivity.extras.getString("arraysize"));

            try {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(HomeActivity.extras.getString("array"));
                JSONArray object = (JSONArray) obj;
                for (int i = 1; i <= size; i++) {
                    JSONObject ret = (JSONObject) object.get(i - 1);


                    bt[i].setTextSize(25);
                    bt[i].setId(Integer.valueOf("1"));
                    bt[i].setGravity(Gravity.CENTER);
                    int color = Color.argb(100, 80, 180, 255);
                    bt[i].setBackgroundColor(color);
                    if(ret.get("type").equals("request")) {
                        bt[i].setText(ret.get("friend_name").toString()+" send you request");
                    }
                    else{
                        bt[i].setText(ret.get("friend_name").toString()+" accept your request");
                    }
                    bt[i].setId(Integer.valueOf(ret.get("not_id").toString()));
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
        if(v.getId()==0) {
            UserController controller = Application.getUserController();
            controller.logout();
        }
      /*  else {

            Button b = (Button) v.findViewById(v.getId());
        ll.removeView(b);
            System.out.println(b.getText());
        }*/
    }


}
