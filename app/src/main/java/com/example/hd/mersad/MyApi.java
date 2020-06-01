package com.example.hd.mersad;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by hd on 2018/7/30 AD.
 */

public interface MyApi {
    @POST("accounts/android-login")
    Call<LoginPost[]> postLoginCredentials(@Body RequestBody requestBody);
    @POST("accounts/android-signup")
    Call<LoginPost[]> signUpPost(@Body RequestBody requestBody);
    @POST("/accounts/android-searchnaghdi")
    Call<SearchRecord[]> searchNPost(@Body SearchNaghdiQuery searchNaghdiQuery);
    @POST("/accounts/android-search_gh_naghdi")
    Call<SearchRecord[]> searchGhPost(@Body SearchGhNaghdiQuery searchGhNaghdiQuery);
    @GET("/accounts/android-all-tags")
    Call<String[]> getSkills();
    @POST("/accounts/android-get-profile")
    Call<User> getProfile(@Body String username);
    @POST("/accounts/android-update-profile")
    Call<Boolean> postProfile(@Body User user);
    @POST("/accounts/android-myprojects")
    Call<Project[]> getMyProjects(@Body String username);
    @POST("/accounts/android-notifications")
    Call<EventReports[]> events(@Body String username);
    @POST("/accounts/android-usernamecheck")
    Call<Boolean> isUsernameTaken(@Body String username);
}

