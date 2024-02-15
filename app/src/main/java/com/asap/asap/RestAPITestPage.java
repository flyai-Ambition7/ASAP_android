package com.asap.asap;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAPITestPage extends AppCompatActivity {
    EditText titleE, textE, putE, laeE;
    Button getB, postB;
    String title, text, put, lae;
    TextView getT;
    /////////////////
    private final String BASE_URL = "https://1e15-203-236-8-208.ngrok-free.app";
    private MyAPI mMyAPI;
    /////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_apitest_page);
        /////
        titleE = findViewById(R.id.title);
        textE = findViewById(R.id.text);
        putE = findViewById(R.id.put);
        laeE = findViewById(R.id.lae);

        getB = findViewById(R.id.get);
        postB = findViewById(R.id.post);

        getT = findViewById(R.id.getResult);
        /////
        ////
        initMyAPI(BASE_URL);
        ////
        postB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleE.getText().toString();
                text = textE.getText().toString();
                put = putE.getText().toString();
                lae = laeE.getText().toString();

                // rest api
                ///////////////////////////////////////
                Log.d(TAG,"POST");
                PostItem item = new PostItem();
                item.setTitle(title);
                item.setText(text);
                item.setPut(put);
                item.setLae(lae);
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
            }
        });


        getB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"GET");
                Call<List<PostItem>> getCall = mMyAPI.get_posts();
                getCall.enqueue(new Callback<List<PostItem>>() {
                    @Override
                    public void onResponse(Call<List<PostItem>> call, Response<List<PostItem>> response) {
                        if( response.isSuccessful()){
                            List<PostItem> mList = response.body();
                            String result ="";
                            PostItem item = mList.get(mList.size() - 1);
                            result += "title : " + item.getTitle() + " text: " + item.getText() +
                                    " put: "+ item.getPut() + " lae: " + item.getLae() + "\n";
                            /*
                            for( PostItem item : mList){
                                result += "title : " + item.getTitle() + " text: " + item.getText() +
                                        " put: "+ item.getPut() + " lae: " + item.getLae() + "\n";
                            }*/
                            getT.setText(result);
                        }else {
                            Log.d(TAG,"Status Code : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PostItem>> call, Throwable t) {
                        Log.d(TAG,"Fail msg : " + t.getMessage());
                    }
                });
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