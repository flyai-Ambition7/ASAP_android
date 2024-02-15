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


                    Intent intent = new Intent(TextUpload2Activity.this, ResultActivity.class);
                    startActivity(intent);
                } else {
                    // 빈 값이 있으면 Toast 메시지 표시
                    Toast.makeText(TextUpload2Activity.this, "모든 필수 입력란을 채워주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}