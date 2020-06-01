package com.example.hd.mersad;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends FatherActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AutoCompleteTextView mUsernameView;
    private AutoCompleteTextView mEmailView;
    private EditText signupPasswordView;
    private EditText signupSecondPasswordView;
    private EditText address;
    private EditText city;
    private EditText state;
    private Switch benefactor;
    private Switch needing;
    private EditText name;
    private EditText familyName;
    private EditText age;
    private ToggleButton sex;
    private Button submitChanges;
    private EditText phone;
    private EditText postalcode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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



        mEmailView = (AutoCompleteTextView) findViewById(R.id.signup_email);
        signupPasswordView = (AutoCompleteTextView) findViewById(R.id.signup_password);
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.signup_username);
        mUsernameView.setEnabled(false);
        signupSecondPasswordView = (AutoCompleteTextView) findViewById(R.id.signup_second_password);
        address = (EditText) findViewById(R.id.signup_address) ;
        city = (EditText) findViewById(R.id.signup_city) ;
        state = (EditText) findViewById(R.id.signup_state) ;
        benefactor = (Switch) findViewById(R.id.benefactor);
        needing = (Switch) findViewById(R.id.needing);
        name = (EditText) findViewById(R.id.signup_name) ;
        familyName = (EditText) findViewById(R.id.signup_familyname) ;
        age = (EditText) findViewById(R.id.signup_age) ;
        address = (EditText) findViewById(R.id.signup_address) ;
        sex = (ToggleButton) findViewById(R.id.gender_button) ;
        submitChanges = (Button) findViewById(R.id.profile_change_button) ;
        postalcode = (EditText) findViewById(R.id.postalCode) ;
        phone = (EditText) findViewById(R.id.phone) ;




        MyApp.myApi.getProfile(User.user.username).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body()==null)
                    return;
                if (response.body()==null)
                    return;
                User user = response.body();
                mEmailView.setText(user.email);
                signupPasswordView.setText(user.password);
                signupSecondPasswordView.setText(user.password);
                mUsernameView.setText(user.username);
                address.setText(user.address);
                city.setText(user.city);
                state.setText(user.province);
                benefactor.setChecked(user.is_benefactor);
                needing.setChecked(user.is_charity);
                name.setText(user.first_name);
                familyName.setText(user.last_name);
                age.setText(user.age+"");
                sex.setChecked(user.gender.equals("male"));
                postalcode.setText(user.postalcode);
                phone.setText(user.phone);

            }



            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mUsernameView.setError(getString(R.string.error_connection));
            }
        });

        needing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                benefactor.setChecked(!isChecked);
                familyName.setVisibility(!isChecked?View.VISIBLE:View.GONE);
                age.setVisibility(!isChecked?View.VISIBLE:View.GONE);
                sex.setVisibility(!isChecked?View.VISIBLE:View.GONE);
            }
        });

        benefactor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                needing.setChecked(!isChecked);
                familyName.setVisibility(isChecked?View.VISIBLE:View.GONE);
                age.setVisibility(isChecked?View.VISIBLE:View.GONE);
                sex.setVisibility(isChecked?View.VISIBLE:View.GONE);
            }
        });



        submitChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                attemptChangeSubmission();
                MyApp.myApi.postProfile(User.user).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.body()){

                        }else mUsernameView.setError(getString(R.string.error_connection));

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        mUsernameView.setError(getString(R.string.error_connection));
                    }
                });
            }
        });



    }


    private void attemptChangeSubmission() {
        // Reset errors.
        mEmailView.setError(null);
        signupPasswordView.setError(null);
        mUsernameView.setError(null);
        signupSecondPasswordView.setError(null);



        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String username = mUsernameView.getText().toString();
        String secondPassword = signupSecondPasswordView.getText().toString();
        String password = signupPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.


        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }
        else if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!(email.contains("@"))) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }else if (TextUtils.isEmpty(password)) {
            signupPasswordView.setError(getString(R.string.error_field_required));
            focusView = signupPasswordView;
            cancel = true;
        }else if (!TextUtils.isEmpty(password) && !(password.length()>4)) {
            signupPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = signupPasswordView;
            cancel = true;
        }else if (!password.equals(secondPassword)) {
            signupSecondPasswordView.setError(getString(R.string.prompt_password_match));
            focusView = signupSecondPasswordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            User user = User.user;
            user.email = mEmailView.getText().toString();
            user.password = password;
            user.address = address.getText().toString();
            user.city = city.getText().toString();
            user.province = state.getText().toString();
            user.phone = phone.getText().toString();
            user.postalcode = postalcode.getText().toString();
            user.is_benefactor = benefactor.isChecked();
            user.is_charity = needing.isChecked();
            user.first_name = name.getText().toString();
            user.last_name = familyName.getText().toString();
            user.age = Integer.parseInt(age.getText().toString());
            user.gender = sex.isChecked()?"male":"female";
        }
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
