package com.asap.asap;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TextUpload2Activity extends AppCompatActivity {
    ImageButton leftButton, rightButton;
    EditText productNameEditText, priceEditText, infoEditText, timeEditText, whereEditText, storePhoneEditText;

    // 입력 내용
    String productName,  price,  info, time, where, storePhone;
/////////////////
    private final String BASE_URL = "https://847e-203-236-8-208.ngrok-free.app";
    private MyAPI mMyAPI;
    /////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_upload2);

        leftButton = findViewById(R.id.textUpload2LeftImageButton);
        rightButton = findViewById(R.id.textUpload2RightImageButton);

        productNameEditText = findViewById(R.id.productNameEditText);
        priceEditText = findViewById(R.id.priceEditText);
        infoEditText = findViewById(R.id.infoEditText);
        timeEditText = findViewById(R.id.timeEditText);
        whereEditText = findViewById(R.id.whereEditText);
        storePhoneEditText = findViewById(R.id.storePhoneEditText);

        ////
        initMyAPI(BASE_URL);
        ////

        // 버튼 이동 처리
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TextUpload2Activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText에서 값을 가져옴
                productName = productNameEditText.getText().toString().trim();
                price = priceEditText.getText().toString().trim();
                info = infoEditText.getText().toString().trim();
                time = timeEditText.getText().toString().trim();
                where = whereEditText.getText().toString().trim();
                storePhone = storePhoneEditText.getText().toString().trim();

                // 필수 입력란이 채워져 있는지 확인
                if (!productName.isEmpty() && !price.isEmpty() && !info.isEmpty()) {

                    // rest api
                    ///////////////////////////////////////
                    Log.d(TAG,"POST");
                    PostItem item = new PostItem();
                    /*
                    item.setText("text 세팅");
                    item.setTitle("title 세팅");

                    item.setLoginID("로그인 ID");
                    item.setLoginPW("로그인 pw");
                    //
                    item.setUsername("회원가입 id");
                    item.setPassword("회원가입 pw");
                    item.setEmail("회원가입 email");
                    item.setMobile("회원가입 mobile");
                    // 이미지 추가
                    item.setStoreName("1. 매장명");
                    item.setPurpose("1. 목적");
                    item.setResultForm("1. 결과 폼");
                    item.setTheme("1. 테마");
                    //
                    item.setProductName("2. 상품명");
                    item.setPrice("2. 가격:");
                    item.setInfo("2 세부 정보");
                    item.setTime("2 시간");
                    item.setWhere("2.위치");
                    item.setStorePhone("2 전화번호");
                    */
                    Call<PostItem> postCall = mMyAPI.post_posts(item);
                    postCall.enqueue(new Callback<PostItem>() {
                        @Override
                        public void onResponse(Call<PostItem> call, Response<PostItem> response) {
                            if(response.isSuccessful()){
                                Log.d(TAG,"등록 완료");
                            }else {
                                Log.d(TAG,"Status Code : " + response.code());
                                Log.d(TAG,response.errorBody().toString());
                                Log.d(TAG,call.request().body().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<PostItem> call, Throwable t) {
                            Log.d(TAG,"Fail msg : " + t.getMessage());
                        }
                    });





                    ///////////////////////////////////////


                    Intent intent = new Intent(TextUpload2Activity.this, ResultActivity.class);
                    startActivity(intent);
                } else {
                    // 빈 값이 있으면 Toast 메시지 표시
                    Toast.makeText(TextUpload2Activity.this, "모든 필수 입력란을 채워주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initMyAPI(String baseUrl){

        Log.d(TAG,"initMyAPI : " + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mMyAPI = retrofit.create(MyAPI.class);
    }
}