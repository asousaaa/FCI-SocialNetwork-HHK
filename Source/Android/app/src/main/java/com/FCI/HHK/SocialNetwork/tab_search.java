package com.FCI.HHK.SocialNetwork;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;

import com.FCI.HHK.Controllers.Application;
import com.FCI.HHK.Controllers.UserController;
import com.FCI.HHK.SocialNetwork.R;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class tab_search extends Fragment implements View.OnClickListener {

    EditText searchname;
    Button searchbutton;
    TableLayout searchtable;
    TextView tv[] = new TextView[100];
    Button B[] = new Button[100];

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.tab_search, container, false);
        searchname = (EditText) V.findViewById(R.id.searchtext);
        searchbutton = (Button) V.findViewById(R.id.searchbutton);
        searchbutton.setOnClickListener(this);
        searchbutton.setId(0);
        searchtable = (TableLayout) V.findViewById(R.id.searchtable);


        if (HomeActivity.extras.getString("service").equals("search")) {
            int size = Integer.valueOf(HomeActivity.extras.getString("arraysize"));
            //    String name = HomeActivity.extras.getString("friendname");
            //    String welcome = HomeActivity.extras.getString("status");
            //    System.out.println(welcome + " " + name);
            try {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(HomeActivity.extras.getString("array"));
                JSONArray object = (JSONArray) obj;


                for (int i = 1; i <= size; i++) {
                    JSONObject ret = (JSONObject) object.get(i - 1);
                    TableRow row = new TableRow(getActivity());
                    row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT));

                    // inner for loop


                    tv[i] = new TextView(getActivity());
                    tv[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT));

                    tv[i].setPadding(15, 15, 0, 0);
                    tv[i].setTextSize(25);
                    tv[i].setText(ret.get("name").toString());
                    tv[i].setId(Integer.valueOf(ret.get("ID").toString()));
                    row.addView(tv[i]);


                    B[i] = new Button(getActivity());
                    B[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT));
                    B[i].setPadding(5, 5, 5, 5);
                    B[i].setText("Add");
                    B[i].setWidth(20);
                    B[i].setId(Integer.valueOf(String.valueOf(i)));
                    row.addView(B[i]);
                    searchtable.addView(row);
                    TextView TV = new TextView(getActivity());
                    TV.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT));
                    TV.setText("Email :" + ret.get("email").toString());
                    searchtable.addView(TV);

                    B[i].setOnClickListener(this);


                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        return V;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        System.out.println("id " + v.getId());
        if (v.getId() == 0) {
            UserController controller = Application.getUserController();
            controller.search(searchname.getText().toString(), controller.GetActiveUserName(), controller.GetActiveUserId());
        } else {

            UserController controller = Application.getUserController();
            System.out.println(tv[v.getId()].getText());
            System.out.println(tv[v.getId()].getId());
            System.out.println(controller.GetActiveUserName());
            System.out.println(controller.GetActiveUserId());
            controller.sendRequest(tv[v.getId()].getText().toString(), String.valueOf((tv[v.getId()].getId())), controller.GetActiveUserName(), controller.GetActiveUserId());

        }
    }
}
