package com.FCI.SWE.SocialNetwork;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.FCI.SWE.Controllers.Application;
import com.FCI.SWE.Controllers.UserController;

public class tab_request extends Fragment  implements View.OnClickListener {
    TextView tv[] = new TextView[10];
    Button B[] = new Button[10];
    TableLayout requesttable;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if(HomeActivity.extras.getString("service").equals("login")) {
            UserController controller = Application.getUserController();
            controller.viewRequest(controller.GetActiveUserId());
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.tab_request, container, false);

        if(HomeActivity.extras.getString("service").equals("viewrequest")) {
            String name = HomeActivity.extras.getString("vfriendname");
            String welcome = HomeActivity.extras.getString("status");
            requesttable = (TableLayout) V.findViewById(R.id.requesttable);


            for (int i = 1; i <= 1; i++) {

                TableRow row = new TableRow(getActivity());
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                // inner for loop

                tv[i] = new TextView(getActivity());
                tv[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                tv[i].setPadding(15, 15, 0, 0);
                tv[i].setTextSize(25);
                tv[i].setText(name);
                tv[i].setId(Integer.valueOf(HomeActivity.extras.get("vfriendid").toString()));
                row.addView(tv[i]);


                B[i] = new Button(getActivity());
                B[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                B[i].setPadding(5, 5, 5, 5);
                B[i].setText("Accept");
                B[i].setId(Integer.valueOf(HomeActivity.extras.get("vfriendid").toString()) + 1);
                requesttable.addView(row);
                row.addView(B[i]);
                B[i].setOnClickListener(this);


            }
        }



        return V;
    }

    @Override
    public void onClick(View view) {

        UserController controller = Application.getUserController();

        controller.acceptRequest(controller.GetActiveUserId(),String.valueOf((tv[1].getId()-1)));


    }
}
