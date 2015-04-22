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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FCI.HHK.Controllers.Application;
import com.FCI.HHK.Controllers.UserController;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class tab_page extends Fragment implements View.OnClickListener {


    public void onCreate(Bundle savedInstanceState) {
        UserController controller = Application.getUserController();
        controller.page_tab = tab_page.this;
        super.onCreate(savedInstanceState);
        controller.ViewPageService(controller.GetActiveUserName(),"page_owner");
    }

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        final UserController controller = Application.getUserController();
        View V = inflater.inflate(R.layout.tab_pages, container, false);
        LinearLayout page_content = (LinearLayout) V.findViewById(R.id.page_content);
        LinearLayout pagesearch_content = (LinearLayout) V.findViewById(R.id.page_search_content);

        final View promptsView = inflater.inflate(R.layout.create_page_dialog, container, false);

        Button create = (Button) V.findViewById(R.id.createpage);
        ImageButton refresh = (ImageButton) V.findViewById(R.id.pagerereshbutton);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.ViewPageService(controller.GetActiveUserName(),"page_owner");
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createpagepopup(promptsView);
            }
        });
        ImageButton search = (ImageButton) V.findViewById(R.id.pagesearchbutton);
        final EditText name= (EditText) V.findViewById(R.id.pagesearchtext);

        search.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.SearchPageService(name.getText().toString(),"page_name");
            }
        });


        if (controller.bundle != null)
            if (controller.bundle.get("SearchPageService_service").equals("SearchPageService")) {
                int size = Integer.valueOf((String) controller.bundle.get("SearchPageService_arraysize"));


                try {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse((String) controller.bundle.get("SearchPageService_array"));
                    JSONArray object = (JSONArray) obj;



        LinearLayout ll;
        for (int i = 1; i <= size; i++) {
                        JSONObject ret = (JSONObject) object.get(i - 1);

                        View page_dialog = inflater.inflate(R.layout.page_dialog, null);

                        TextView page_owner = (TextView) page_dialog.findViewById(R.id.page_owner);

                        page_owner.setText(ret.get("page_owner").toString());

                        TextView page_category = (TextView) page_dialog.findViewById(R.id.page_category);
                        page_category.setText(ret.get("cateagory").toString());

                        TextView page_name = (TextView) page_dialog.findViewById(R.id.page_name);
                        page_name.setText(ret.get("page_name").toString());

                        Button like = (Button) page_dialog.findViewById(R.id.pagelike);
                        like.setText(ret.get("numoflikes").toString());

                        ll = new LinearLayout(getActivity());

                        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                        p.setMargins(10, 10, 10, 10);

                        ll.setId(i);
                           page_dialog.setId(i);
                        ll.addView(page_dialog, p);

                        pagesearch_content.addView(ll);


                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }


        if (controller.bundle != null)
            if (controller.bundle.get("ViewPageService_service").equals("ViewPageService")) {
                int size = Integer.valueOf((String) controller.bundle.get("ViewPageService_arraysize"));


                try {
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse((String) controller.bundle.get("ViewPageService_array"));
                    JSONArray object = (JSONArray) obj;


                    LinearLayout ll;
                    for (int i = 1; i <= size; i++) {
                        JSONObject ret = (JSONObject) object.get(i - 1);

                        View page_dialog = inflater.inflate(R.layout.page_dialog, null);

                        TextView page_owner = (TextView) page_dialog.findViewById(R.id.page_owner);

                        page_owner.setText(ret.get("page_owner").toString());

                        TextView page_category = (TextView) page_dialog.findViewById(R.id.page_category);
                        page_category.setText(ret.get("cateagory").toString());

                        TextView page_name = (TextView) page_dialog.findViewById(R.id.page_name);
                        page_name.setText(ret.get("page_name").toString());

                        Button like = (Button) page_dialog.findViewById(R.id.pagelike);
                        like.setText(ret.get("numoflikes").toString());

                        ll = new LinearLayout(getActivity());

                        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                        p.setMargins(10, 10, 10, 10);

                        ll.setId(i);
                        page_dialog.setId(i);
                        ll.addView(page_dialog, p);

                        page_content.addView(ll);


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

    }


    public void createpagepopup(final View v) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);


        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                final EditText pagecaegory = (EditText) v.findViewById(R.id.pagecateagory);
                final EditText pagename = (EditText) v.findViewById(R.id.pagename);
                UserController controller = Application.getUserController();
                controller.Createpage(controller.GetActiveUserName(),pagename.getText().toString()
                ,pagecaegory.getText().toString());



            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });


        AlertDialog dialog = builder.create();
        dialog.show();
    }

}


