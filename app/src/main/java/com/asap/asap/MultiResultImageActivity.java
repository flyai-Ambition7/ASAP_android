package com.asap.asap;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MultiResultImageActivity extends AppCompatActivity {
    private RecyclerView resultRecyclerView;
    private Button homeButton;
    private Button saveButton;
    private ArrayList<String> resultImageUrlList = new ArrayList<>();
    private ArrayList<RecyclerViewItem> RecyclerViewItemList = new ArrayList<>();
    //private ArrayList<String> resultImageUrlList;
    private MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_result_image);

        resultRecyclerView = findViewById(R.id.resultRecyclerView);
        homeButton = findViewById(R.id.homeButton);
        saveButton = findViewById(R.id.saveButton);

        // 실제 구현시 intent로 resultImageUrlList 받아오기
        //Intent intent = getIntent();
        //resultImageUrlList = (ArrayList<String>) intent.getSerializableExtra("resultImageUrlList");
        // intetn로 받아오는 것 말고 임시로 넣어보기
        resultImageUrlList.add("https://www.job-post.co.kr/news/photo/202307/81602_85141_1831.jpg");
        resultImageUrlList.add("https://감성커피.com/upload/event_01/2021_08_09/admin_XisVh_2021_08_09_14_28_55.jpg");
        resultImageUrlList.add("https://image.edaily.co.kr/images/Photo/files/NP/S/2021/02/PS21021701225.jpg");
        resultImageUrlList.add("https://www.koit.co.kr/news/photo/202307/115047_67683_3438.jpg");
        resultImageUrlList.add("https://blog.kakaocdn.net/dn/BaJw7/btqCQoDbqTw/jBm6GVMR42qaubsxA1kJKK/img.jpg");
        resultImageUrlList.add("https://marketplace.canva.com/EAFzZlVhCN0/1/0/800w/canva-%EC%BB%A4%ED%94%BC%EC%83%89-%EB%B2%A0%EC%9D%B4%EC%A7%80%EC%83%89-%EA%B9%94%EB%81%94%ED%95%9C-%EC%B9%B4%ED%8E%98-%EC%8B%A0-%EB%A9%94%EB%89%B4-%EC%9D%B8%EC%8A%A4%ED%83%80%EA%B7%B8%EB%9E%A8-%ED%8F%AC%EC%8A%A4%ED%8A%B8-odekzsrVFWo.jpg");
        resultImageUrlList.add("https://marketplace.canva.com/EAF6nXQOkK0/1/0/1600w/canva-%EA%B0%88%EC%83%89-%EC%BB%A4%ED%94%BC-%EB%A9%94%EB%89%B4-%EC%9D%B8%EC%8A%A4%ED%83%80%EA%B7%B8%EB%9E%A8-%EA%B2%8C%EC%8B%9C%EB%AC%BC-lGo4asC3iOY.jpg");
        resultImageUrlList.add("https://img.freepik.com/free-psd/special-coffee-menu-sale-promotional-web-banner-or-instagram-banner-template_505751-3240.jpg");

        // 객체 생성 후 전달
        for (String resultUrl : resultImageUrlList) {
            RecyclerViewItemList.add(new RecyclerViewItem(resultUrl, false));
        }
        
        adapter = new MyRecyclerViewAdapter(this, RecyclerViewItemList);
        resultRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        resultRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2,fromDpToPx(16f)));
        resultRecyclerView.setAdapter(adapter);

        Log.d("Glide 라이브러리 사용 ", "0000000000000000000000000");

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MultiResultImageActivity.this, ImageUploadActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (RecyclerViewItem recyclerViewItem : RecyclerViewItemList) {
                        if (recyclerViewItem.isSelected()){
                            //선택된 애들만 저장하도록 함
                            saveImageToGallery(recyclerViewItem.getImageUrl());
                        }
                    }
                      Toast.makeText(MultiResultImageActivity.this, "이미지가 갤러리에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                      Toast.makeText(MultiResultImageActivity.this, "이미지 저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void saveImageToGallery(String imageUrl) {
        GlideApp.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        // Save the Bitmap to the gallery
                        saveBitmapToGallery(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Do nothing
                    }
                });
    }

    private void saveBitmapToGallery(Bitmap bitmap) {
        String fileName = "ASAP_Result_" + System.currentTimeMillis() + ".png";
        File galleryFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), fileName);

        try {
            OutputStream outputStream = new FileOutputStream(galleryFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            // Insert the image into the MediaStore
            MediaStore.Images.Media.insertImage(getContentResolver(), galleryFile.getAbsolutePath(), galleryFile.getName(), galleryFile.getName());

          //  Toast.makeText(MultiResultImageActivity.this, "이미지가 갤러리에 저장되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
          //  Toast.makeText(MultiResultImageActivity.this, "이미지 저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public static int fromDpToPx(float dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
    //////////////////////////////////////////////////////

/*
    private void saveImageToGallery(RecyclerViewItem recyclerViewItem) {

        BitmapDrawable drawable = (BitmapDrawable) resultImageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        String fileName = "ASAP_Result_" + System.currentTimeMillis() + ".png";
        File galleryFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), fileName);

        try {
            OutputStream outputStream = new FileOutputStream(galleryFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            MediaStore.Images.Media.insertImage(getContentResolver(), galleryFile.getAbsolutePath(), galleryFile.getName(), galleryFile.getName());

            Toast.makeText(MultiResultImageActivity.this, "이미지가 갤러리에 저장되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MultiResultImageActivity.this, "이미지 저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap decodeBase64ToBitmap(String base64Image) {
        try {
            byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
          //  resultImageView.setImageBitmap(decodedBitmap);
            return decodedBitmap;
        } catch (IllegalArgumentException e) {
            Log.d(ContentValues.TAG, "잘못된 Base64 문자열이 들어옴");
            e.printStackTrace();
            return null;
        }
    }*/
//@Override
//public void onBackPressed() { super.onBackPressed();}
}
