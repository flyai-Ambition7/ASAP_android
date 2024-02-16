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
    /*
    // 테스트 용도
    @POST("/posts/")
    Call<PostItem> post_posts(@Body PostItem post);F
    @GET("@/posts/")
    Call<List<PostItem>> get_posts();

    @GET("/posts/{pk}/")
    Call<PostItem> get_post_pk(@Path("pk") int pk);

    @POST("@api/login")
    Call<PostItem> post_login2(@Body PostItem post);
    @GET("@api/login")
    Call<List<PostItem>> get_login2();

    @GET("@api/login/{pk}/")
    Call<PostItem> get_login2_pk(@Path("pk") int pk);

    @POST("/new_menu_input/")
    Call<PostItem> post_new_menu_input2(@Body PostItem post);
    @GET("/new_menu_input/")
    Call<List<PostItem>> get_new_menu_input2();

    @GET("/new_menu_input/{pk}/")
    Call<PostItem> get_new_menu_input2_pk(@Path("pk") int pk);

    */


    // 회원가입 API
    @POST("/asap/signup/")
    Call<SignUpItem> post_signup(@Body SignUpItem post);
    @GET("/asap/signup/")
    Call<List<SignUpItem>> get_signup();

   // @GET("/asap/signup/{pk}/")
   // Call<SignUpItem> get_signup_pk(@Path("pk") int pk);

    // 로그인 API
    @POST("/asap/login/")
    Call<LoginItem> post_login(@Body LoginItem post);
    @GET("/asap/login/")
    Call<List<LoginItem>> get_login();

    @GET("/asap/login/{pk}/")
    Call<LoginItem> get_login_pk(@Path("pk") int pk);

    // 신메뉴 API : 신메뉴 데이터 입력 new_menu_input
    @POST("/asap/new_menu_input/")
    Call<NewMenuInputItem> post_new_menu_input(@Body NewMenuInputItem post);
    @GET("/asap/new_menu_input/")
    Call<List<NewMenuInputItem>> get_new_menu_input();

    @GET("/asap/new_menu_input/{pk}/")
    Call<NewMenuInputItem> get_new_menu_input_pk(@Path("pk") int pk);
    
    // 이후 ui 정해지고 input 정해지면 이벤트, 개업 api에 대해서도 추가 예정

    //

    // 최종 결과물 API : 홍보물 출력 image_result
/*
    @POST("/asap/image_result/")
    Call<ImageResultItem> post_image_result(@Body ImageResultItem post);
    @GET("/asap/image_result/")
    Call<List<ImageResultItem>> get_image_result();

    @GET("/asap/image_result/{pk}/")
    Call<ImageResultItem> get_image_result(@Path("pk") int pk);


*/
}
