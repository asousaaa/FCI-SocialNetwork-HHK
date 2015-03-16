package com.FCI.SWE.SocialNetwork;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.Toast;

public class HomeActivity extends FragmentActivity implements
        TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    private TabHost mTabHost;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    public static Bundle extras;
    private SharedPreferences shared;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, tab_home.class.getName()));
        fragments.add(Fragment.instantiate(this, tab_search.class.getName()));
        fragments.add(Fragment.instantiate(this, tab_request.class.getName()));
        fragments.add(Fragment.instantiate(this, tab_setting.class.getName()));

        this.mPagerAdapter = new PagerAdapter(
                super.getSupportFragmentManager(), fragments);

        this.mViewPager = (ViewPager) super.findViewById(R.id.viewpager);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);

        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();

        AddTab(this.mTabHost,
                this.mTabHost.newTabSpec("Tab1")
                        .setIndicator(
                                "",
                                getResources().getDrawable(
                                        R.drawable.ic_action_person)));

        AddTab(this.mTabHost,
                this.mTabHost.newTabSpec("Tab2")
                        .setIndicator(
                                "",
                                getResources().getDrawable(
                                        R.drawable.ic_action_search)));
        AddTab(this.mTabHost,
                this.mTabHost.newTabSpec("Tab3").setIndicator("",
                        getResources().getDrawable(R.drawable.ic_action_group)));

        AddTab(this.mTabHost,
                this.mTabHost.newTabSpec("Tab4").setIndicator(
                        "",
                        getResources().getDrawable(
                                R.drawable.ic_action_about)));

        mTabHost.setOnTabChangedListener(this);

        extras = getIntent().getExtras();

        shared = getSharedPreferences("social", MODE_PRIVATE);
        position = shared.getInt("pos", 0);
        mTabHost.setCurrentTab(position);
        mViewPager.setCurrentItem(position);
        if (extras.getString("service").equals("sendrequest")) {
            Toast.makeText(getApplication(), "Request send Successfully", Toast.LENGTH_LONG).show();
        }
        if (extras.getString("service").equals("acceptrequest")) {

            Toast.makeText(getApplication(), "Request accepted Successfully", Toast.LENGTH_LONG).show();
        }
    }



    private void AddTab(TabHost tabHost, TabHost.TabSpec tabSpec) {
        tabSpec.setContent(new TabFactory(this));
        tabHost.addTab(tabSpec);
    }

    public void onTabChanged(String tag) {
        // TabInfo newTab = this.mapTabInfo.get(tag);
        SharedPreferences.Editor editor = getSharedPreferences("social",
                MODE_PRIVATE).edit();

        int pos = this.mTabHost.getCurrentTab();

        editor.putInt("pos", pos);
        editor.commit();

        this.mViewPager.setCurrentItem(pos);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub
        this.mTabHost.setCurrentTab(arg0);
    }

    class TabFactory implements TabContentFactory {

        private final Context mContext;

        public TabFactory(Context context) {
            mContext = context;
        }

        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }

}
