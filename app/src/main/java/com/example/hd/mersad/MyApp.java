package com.example.hd.mersad;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by hd on 2018/7/27 AD.
 */

public class MyApp extends Application {

    static MyApi myApi;
    static Retrofit retrofit;


    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSANS_BOLD.TTF")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        createMyRetrofitInstance();

    }

    private void createMyRetrofitInstance() {
        retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8000/")
//        retrofit = new Retrofit.Builder().baseUrl("http://192.168.203.219:80/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(MyApi.class);

    }

}
