package com.asap.asap;

import static android.content.ContentValues.TAG;
import static com.asap.asap.MainActivity.myAPI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingActivity extends AppCompatActivity {
    String imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

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
                        // 목록이 비어있지 않으면 마지막 아이템을 사용
                        ImageResultItem latestImage = mList.get(mList.size() - 1);
                        // latestImage를 사용하여 필요한 로직 수행
                        Log.d("받아온 데이터", latestImage.getImage());
                        // 예시로 로그에 출력하는 부분
                        Log.d(TAG, "Latest Image URL: " + latestImage.getImage().toString());
                        imageUrl = latestImage.getImage();
                        Log.d("url이다", imageUrl);
                        Intent intent = new Intent(LoadingActivity.this, ResultActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        intent.putExtra("imageUrl", imageUrl);
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
}