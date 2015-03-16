package com.FCI.SWE.SocialNetwork;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.WrapperListAdapter;

import com.FCI.SWE.Controllers.Application;
import com.FCI.SWE.Controllers.UserController;

public class tab_search extends Fragment implements View.OnClickListener {

    EditText searchname;
    Button searchbutton;
    TableLayout searchtable;
    TextView tv[] = new TextView[10];
    Button B[] = new Button[10];

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


        if(HomeActivity.extras.getString("service").equals("search")) {
            String name = HomeActivity.extras.getString("friendname");
            String welcome = HomeActivity.extras.getString("status");
            System.out.println(welcome + " " + name);


            for (int i = 1; i <= 1; i++) {

                TableRow row = new TableRow(getActivity());
                row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT));

                // inner for loop

               tv[i] = new TextView(getActivity());
                tv[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT));

                tv[i].setPadding(15, 15, 0, 0);
                tv[i].setTextSize(25);
                tv[i].setText(name);
                tv[i].setId(Integer.valueOf(HomeActivity.extras.get("friendid").toString()));
                row.addView(tv[i]);


                 B[i] = new Button(getActivity());
                B[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT));
                B[i].setPadding(5, 5, 5, 5);
                B[i].setText("Add");
                B[i].setId(Integer.valueOf(HomeActivity.extras.get("friendid").toString()) + 1);
                searchtable.addView(row);
                row.addView(B[i]);
                B[i].setOnClickListener(this);


        }

    }



        return V;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        System.out.println("id " + v.getId());
        if(v.getId()==0) {
            UserController controller = Application.getUserController();
            controller.search(searchname.getText().toString(), controller.GetActiveUserName(), controller.GetActiveUserId());
        }
        else{

            UserController controller = Application.getUserController();
            System.out.println(tv[1].getText());
            System.out.println(tv[1].getId());
            System.out.println(controller.GetActiveUserName());
            System.out.println(controller.GetActiveUserId());
            controller.sendRequest(tv[1].getText().toString(),String.valueOf((tv[1].getId())),controller.GetActiveUserName(),controller.GetActiveUserId());

        }
    }
}
