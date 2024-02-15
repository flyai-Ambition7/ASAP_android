package com.asap.asap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText loginUserNameEditText, loginPasswordImageView;
    TextView singUpTextView;
    Button startButton;

    String loginID, loginPW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 객체 가져오기
        loginUserNameEditText = findViewById(R.id.loginUserNameEditText);
        loginPasswordImageView = findViewById(R.id.loginPasswordEditText);
        singUpTextView = findViewById(R.id.signUpTextView);
        startButton = findViewById(R.id.startButton);

        // 회원가입 글시 누르면 회원가입 화면으로 이동
        singUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        //시작하기 버튼 누르면 restAPI 호출 등 작업 필요 => 이후 작성
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginID = loginUserNameEditText.getText().toString();
                loginPW = loginPasswordImageView.getText().toString();
                if (loginID.length()==0 ||loginPW.length()==0){
                    // 공백일 때
                    Toast.makeText(LoginActivity.this  ,"제대로 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    //공백 아닐 때
                    // REST API로 검증하는 과정 추가 ====================
                    ///
                    //임시로 입력받기
                    if (loginID.equals("a") && loginPW.equals("a")){
                        Intent intent = new Intent(LoginActivity.this, ImageUploadActivity.class);
                        startActivity(intent);
                    }
                    ///
                }

            }
        });



    }
}