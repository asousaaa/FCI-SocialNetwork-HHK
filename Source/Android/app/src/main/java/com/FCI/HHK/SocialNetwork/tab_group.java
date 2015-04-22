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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.FCI.HHK.Controllers.Application;
import com.FCI.HHK.Controllers.UserController;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Text;

public class tab_group extends Fragment implements View.OnClickListener {


    Button creategroup;
    ImageButton refreshgroup;
    LinearLayout group_table;
    TableLayout friend_table;
    TableLayout chat_table;

    TextView tv[] = new TextView[100];
    CheckBox cb[] = new CheckBox[100];

    TextView groupname[] = new TextView[100];
    TextView members[] = new TextView[100];

    public void onCreate(Bundle savedInstanceState) {
        UserController controller = Application.getUserController();
        controller.group_tab = tab_group.this;
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        final UserController controller = Application.getUserController();

        // Inflate the layout for this fragment
        final View V = inflater.inflate(R.layout.tab_group, container, false);

        //  LayoutInflater li = LayoutInflater.from(getActivity());
        final View msgview = inflater.inflate(R.layout.chat_dialog, container, false);
        refreshgroup = (ImageButton) V.findViewById(R.id.refresh_group);

        final View promptsView = inflater.inflate(R.layout.create_group_dialog, container, false);

        friend_table = (TableLayout) promptsView.findViewById(R.id.friendtable);
        chat_table = (TableLayout) msgview.findViewById(R.id.chattable);

        group_table = (LinearLayout) V.findViewById(R.id.grouptable);
        creategroup = (Button) V.findViewById(R.id.create_group);
        creategroup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UserController controller = Application.getUserController();
                controller.creategroup = true;
                controller.listfriend(controller.GetActiveUserId());


            }
        });

        refreshgroup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UserController controller = Application.getUserController();
                controller.viewchatgroup(controller.GetActiveUserId());
            }
        });

        if (controller.bundle != null)
            if (controller.bundle.get("ViewChatGroupService_service").equals("ViewChatGroupService")) {
                int size = Integer.valueOf((String) controller.bundle.get("ViewChatGroupService_arraysize"));


                try {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse((String) controller.bundle.get("ViewChatGroupService_array"));
                    JSONArray object = (JSONArray) obj;

                    LinearLayout ll = new LinearLayout(getActivity());
                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                    p.setMargins(10, 10, 10, 10);

                    for (int i = 1; i <= size; i++) {
                        JSONObject ret = (JSONObject) object.get(i - 1);

                        if (i == size && i % 2 != 0) {
                            View v = inflater.inflate(R.layout.group_chat, null);

                            v.setId(Integer.valueOf(i));
                            groupname[i] = new TextView(getActivity());
                            ImageButton ab = (ImageButton) v.findViewById(R.id.opengroupchat);
                            groupname[i] = (TextView) v.findViewById(R.id.groupchatname);
                            groupname[i].setText(ret.get("groupname").toString());
                            groupname[i].setId(Integer.valueOf(ret.get("chatid").toString()));
                            ab.setId(Integer.valueOf(ret.get("chatid").toString()));
                            final String finalI1 =  groupname[i].getText().toString();
                            ab.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    RelativeLayout r = (RelativeLayout) v.findViewById(R.id.tab_group);
                                    if (msgview.getParent() != null) {
                                        ((ViewGroup) msgview.getParent()).removeView(msgview);
                                    }
                                   controller.viewmsgchatgroup(String.valueOf(v.getId()));
                                    System.out.println("id = " + v.getId());
                                }
                            });
                            members[i] = new TextView(getActivity());
                            members[i] = (TextView) v.findViewById(R.id.groupchatmembers);
                            String mem = ret.get("member").toString();
                            mem = mem.replaceAll("\",\"", ", ");
                            mem = mem.replace("[\"", "");
                            mem = mem.replace("\"]", "");
                            members[i].setText(mem);
                            members[i].setId(Integer.valueOf(String.valueOf(i)));

                            ll.addView(v, p);
                            System.out.println("enter 2");
                            group_table.addView(ll, p);
                            ll = new LinearLayout(getActivity());
                            p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                            p.setMargins(10, 10, 10, 10);
                            group_table.addView(ll);
                            System.out.println("enter end");
                        } else if (i % 2 == 0) {
                            View v = inflater.inflate(R.layout.group_chat, null);

                            v.setId(Integer.valueOf(i));
                            groupname[i] = new TextView(getActivity());
                            ImageButton ab = (ImageButton) v.findViewById(R.id.opengroupchat);
                            groupname[i] = (TextView) v.findViewById(R.id.groupchatname);
                            groupname[i].setText(ret.get("groupname").toString());
                            groupname[i].setId(Integer.valueOf(ret.get("chatid").toString()));
                            ab.setId(Integer.valueOf(ret.get("chatid").toString()));
                            final String finalI =  groupname[i].getText().toString();
                            ab.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    RelativeLayout r = (RelativeLayout) v.findViewById(R.id.tab_group);
                                    if (msgview.getParent() != null) {
                                        ((ViewGroup) msgview.getParent()).removeView(msgview);
                                    }
                                    controller.viewmsgchatgroup(String.valueOf(v.getId()));
                                    System.out.println("id = " + v.getId());
                                }
                            });
                            members[i] = new TextView(getActivity());
                            members[i] = (TextView) v.findViewById(R.id.groupchatmembers);
                            String mem = ret.get("member").toString();
                            mem = mem.replaceAll("\",\"", ", ");
                            mem = mem.replace("[\"", "");
                            mem = mem.replace("\"]", "");
                            members[i].setText(mem);
                            members[i].setId(Integer.valueOf(String.valueOf(i)));

                            ll.addView(v, p);
                            System.out.println("enter 2");
                            group_table.addView(ll, p);
                            ll = new LinearLayout(getActivity());
                            p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                            p.setMargins(10, 10, 10, 10);

                        } else {

                            View v = inflater.inflate(R.layout.group_chat, null);

                            v.setId(Integer.valueOf(i));
                            groupname[i] = new TextView(getActivity());
                            ImageButton ab = (ImageButton) v.findViewById(R.id.opengroupchat);
                            groupname[i] = (TextView) v.findViewById(R.id.groupchatname);
                            groupname[i].setText(ret.get("groupname").toString());
                            groupname[i].setId(Integer.valueOf(ret.get("chatid").toString()));
                            ab.setId(Integer.valueOf(ret.get("chatid").toString()));
                            final String finalI2 = groupname[i].getText().toString();
                            ab.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    RelativeLayout r = (RelativeLayout) v.findViewById(R.id.tab_group);
                                    if (msgview.getParent() != null) {
                                        ((ViewGroup) msgview.getParent()).removeView(msgview);
                                    }

                                    controller.viewmsgchatgroup(String.valueOf(v.getId()));
                                    System.out.println("id = " + v.getId());

                                }
                            });

                            members[i] = new TextView(getActivity());
                            members[i] = (TextView) v.findViewById(R.id.groupchatmembers);
                            String mem = ret.get("member").toString();
                            mem = mem.replaceAll("\",\"", ", ");
                            mem = mem.replace("[\"", "");
                            mem = mem.replace("\"]", "");
                            members[i].setText(mem);
                            members[i].setId(Integer.valueOf(String.valueOf(i)));

                            ll.addView(v, p);
                            System.out.println("enter");
                        }

                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }

        if (controller.bundle != null)
            if (controller.bundle.get("ViewMsgChatGroupService_service").equals("ViewMsgChatGroupService")) {
                int size = Integer.valueOf((String) controller.bundle.get("ViewMsgChatGroupService_arraysize"));

                String name="",chatid="";
                try {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse((String) controller.bundle.get("ViewMsgChatGroupService_array"));
                    JSONArray object = (JSONArray) obj;
                    System.out.println("Size = "+ size);

                    for (int i = 1; i <= size; i++) {
                        JSONObject ret = (JSONObject) object.get(i - 1);

                        name=ret.get("chatname").toString();
                        chatid = ret.get("chatid").toString();
                        TableRow row = new TableRow(getActivity());
                        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));


                        TextView tv = new TextView(getActivity());
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT));

                        tv.setPadding(5, 5, 0, 0);
                        tv.setTextSize(20);

                        tv.setText(ret.get("sender").toString()+" : "+ret.get("content").toString());
                        tv.setId(Integer.valueOf(i));
                        row.addView(tv);
                        row.setId(Integer.valueOf(i));
                        chat_table.addView(row);
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if(size<1){
                    chatid = controller.bundle.get("ViewMsgChatGroupService_array").toString();
                }
                Msggrouppopup(msgview, name,chatid);

            }




        if (controller.bundle != null)
            if (controller.bundle.get("FriendList_service").equals("FriendList") && controller.creategroup) {
                int size = Integer.valueOf((String) controller.bundle.get("FriendList_arraysize"));


                try {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse((String) controller.bundle.get("FriendList_array"));
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
                JSONArray names = new JSONArray();
                JSONArray ides = new JSONArray();
                UserController controller = Application.getUserController();

                for (int i = 1; i < 100; i++) {
                    if (cb[i] == null) break;
                    if (cb[i].isChecked()) {

                        ides.add(String.valueOf(tv[i].getId()));
                        names.add(String.valueOf(tv[i].getText()));
                    }
                }
                ides.add(controller.GetActiveUserId());
                names.add(controller.GetActiveUserName());

                System.out.println(names.toJSONString());
                System.out.println(ides.toJSONString());


                controller.CreateGroupChat(groupname.getText().toString(), controller.GetActiveUserName(), names.toJSONString(), ides.toJSONString());

            }
        })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });


        AlertDialog dialog = builder.create();

        dialog.show();


    }

    public void Msggrouppopup(final View msgview, final String name, final String id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setView(msgview);
        TextView tv = (TextView) msgview.findViewById(R.id.titel);
        tv.setText(name);
        ImageButton ib = (ImageButton) msgview.findViewById(R.id.chatbuttonsend);
        final EditText et = (EditText) msgview.findViewById(R.id.chat_msg);

       final AlertDialog dialog = builder.create();
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserController controller = Application.getUserController();
                System.out.println("ID = "+id);
                controller.MsgChatGroup(id, controller.GetActiveUserName(), et.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        System.out.println("id " + v.getId());

    }
}




