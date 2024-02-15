package com.asap.asap;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyAPI {
    @POST("/posts/")
    Call<PostItem> post_posts(@Body PostItem post);
    @GET("/posts/")
    Call<List<PostItem>> get_posts();

    @GET("/posts/{pk}/")
    Call<PostItem> get_post_pk(@Path("pk") int pk);
    //
    @POST("/asap/signup/")
    Call<SignUpItem> post_signup(@Body SignUpItem post);
    @GET("/asap/signup/")
    Call<List<SignUpItem>> get_signup();

    @GET("/asap/signup/{pk}/")
    Call<SignUpItem> get_signup_pk(@Path("pk") int pk);


}
