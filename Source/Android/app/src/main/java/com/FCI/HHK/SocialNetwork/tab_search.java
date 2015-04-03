package com.FCI.HHK.SocialNetwork;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    ImageButton searchbutton;
    ImageButton friend;
    TableLayout friend_table;
    TableLayout searchtable;
    TextView tv[] = new TextView[100];
    Button B[] = new Button[100];

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.tab_search, container, false);
        searchname = (EditText) V.findViewById(R.id.searchtext);
        searchbutton = (ImageButton) V.findViewById(R.id.searchbutton);
        searchbutton.setOnClickListener(this);
        searchbutton.setId(0);
        searchtable = (TableLayout) V.findViewById(R.id.searchtable);

        friend = (ImageButton) V.findViewById(R.id.show_friend);
       friend_table = (TableLayout) V.findViewById(R.id.friendtable);

        friend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UserController controller = Application.getUserController();

                controller.bundle = HomeActivity.bundle;
                System.out.println(HomeActivity.bundle);
                controller.listfriend(controller.GetActiveUserId());
            }
        });


        if (HomeActivity.extras.getString("search_service").equals("search")) {
            int size = Integer.valueOf(HomeActivity.extras.getString("search_arraysize"));
            //    String name = HomeActivity.extras.getString("friendname");
            //    String welcome = HomeActivity.extras.getString("status");
            //    System.out.println(welcome + " " + name);
            try {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(HomeActivity.extras.getString("search_array"));
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


        if (HomeActivity.extras.getString("FriendList_service").equals("FriendList")) {
            int size = Integer.valueOf(HomeActivity.extras.getString("FriendList_arraysize"));

            try {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(HomeActivity.extras.getString("FriendList_array"));
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
                    tv[i].setText(ret.get("friend_name").toString());
                    tv[i].setId(Integer.valueOf(ret.get("friend_id").toString()));
                    row.addView(tv[i]);


                    B[i] = new Button(getActivity());
                    B[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT));
                    B[i].setPadding(5, 5, 5, 5);
                    B[i].setText("msg");
                    B[i].setWidth(20);
                    B[i].setId(Integer.valueOf(String.valueOf(i)));
                    row.addView(B[i]);
                    friend_table.addView(row);
                    B[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            final View V= v;

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            LayoutInflater li = LayoutInflater.from(getActivity());
                            final View promptsView = li.inflate(R.layout.message_dialog, null);

                            builder.setView(promptsView);


                            builder.setPositiveButton("send", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                           final EditText msg = (EditText) promptsView
                                                    .findViewById(R.id.messagecontent);
                                            UserController controller = Application.getUserController();
                                            System.out.println(tv[V.getId()].getId());
                                            System.out.println(tv[V.getId()].getText());
                                            controller.sendmessage(controller.GetActiveUserId(),String.valueOf(tv[V.getId()].getId()),
                                                    controller.GetActiveUserName(),String.valueOf(tv[V.getId()].getText()),msg.getText().toString());
                                            System.out.println("no thing yet :P");

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
                    });


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
            controller.search(searchname.getText().toString(), controller.GetActiveUserName(), controller.GetActiveUserId());

        }
    }
}
