package com.FCI.HHK.SocialNetwork;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.FCI.HHK.Controllers.Application;
import com.FCI.HHK.Controllers.UserController;

public class activity_timeline extends Activity {


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(this);
        final ViewGroup container = new ViewGroup(this) {
            @Override
            protected void onLayout(boolean b, int i, int i2, int i3, int i4) {

            }
        };

        final UserController controller = Application.getUserController();
        View V = inflater.inflate(R.layout.timeline, container, false);

        LinearLayout post = (LinearLayout) V.findViewById(R.id.timeline_post);
        LinearLayout timeline_content = (LinearLayout) V.findViewById(R.id.timeline_content);

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


        LinearLayout ll;
        for (int i = 1; i < 5; i++) {
            View timeline_dialog = inflater.inflate(R.layout.timeline_dialog, null);

            ll = new LinearLayout(this);
            System.out.println(ll.getParent());
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            p.setMargins(10, 10, 10, 10);

            ll.setId(i);
            timeline_dialog.setId(i);
            ll.addView(timeline_dialog, p);

            timeline_content.addView(ll);
            System.out.println(ll.getParent());
        }

        setContentView(V);

    }



}




