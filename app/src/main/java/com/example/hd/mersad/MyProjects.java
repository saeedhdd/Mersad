package com.example.hd.mersad;

import android.annotation.TargetApi;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProjects extends FatherActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_projects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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


        MyApp.myApi.getMyProjects(User.user.username).enqueue(new Callback<Project[]>() {
            @Override
            public void onResponse(Call<Project[]> call, Response<Project[]> response) {
                Project[] projects = response.body();
                for (Project record:projects) {
                    addRecord(record);
                }
            }

            @Override
            public void onFailure(Call<Project[]> call, Throwable t) {

            }
        });

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addRecord(Project project) {

        TableLayout tl = (TableLayout) findViewById(R.id.table_my_projects);
        TextView[] viewsInRow = new TextView[4];
        TableRow row = new TableRow(this);
        for (int i = 0; i < 3 ; i++) {
            TextView tv = new TextView(this);
            tv.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/IRANSANS_LIGHT.TTF"));
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            viewsInRow[i] = tv;
            TextView tx = (TextView) findViewById(R.id.myprojects_date);
            tv.setLayoutParams(tx.getLayoutParams());
            row.addView(tv);

        }
        viewsInRow[2].setText(project.proj_name);
        viewsInRow[1].setText(project.state);
        viewsInRow[0].setText(project.description);

        tl.addView(row);
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



}
