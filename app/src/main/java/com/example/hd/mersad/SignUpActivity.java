package com.example.hd.mersad;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class SignUpActivity extends FatherActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
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
    private View mProgressView;
    private View mSignupFormView;
    private EditText phone;
    private EditText postalcode;


    private TextView stage1;
    private TextView stage2;
    private TextView stage3;

    Bundle bundle ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_up);
        // Set up the login form.
        bundle = new Bundle();

        mEmailView = (AutoCompleteTextView) findViewById(R.id.signup_email);
        signupPasswordView = (AutoCompleteTextView) findViewById(R.id.signup_password);
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.signup_username);
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
        postalcode = (EditText) findViewById(R.id.postalCode) ;
        phone = (EditText) findViewById(R.id.phone) ;
        sex = (ToggleButton) findViewById(R.id.gender_button) ;
        populateAutoComplete();

        stage1 = (TextView)findViewById(R.id.signup_stage1);
        stage2 = (TextView)findViewById(R.id.signup_stage2);
        stage3 = (TextView)findViewById(R.id.signup_stage3);


        ImageView imageView = (ImageView) findViewById(R.id.mersadLogo2);
        imageView.setImageResource(R.drawable.mersad_default_logo);

        final LinearLayout l1 = (LinearLayout) findViewById(R.id.sign_up_forms);
        final LinearLayout l2 = (LinearLayout) findViewById(R.id.sign_up_forms2);
        final LinearLayout l3 = (LinearLayout) findViewById(R.id.sign_up_forms3);
        final LinearLayout l4 = (LinearLayout) findViewById(R.id.sign_up_forms4);
        signupPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                    attemptGoToStage2();
