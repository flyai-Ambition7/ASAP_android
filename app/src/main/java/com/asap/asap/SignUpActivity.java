package com.asap.asap;

import static android.content.ContentValues.TAG;
import static com.asap.asap.MainActivity.myAPI;

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


public class SignUpActivity extends AppCompatActivity {
    EditText signUpUserNameEditText, signUpPasswordEditText, signUpEmailEditText, signUpMobileEditText;
    Button signUpButton;
    String username, password, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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
                phone = signUpMobileEditText.getText().toString();

                if (username.length()==0 || password.length()==0 || email.length()==0 || phone.length()==0){
                    // 공백 확인
                    Toast.makeText(SignUpActivity.this  ,"빈칸 없이 제대로 입력해주세요", Toast.LENGTH_SHORT).show();

                }else{
                    // 빈칸 없으면 rest api로 확인 과정 필요
                    if (signUp()){
                        // 확인되었으면 로그인 페이지로
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }else{
                        // 중복이거나 등의 이유로 되지 않을 경우 다시 입력 받게 함
                        Toast.makeText(SignUpActivity.this  ,"현재 사용 불가능해서 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private boolean signUp(){
        final boolean[] isSignUp = {false};
        Log.d(TAG,"POST");
        SignUpItem item = new SignUpItem();
        item.setUsername("abcd1234");
        item.setPassword("yujin7894@");
        item.setEmail("yujin45@sookmyung.ac.kr");
        item.setPhone("010-8951-1834");


        Call<SignUpItem> postCall = myAPI.post_signup(item);
        postCall.enqueue(new Callback<SignUpItem>() {

            @Override
            public void onResponse(Call<SignUpItem> call, Response<SignUpItem> response) {
                if(response.isSuccessful()){
                    Log.d(TAG,"등록 완료");
                    isSignUp[0] = true;
                }else {
                    Log.d(TAG,"Status Code : " + response.code());
                    Log.d(TAG,response.errorBody().toString());
                    Log.d(TAG,call.request().body().toString());
                    isSignUp[0] = false;
                }
            }

            @Override
            public void onFailure(Call<SignUpItem> call, Throwable t) {
                Log.d(TAG,"Fail msg : " + t.getMessage());
                isSignUp[0] = false;
            }
        });
        ////
        return isSignUp[0];
    }

}