package com.example.hd.mersad;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by hd on 2018/7/27 AD.
 */

public class FatherActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent ;

        Class<?> dest = this.getClass();

        if (id == R.id.nav_events) {
            dest = Events.class;
        } else if (id == R.id.nav_myprojects) {
            dest = MyProjects.class;
        } else if (id == R.id.nav_profile) {
            dest = Profile.class;

//        } else if (id == R.id.nav_reports) {
//            dest = Report.class;

        } else if (id == R.id.nav_logout) {
            dest = LoginActivity.class;

        } else if (id == R.id.nav_home) {
            dest = dashboardActivity.class;


        }


        intent = new Intent(this,dest);
        startActivity(intent);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
