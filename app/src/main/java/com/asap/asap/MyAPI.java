package com.asap.asap;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyAPI {

    // 회원가입 API
    @POST("/users/signup/")
    Call<SignUpItem> post_signup(@Body SignUpItem post);
    @GET("/users/signup/")
    Call<List<SignUpItem>> get_signup();

    // 로그인 API
    @POST("/users/login/")
    Call<LoginItem> post_login(@Body LoginItem post);
    @GET("/users/login/")
    Call<List<LoginItem>> get_login();

    @GET("/users/login/{pk}/")
    Call<LoginItem> get_login_pk(@Path("pk") int pk);

    // 로그아웃 api

    // 신메뉴 API : 신메뉴 데이터 입력 new_menu_input
    @POST("/asap/item-info/")
    Call<NewMenuInputItem> post_new_menu_input(@Body NewMenuInputItem post);
    @GET("/asap/item-info/")
    Call<List<NewMenuInputItem>> get_new_menu_input();

    @GET("/asap/item-info/{pk}/")
    Call<NewMenuInputItem> get_new_menu_input_pk(@Path("pk") int pk);

    // 최종 결과물 API : 홍보물 출력 image_result
    @POST("/asap/result-data/")
    Call<ImageResultItem> post_image_result(@Body ImageResultItem post);
    @GET("/asap/result-data/")
    Call<List<ImageResultItem>> get_image_result();

    @GET("/asap/result-data/{pk}/")
    Call<ImageResultItem> get_image_result(@Path("pk") int pk);



}
