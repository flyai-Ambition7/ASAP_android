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
    @POST("@api/posts")
    Call<PostItem> post_posts(@Body PostItem post);
    @GET("@api/posts")
    Call<List<PostItem>> get_posts();

    @GET("@api/posts/{pk}/")
    Call<PostItem> get_post_pk(@Path("pk") int pk);


    // 회원가입 API
    @POST("@api/signup")
    Call<SignUpItem> post_signup(@Body SignUpItem post);
    @GET("@api/signup")
    Call<List<SignUpItem>> get_signup();

    @GET("@api/signup/{pk}/")
    Call<SignUpItem> get_signup_pk(@Path("pk") int pk);

    // 로그인 API
    @POST("@api/login")
    Call<LoginItem> post_login(@Body LoginItem post);
    @GET("@api/login")
    Call<List<LoginItem>> get_login();

    @GET("@api/login{pk}/")
    Call<LoginItem> get_login_pk(@Path("pk") int pk);

    //



}
