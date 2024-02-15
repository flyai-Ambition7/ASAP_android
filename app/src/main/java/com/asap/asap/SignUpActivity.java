package com.asap.asap;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {
    EditText signUpUserNameEditText, signUpPasswordEditText, signUpEmailEditText, signUpMobileEditText;
    Button signUpButton;

    String username, password, email, mobile;

    private final String BASE_URL = "https://1e15-203-236-8-208.ngrok-free.app";
    private MyAPI mMyAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ////
        initMyAPI(BASE_URL);
        ////

        // 객체 가져오기
        signUpUserNameEditText = findViewById(R.id.signUpUserNameEditText);
        signUpPasswordEditText = findViewById(R.id.signUpPasswordEditText);
        signUpEmailEditText = findViewById(R.id.signUpEmailEditText);
        signUpMobileEditText = findViewById(R.id.signUpMobileEditText);
        signUpButton = findViewById(R.id.signUpButton);

        // 가입 버튼 누르면 restAPI로 확인 과정 필요
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = signUpUserNameEditText.getText().toString();
                password = signUpPasswordEditText.getText().toString();
                email = signUpEmailEditText.getText().toString();
                mobile = signUpMobileEditText.getText().toString();

                if (username.length()==0 || password.length()==0 || email.length()==0 || mobile.length()==0){
                    // 공백 확인
                    Toast.makeText(SignUpActivity.this  ,"빈칸 없이 제대로 입력해주세요", Toast.LENGTH_SHORT).show();

                }else{
                    // 빈칸 없으면 rest api로 확인 과정 필요
                    if (restAPI()){
                        // 확인되었으면 로그인 페이지로
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }else{
                        // 중복이거나 등의 이유로 되지 않을 경우 다시 입력 받게 함
                        Toast.makeText(SignUpActivity.this  ,"현재 사용 불가능해서 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private boolean restAPI(){
        // rest api
        ///////////////////////////////////////
        /*
        Log.d(TAG,"POST");
        SignUpItem item = new SignUpItem();

                    item.setUsername(username);
                    item.setPassword(password);
                    item.setEmail(email);
                    item.setMobile(mobile);

        Call<SignUpItem> postCall = mMyAPI.post_posts(item);
        postCall.enqueue(new Callback<SignUpItem>() {
            @Override
            public void onResponse(Call<SignUpItem> call, Response<SignUpItem> response) {
                if(response.isSuccessful()){
                    Log.d(TAG,"등록 완료");
                }else {
                    Log.d(TAG,"Status Code : " + response.code());
                    Log.d(TAG,response.errorBody().toString());
                    Log.d(TAG,call.request().body().toString());
                }
            }

            @Override
            public void onFailure(Call<SignUpItem> call, Throwable t) {
                Log.d(TAG,"Fail msg : " + t.getMessage());
            }
        });




           */
        ///////////////////////////////////////

        return true;
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