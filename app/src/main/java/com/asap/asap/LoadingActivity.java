package com.asap.asap;

import static android.content.ContentValues.TAG;
//import static com.asap.asap.MainActivity.myAPI;

import static com.asap.asap.MainActivity.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoadingActivity extends AppCompatActivity {
    String imageUrl;
    //public final String BASE_URL = "https://7818-203-236-8-208.ngrok-free.app";
    public MyAPI myAPI;

    int  resultImageUrlListCount = 1; // 총 생성 이미지 개수
    ArrayList<String> resultImageUrlList = new ArrayList<>();
    // 총 생성된 이미지 url 들어 있는 리스트

    ProgressBar progressBar1, progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        progressBar1 = findViewById(R.id.progressBar1);

        // 프로그레스바 보이기
        progressBar1.setVisibility(View.VISIBLE);

        // 프로그레스바 안 보이게
       // progressBar1.setVisibility(View.GONE);
///////////////
        initAPI(BASE_URL);
        //////////
        // Intent에서 데이터 추출
        Intent intent = getIntent();
        if (intent != null) {
            NewMenuInputItem receivedItem = (NewMenuInputItem) intent.getSerializableExtra("newMenuInputItem");

            // 받아온 데이터 사용
            if (receivedItem != null) {
                Log.d("LoadingActivity", "Received Data: " + receivedItem.getStore_name() + " " +
                        receivedItem.getPurpose() + " " + receivedItem.getResult_type() + " " +
                        receivedItem.getTheme() + " " + receivedItem.getProduct_name() + " " +
                        receivedItem.getPrice() + " " + receivedItem.getDescription() + " " +
                        receivedItem.getBusiness_hours() + " " + receivedItem.getLocation() + " " +
                        receivedItem.getContact());
                restAPIPost(receivedItem);
            }

        }
        //////////////



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

    public boolean restAPIGet(){
        final boolean[] isResult = {false};
        Log.d(TAG,"GET");
        Call<List<ImageResultItem>> getCall = myAPI.get_image_result();
        getCall.enqueue(new Callback<List<ImageResultItem>>() {
            @Override
            public void onResponse(Call<List<ImageResultItem>> call, Response<List<ImageResultItem>> response) {
                if( response.isSuccessful()){
                    isResult[0] = true;
                    List<ImageResultItem> mList = response.body();
                    //첫 번째 아이템을 사용
                    //ImageResultItem latestImage = mList.get(0);
                    if (mList != null && !mList.isEmpty()) {
                        int listSize = mList.size();
                        int startIndex = Math.max(listSize - resultImageUrlListCount, 0);
                        List<ImageResultItem> selectedItems = mList.subList(startIndex, listSize);

                        for (ImageResultItem item : selectedItems) {
                            Log.d("받아온 데이터", item.getResult_image_url());
                            resultImageUrlList.add(item.getResult_image_url());
                        }
                        /*
                        // 목록이 비어있지 않으면 마지막 아이템을 사용
                        ImageResultItem latestImage = mList.get(mList.size() - 1);
                        // latestImage를 사용하여 필요한 로직 수행
                        Log.d("받아온 데이터", latestImage.getResult_image_url());
                        // 예시로 로그에 출력하는 부분
                        Log.d(TAG, "Latest Image URL: " + latestImage.getResult_image_url().toString());
                        imageUrl = latestImage.getResult_image_url();
                        Log.d("url이다", imageUrl);
                        */

                        //Intent intent = new Intent(LoadingActivity.this, ResultActivity.class);
                        Intent intent = new Intent(LoadingActivity.this, MultiResultImageActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        //intent.putExtra("imageUrl", imageUrl);
                        intent.putExtra("resultImageUrlList", resultImageUrlList);
                        startActivity(intent);

                    } else {
                        isResult[0] = false;
                        Log.d(TAG, "Image list is empty.");
                    }
                }else {
                    isResult[0] = false;
                    Log.d(TAG,"Status Code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ImageResultItem>> call, Throwable t) {
                isResult[0] = false;
                Log.d(TAG,"Fail msg : " + t.getMessage());
            }
        });
        Log.d("결과 ", String.valueOf(isResult[0]));
        return isResult[0];
    }
    public boolean restAPIPost(NewMenuInputItem item) {
        // rest api
        ///////////////////////////////////////
        final boolean[] isNewMenuInput = {false};
        Log.d(TAG, "POST");

        Call<NewMenuInputItem> postCall = myAPI.post_new_menu_input(item);
        postCall.enqueue(new Callback<NewMenuInputItem>() {

            @Override
            public void onResponse(Call<NewMenuInputItem> call, Response<NewMenuInputItem> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "등록 완료");
                    isNewMenuInput[0] = true;
                    Log.d(TAG, "Status Code 성공 : " + response.code());
                    Toast.makeText(LoadingActivity.this, "Status Code성공 : " + response.code(), Toast.LENGTH_SHORT).show();
                 
                    //get 시작
                    //
                    restAPIGet();

                } else {
                    Toast.makeText(LoadingActivity.this,"Status 못받 : " +  response.code(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(LoadingActivity.this,"Status Code on Failure: " + call.execute().code(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Status Code on Failure: " + call.execute().code());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    Toast.makeText(LoadingActivity.this,"서버 응답 없음 Fail msg : " + t.getMessage(), Toast.LENGTH_SHORT).show();
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

}