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


        initAPI(BASE_URL);
        Log.d(TAG,"GET");
        Call<List<ImageResultItem>> getCall = myAPI.get_image_result();
        getCall.enqueue(new Callback<List<ImageResultItem>>() {
            @Override
            public void onResponse(Call<List<ImageResultItem>> call, Response<List<ImageResultItem>> response) {
                if( response.isSuccessful()){
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
                        Log.d(TAG, "Image list is empty.");
                    }
                }else {
                    Log.d(TAG,"Status Code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ImageResultItem>> call, Throwable t) {
                Log.d(TAG,"Fail msg : " + t.getMessage());
            }
        });


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