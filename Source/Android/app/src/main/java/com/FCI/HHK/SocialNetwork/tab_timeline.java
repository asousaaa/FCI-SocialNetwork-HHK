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

public class tab_timeline extends Fragment implements View.OnClickListener {


    public void onCreate(Bundle savedInstanceState) {
         UserController controller = Application.getUserController();
        controller.timeline_tab = tab_timeline.this;
        super.onCreate(savedInstanceState);
        controller.ViewUserPostService(controller.GetActiveUserId());
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


       final UserController controller = Application.getUserController();
        View V = inflater.inflate(R.layout.timeline, container, false);

        LinearLayout post = (LinearLayout) V.findViewById(R.id.timeline_post);
        LinearLayout timeline_content = (LinearLayout) V.findViewById(R.id.timeline_content);
        Button person = (Button) V.findViewById(R.id.timeline_type);
        person.setText(controller.GetActiveUserName());

        View post_dialog = inflater.inflate(R.layout.post_dialog, null);



        Button postbutton = (Button) post_dialog.findViewById(R.id.Post_button);
        final EditText post_content = (EditText) post_dialog.findViewById(R.id.post_content);
        final EditText post_feel = (EditText) post_dialog.findViewById(R.id.post_feel);

        postbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.CreatePostService(controller.GetActiveUserName(), controller.GetActiveUserId()
                        ,post_feel.getText().toString(),post_content.getText().toString(),"user");
                post_content.setText("");
                post_feel.setText("");
            }
        });

        post.addView(post_dialog);


        if (controller.bundle != null)
            if (controller.bundle.get("ViewUserPostService_service").equals("ViewUserPostService")) {
                int size = Integer.valueOf((String) controller.bundle.get("ViewUserPostService_arraysize"));


                try {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse((String) controller.bundle.get("ViewUserPostService_array"));
                    JSONArray object = (JSONArray) obj;

                    LinearLayout ll;
                    for (int i = 1; i <= size; i++) {
                        JSONObject ret = (JSONObject) object.get(i - 1);

                        View timeline_dialog = inflater.inflate(R.layout.timeline_dialog, null);

                        Button post_owner  = (Button) timeline_dialog.findViewById(R.id.post_owner);

                        post_owner.setText(ret.get("user_name").toString());

                        if(! ret.get("feeling").toString().equals("")){
                            post_owner.setText(post_owner.getText() + " is "+ret.get("feeling").toString());
                        }

                        TextView post_content1  = (TextView) timeline_dialog.findViewById(R.id.post_contentt);
                        post_content1.setText(ret.get("content").toString());

                        Button like = (Button) timeline_dialog.findViewById(R.id.post_like);
                        like.setId(Integer.valueOf(ret.get("user_id").toString()));

                        ll = new LinearLayout(getActivity());

                        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                        p.setMargins(10, 10, 10, 10);

                        ll.setId(i);
                        timeline_dialog.setId(i);
                        ll.addView(timeline_dialog, p);

                        timeline_content.addView(ll);



                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }


/*
        LinearLayout ll;
        for (int i = 1; i < 5; i++) {
            View timeline_dialog = inflater.inflate(R.layout.timeline_dialog, null);

            ll = new LinearLayout(getActivity());
            System.out.println(ll.getParent());
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            p.setMargins(10, 10, 10, 10);

            ll.setId(i);
            timeline_dialog.setId(i);
            ll.addView(timeline_dialog, p);

            timeline_content.addView(ll);
            System.out.println(ll.getParent());
        }
        */

        return V;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        System.out.println("id " + v.getId());

    }
}




