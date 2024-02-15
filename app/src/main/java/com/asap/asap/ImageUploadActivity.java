package com.asap.asap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ImageUploadActivity extends AppCompatActivity {
    ImageView imageUploadImageView;
    ImageButton leftButton, rightButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    boolean isImageChanged = false;
    // 이미지 변경 관련
    Uri selectedImageUri;
    Bitmap bitmap;
    String base64Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);
        // 객체 가져오기
        imageUploadImageView = findViewById(R.id.imageUploadImageView);
        leftButton = findViewById(R.id.imageUploadLeftImageButton);
        rightButton = findViewById(R.id.imageUploadRightImageButton);

        // 버튼 이동 처리
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(ImageUploadActivity.this, LoginActivity.class);
               // intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
               // startActivity(intent);
                // 왼쪽 버튼 막아두기 = 로그인, 회원가입 화면으로 못가게
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이미지 넣었으면 넘어가게 함
                if (isImageChanged) {
                    Intent intent = new Intent(ImageUploadActivity.this, TextUpload1Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    // 이미지 비트맵 함께 넘기기
                    intent.putExtra("base64Image", base64Image);
                    startActivity(intent);
                } else {
                    Toast.makeText(ImageUploadActivity.this, "이미지를 넣어주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 이미지 뷰 클릭하면 갤러리 연결해서 이미지 넣을 수 있게 함
        imageUploadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자 갤러리 열어서 이미지 삽입하게 함
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            // Uri 가져오기
            selectedImageUri = data.getData();
            // 현재 이미지 뷰 교체
            imageUploadImageView.setImageURI(selectedImageUri);
            // 이미지가 변경되었음을 표시
            isImageChanged = true;
            // 이후 저 uri를 어떻게 서버로 보낼지도 고려해서 짜두기
            //

            // Uri를 Bitmap으로 변환
            bitmap = getBitmapFromUri(selectedImageUri);

            // Bitmap을 Base64로 변환
            base64Image = ImageUtils.bitmapToBase64(bitmap);

            Log.d("이미지 변환된 것 확인 ------", base64Image);
        }
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