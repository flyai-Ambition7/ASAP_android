package com.asap.asap;

import static android.content.ContentValues.TAG;

import static com.asap.asap.MainActivity.myAPI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

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
    String imageUriString;
    Uri imageUri;
    Bitmap bitmap;
    String base64Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_upload2);

        // Intent에서 전달된 이미지 URI 가져오기
        imageUriString = getIntent().getStringExtra("imageUri");
        imageUri = Uri.parse(imageUriString);

        
        // 객체 생성
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
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Uri를 Bitmap으로 변환
                bitmap = getBitmapFromUri(imageUri);

                // Bitmap을 Base64로 변환
                base64Image = ImageUtils.bitmapToBase64(bitmap);
                Log.d("최종?!", base64Image);

                Log.d("이미지 변환된 것 확인 ------", base64Image);
                // EditText에서 값을 가져옴
                productName = productNameEditText.getText().toString().trim();
                price = priceEditText.getText().toString().trim();
                info = infoEditText.getText().toString().trim();
                time = timeEditText.getText().toString().trim();
                where = whereEditText.getText().toString().trim();
                storePhone = storePhoneEditText.getText().toString().trim();

                // 필수 입력란이 채워져 있는지 확인
                if (!productName.isEmpty() && !price.isEmpty() && !info.isEmpty()) {
                    if (restAPI()){
                        Intent intent = new Intent(TextUpload2Activity.this, ResultActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }


                } else {
                    // 빈 값이 있으면 Toast 메시지 표시
                    Toast.makeText(TextUpload2Activity.this, "모든 필수 입력란을 채워주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean restAPI(){
            // rest api
            ///////////////////////////////////////
            final boolean[] isNewMenuInput = {false};
            Log.d(TAG,"POST");
            NewMenuInputItem item = new NewMenuInputItem();
            //item.setImage("테스트 이미지"); // 이후 추가
            item.setImage(base64Image); // base64로 변형한 이미지 전달

            
            item.setStore_name("스토어 이름");
            item.setPurpose("목적");
            item.setResult_type("결과물 형태");
            item.setTheme("테마");
            item.setProduct_name("상품명");
            item.setPrice(5000);
            item.setDescription("악!!!!!!!!!!!!!!!!!");
            item.setBusiness_hours("24시");
            item.setLocation("위치");
            item.setContact("연락처");

            Call<NewMenuInputItem> postCall = myAPI.post_new_menu_input(item);
            postCall.enqueue(new Callback<NewMenuInputItem>() {

                @Override
                public void onResponse(Call<NewMenuInputItem> call, Response<NewMenuInputItem> response) {
                    if(response.isSuccessful()){
                        Log.d(TAG,"등록 완료");
                        isNewMenuInput[0] = true;
                    }else {
                        Log.d(TAG,"Status Code : " + response.code());
                        Log.d(TAG,response.errorBody().toString());
                        Log.d(TAG,call.request().body().toString());
                        isNewMenuInput[0] = false;
                    }
                }

                @Override
                public void onFailure(Call<NewMenuInputItem> call, Throwable t) {
                    Log.d(TAG,"Fail msg : " + t.getMessage());
                    isNewMenuInput[0] = false;
                }
            });
            ////
            return isNewMenuInput[0];
        }

    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            // Uri에서 InputStream을 통해 Bitmap으로 변환
            InputStream imageStream = getContentResolver().openInputStream(uri);
            return BitmapFactory.decodeStream(imageStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}