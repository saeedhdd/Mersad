package com.example.hd.mersad;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class dashboardActivity extends FatherActivity {


    ViewPager viewpager;
    TabLayout tabs;
    SlidePagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tabs = (TabLayout) findViewById(R.id.tabLayout);
        setUpViewPager();
        tabs.setupWithViewPager(viewpager);
        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        setUpTabIcons();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    private void setUpTabIcons() {
        tabs.getTabAt(0).setIcon(R.drawable.ic_menu_home);
        tabs.getTabAt(1).setIcon(R.drawable.ic_menu_search);
        tabs.getTabAt(2).setIcon(R.drawable.ic_menu_search);
    }

    private void prepareSlides() {
        String[] titles = {getString(R.string.reports),getString(R.string.search_nonaghdi), getString(R.string.search_naghdi), getString(R.string.dashboard)};

        pagerAdapter.addFragment(
                    dashBoardFrag.newSlide(R.layout.dashslide),
                    titles[3]);
        pagerAdapter.addFragment(
                    SearchNFragment.newSlide(),
                    titles[2]);
        pagerAdapter.addFragment(
                    SearchGhFragment.newSlide(),
                    titles[1]);

    }

    private void setUpViewPager() {
        pagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(pagerAdapter);
        prepareSlides();

    }

    public class SlidePagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments;
        List<String> tabTitles;

        public SlidePagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
            tabTitles = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        public void addFragment(Fragment fragment, String tabTitle) {
            fragments.add(fragment);
            tabTitles.add(tabTitle);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles.get(position);
        }


    }

    public void transit(View v){


        Intent intent ;

        Class<?> dest = this.getClass();


        switch ((String)v.getTag()){
            case "1":
                dest = Profile.class;
                break;
            case "2":
                dest = Events.class;
                break;
            case "3":
                dest = MyProjects.class;
                break;
            case "4":
                dest = LoginActivity.class;
                break;

        }

        intent = new Intent(this,dest);
        startActivity(intent);

    }


}
