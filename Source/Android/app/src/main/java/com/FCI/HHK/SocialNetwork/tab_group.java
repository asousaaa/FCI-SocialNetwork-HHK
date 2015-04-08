package com.FCI.HHK.SocialNetwork;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.FCI.HHK.Controllers.Application;
import com.FCI.HHK.Controllers.UserController;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class tab_group extends Fragment implements View.OnClickListener {


    Button creategroup;
    TableLayout group_table, friend_table;

    TextView tv[] = new TextView[100];
    CheckBox cb[] = new CheckBox[100];

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.tab_group, container, false);

      //  LayoutInflater li = LayoutInflater.from(getActivity());

        final View promptsView = inflater.inflate(R.layout.create_group_dialog,container,false);
        friend_table = (TableLayout) promptsView.findViewById(R.id.friendtable);

        creategroup = (Button) V.findViewById(R.id.create_group);
        group_table = (TableLayout) V.findViewById(R.id.grouptable);

        creategroup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UserController controller = Application.getUserController();
                controller.bundle = HomeActivity.bundle;
                controller.listfriend(controller.GetActiveUserId());
                controller.creategroup = true;


            }
        });
        UserController controller = Application.getUserController();



        if (HomeActivity.extras.getString("FriendList_service").equals("FriendList") && controller.creategroup  ) {
            int size = Integer.valueOf(HomeActivity.extras.getString("FriendList_arraysize"));


            try {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(HomeActivity.extras.getString("FriendList_array"));
                JSONArray object = (JSONArray) obj;


                for (int i = 1; i <= size; i++) {
                    JSONObject ret = (JSONObject) object.get(i - 1);
                    TableRow row = new TableRow(getActivity());
                    row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));

                    // inner for loop


                    tv[i] = new TextView(getActivity());
                    tv[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));

                    tv[i].setPadding(15, 15, 0, 0);
                    tv[i].setTextSize(25);
                    tv[i].setText(ret.get("friend_name").toString());
                    tv[i].setId(Integer.valueOf(ret.get("friend_id").toString()));
                    row.addView(tv[i]);


                    cb[i] = new CheckBox(getActivity());
                    cb[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    cb[i].setPadding(5, 5, 5, 5);
                    cb[i].setChecked(false);
                    cb[i].setId(Integer.valueOf(String.valueOf(i)));
                    row.addView(cb[i]);
                    friend_table.addView(row);


                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            controller.creategroup = false;
            Creategrouppopup(promptsView);
        }


        return V;
    }

    public void Creategrouppopup(final View promptsView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(promptsView);


        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                final EditText groupname = (EditText) promptsView
                        .findViewById(R.id.groupname);
                ArrayList names = new ArrayList();
                ArrayList ides = new ArrayList();
                    for(int i=1;i<100;i++){
                        if(cb[i]==null) break;
                        if (cb[i].isChecked() ){
                            ides.add(tv[i].getId());
                            names.add(tv[i].getText());
                        }
                    }

                UserController controller = Application.getUserController();
                System.out.println( names.toString());
                System.out.println( ides.toString());
/*
                ArrayList output = new ArrayList();
                String listString =  names.toString().substring(0, names.toString().length() - 1); // chop off brackets
                for (String token : new StringTokenizer(listString, ",")) {
                    output.add(token.trim());
                }
*/

                controller.CreateGroupChat(groupname.getText().toString(), controller.GetActiveUserName(), names.toString(), ides.toString());

            }
        })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });


// 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();

        dialog.show();


    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        System.out.println("id " + v.getId());

    }
}