//                    return true;
//                }
                return false;
            }
        });

        Button goToStage2Button = (Button) findViewById(R.id.gotostage2);
        goToStage2Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptGoToStage2(l1,l2);
            }
        });
        Button goToStage3Button = (Button) findViewById(R.id.signup_go_stage3);
        goToStage3Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptGoToStage3(l2,l3);
            }
        });
        Button goToStage4Button = (Button) findViewById(R.id.signup_submit);
        goToStage4Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSubmit(l3,l4);
            }
        });
        Button loginAttempt = (Button) findViewById(R.id.login_attempt);
        loginAttempt.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goTologinActivity();
                    }
                });

        mSignupFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

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

        mUsernameView.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mUsernameView.setError(null);
                    MyApp.myApi.isUsernameTaken(mUsernameView.getText().toString()).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            boolean isTaken = response.body();
                            if (isTaken){
                                mUsernameView.setError(getString(R.string.error_taken_username));
                                mUsernameView.requestFocus();

                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            mUsernameView.setError(getString(R.string.error_connection));
                            mUsernameView.requestFocus();
                            t.printStackTrace();
                        }


                    });
                }
            }
        });


    }

    private void goTologinActivity() {
        Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptGoToStage2(LinearLayout l1,LinearLayout l2) {
        if (mAuthTask != null) {
            return;
        }

        if (mUsernameView.getError()!=null){
            mUsernameView.requestFocus();
            return;
        }

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
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }else if (TextUtils.isEmpty(password)) {
            signupPasswordView.setError(getString(R.string.error_field_required));
            focusView = signupPasswordView;
            cancel = true;
        }else if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            signupPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = signupPasswordView;
            cancel = true;
        }else if (!password.equals(secondPassword)) {
            signupSecondPasswordView.setError(getString(R.string.prompt_password_match));
            focusView = signupSecondPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            bundle.putString("username",username);
            bundle.putString("email",email);
            bundle.putString("password",password);
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
            fadeInOut(l1, l2, stage1,stage2,R.drawable.stage1active, R.drawable.stage2active);
        }
    }
    private void attemptSubmit(final LinearLayout l3,final LinearLayout l4) {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        name.setError(null);
        familyName.setError(null);
        sex.setError(null);
        age.setError(null);

        // Store values at the time of the login attempt.
        String nameString = name.getText().toString();
        String familiNameString = familyName.getText().toString();
        int ageString = 0 ;
        try {
               ageString = Integer.parseInt(age.getText().toString());
        }catch (Exception e){}
        boolean sexBool = sex.isChecked(); //TODO

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.


        // Check for a valid email address.
        if (TextUtils.isEmpty(nameString)) {
            city.setError(getString(R.string.error_field_required));
            focusView = name;
            cancel = true;
        }
        else if (TextUtils.isEmpty(familiNameString)) {
            familyName.setError(getString(R.string.error_field_required));
            focusView = familyName;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            bundle.putString("name",nameString);
            bundle.putString("familyName",familiNameString);
            bundle.putInt("age",ageString);
            bundle.putBoolean("isMale",sexBool);

            String json = "  {\n" +
                    "    \"username\": \""+bundle.getString("username")+"\",\n" +
                    "    \"email\": \""+bundle.getString("email")+"\",\n" +
                    "    \"password\": \""+bundle.getString("password")+"\",\n" +
                    "    \"province\": \""+bundle.getString("state")+"\",\n" +
                    "    \"city\": \""+bundle.getString("city")+"\",\n"+
                    "    \"address\": \""+bundle.getString("address")+"\",\n" +
                    "    \"postalcode\": \""+bundle.getString("postalcode")+"\",\n" +
                    "    \"phone\": \""+bundle.getString("phone")+"\",\n" +
                    "    \"is_benefactor\": "+bundle.getBoolean("benefactor")+",\n" +
                    "    \"is_charity\": "+bundle.getBoolean("needing")+",\n" +
                    "    \"first_name\": \""+bundle.getString("name")+"\",\n" +
                    "    \"last_name\": \""+bundle.getString("familyName")+"\",\n" +
                    "    \"age\": "+bundle.getInt("age")+",\n" +
                    "    \"gender\": "+(!bundle.getBoolean("isMale")?"\"male\"":"\"female\"")+"\n" +
                    "      }";
            final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),json);
            MyApp.myApi.signUpPost(requestBody).enqueue(new Callback<LoginPost[]>() {
                @Override
                public void onResponse(Call<LoginPost[]> call, Response<LoginPost[]> response) {
                        fadeInOut(l3, l4,stage1,stage2,R.drawable.stage1active,R.drawable.stage2active);
                }

                @Override
                public void onFailure(Call<LoginPost[]> call, Throwable t) {
                    showProgress(false);
                    familyName.setError(getString(R.string.error_connection));
                    familyName.requestFocus();
                    t.printStackTrace();
                }
            });


        }
    }



    private void attemptGoToStage3(LinearLayout l3,LinearLayout l4) {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        city.setError(null);
        state.setError(null);
        address.setError(null);
        benefactor.setError(null);
        needing.setError(null);


        // Store values at the time of the login attempt.
        String cityString = city.getText().toString();
        String stateString = state.getText().toString();
        String addressString = address.getText().toString();
        boolean benefactorBool = benefactor.isChecked();
        boolean needingBool = needing.isChecked();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.


        // Check for a valid email address.
        if (TextUtils.isEmpty(cityString)) {
            city.setError(getString(R.string.error_field_required));
            focusView = city;
            cancel = true;
        }
        else if (TextUtils.isEmpty(stateString)) {
            state.setError(getString(R.string.error_field_required));
            focusView = state;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

//            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
            bundle.putString("state",stateString);
            bundle.putString("address",addressString);
            bundle.putString("city",cityString);
            bundle.putBoolean("benefactor",benefactorBool);
            bundle.putBoolean("needing",needingBool);
            bundle.putString("phone",phone.getText().toString());
            bundle.putString("postalcode",postalcode.getText().toString());
            fadeInOut(l3, l4,stage2,stage3,R.drawable.stage2active,R.drawable.stage3active);
        }
    }

    private void fadeInOut(final LinearLayout l1, final LinearLayout l2, final TextView stage1, final TextView stage2, final int drawable1,final int drawable2) {
        l1.animate().alpha(0f).setDuration(1000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                l1.setVisibility(View.GONE);
                l2.setVisibility(View.VISIBLE);
                l2.setAlpha(0f);
                l2.animate().alpha(1f).setDuration(1000);
                stage1.setBackground(ResourcesCompat.getDrawable(getResources(), drawable1, null));
                stage2.setBackground(ResourcesCompat.getDrawable(getResources(), drawable2, null));


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSignupFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSignupFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSignupFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mSignupFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(SignUpActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return false;
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                signupPasswordView.setError(getString(R.string.error_incorrect_password));
                signupPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

