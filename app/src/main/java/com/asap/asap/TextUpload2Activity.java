package com.asap.asap;

import static android.content.ContentValues.TAG;

import static com.asap.asap.MainActivity.BASE_URL;

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
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TextUpload2Activity extends AppCompatActivity {
    ImageButton leftButton, rightButton;
    EditText productNameEditText, priceEditText, infoEditText, timeEditText, whereEditText, storePhoneEditText;

    // 입력 내용
    public static String productName, price, info; // 필수
    public static String time, where, storePhone; // 선택

    public static String storeName, theme, purpose, resultForm;
    String imageUriString;
     Uri imageUri;
    Bitmap bitmap;
    public static String base64Image;
    public MyAPI myAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_upload2);
        initAPI(BASE_URL);
        // Intent에서 전달된 이미지 URI 가져오기
        imageUriString = getIntent().getStringExtra("imageUri");
        imageUri = Uri.parse(imageUriString);
        // 나머지 입력 항목들도 가져오기
        storeName = getIntent().getStringExtra("storeName");
        theme = getIntent().getStringExtra("storeName");
        purpose = getIntent().getStringExtra("purpose");
        resultForm = getIntent().getStringExtra("resultForm");


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
                Log.d("입력란 채우는 중", "입력란~~~~~~~~~~~~~~~~~~~~");

                // 필수 입력란이 채워져 있는지 확인
                if (!productName.isEmpty() && !price.isEmpty() && !info.isEmpty()) {
                    if (time.isEmpty()){ time = "미입력";}
                    if (where.isEmpty()){where = "미입력";}
                    if (storePhone.isEmpty()){storePhone= "미입력";}
                    Log.d("입력란 다 채워지니", "입력란~~~~~~~~~~~~~~~~~~~~");

                   loadingIntent(); // 이거 사용해야 함
                   // restAPI();
                } else {
                    // 빈 값이 있으면 Toast 메시지 표시
                    Toast.makeText(TextUpload2Activity.this, "모든 필수 입력란을 채워주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loadingIntent(){
        // Intent 생성
        Intent intent = new Intent(this, LoadingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
    public boolean restAPI() {
        final boolean[] isNewMenuInput = {false};
        Log.d(TAG, "POST");
        NewMenuInputItem item = new NewMenuInputItem();
        Log.d("NewMenuInputItem item", "입력란~~~~~~~~~~~~~~~~~~~~");
        //item.setImage("테스트 이미지"); // 이후 추가
        item.setImage(base64Image); // base64로 변형한 이미지 전달 민수 테스트
        item.setStore_name(storeName);
        item.setPurpose(purpose);
        item.setResult_type(resultForm);
        item.setTheme(theme);
        item.setProduct_name(productName);
        //item.setPrice(Integer.parseInt(price)); // 민수
        item.setPrice(price); // 지안
        item.setDescription(info);
        item.setBusiness_hours(time);
        item.setLocation(where);
        item.setContact(storePhone);
        Log.d("TextUpload2Activity intent 잘 받아옴", storeName + purpose + resultForm+theme+productName+price+info+time+where+storePhone);

        Call<NewMenuInputItem> postCall = myAPI.post_new_menu_input(item);
        postCall.enqueue(new Callback<NewMenuInputItem>() {

            @Override
            public void onResponse(Call<NewMenuInputItem> call, Response<NewMenuInputItem> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "등록 완료");
                    isNewMenuInput[0] = true;
                    Log.d(TAG, "Status Code 성공 : " + response.code());
                    Toast.makeText(TextUpload2Activity.this, "Status Code성공 : " + response.code(), Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(TextUpload2Activity.this, ResultActivity.class);
                    Intent intent = new Intent(TextUpload2Activity.this, LoadingActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);

                } else {
                    Toast.makeText(TextUpload2Activity.this,"Status 못받 : " +  response.code(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Status Code 못받 : " + response.code());
                    Log.d(TAG, response.errorBody().toString());
                    Log.d(TAG, call.request().body().toString());
                    isNewMenuInput[0] = false;
                }
            }

            @Override
            public void onFailure(Call<NewMenuInputItem> call, Throwable t) {
                if (call != null && call.isExecuted()) {
                    // 서버 응답이 있을 때만 상태 코드 로그 출력
                    try {
                        Toast.makeText(TextUpload2Activity.this,"Status Code on Failure: " + call.execute().code(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Status Code on Failure: " + call.execute().code());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    Toast.makeText(TextUpload2Activity.this,"서버 응답 없음 Fail msg : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "서버 응답 없음 Fail msg : " + t.getMessage());
                }
                Log.d(TAG, "Fail msg : " + t.getMessage());

                isNewMenuInput[0] = false;
            }
        });
        ////
        Log.d("결과 ", String.valueOf(isNewMenuInput[0]));
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


    public void initAPI(String baseUrl) {
        // timeout setting 해주기
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .build();
        Log.d(TAG,"initAPI : " + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //.addConverterFactory(new NullOnEmptyConverterFactory())
        myAPI = retrofit.create(MyAPI.class);
    }


}