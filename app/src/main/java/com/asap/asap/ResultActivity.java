package com.asap.asap;

import static android.content.ContentValues.TAG;
import static com.asap.asap.MainActivity.myAPI;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ResultActivity extends AppCompatActivity {
    ImageView resultImageView;
    Button homeButton, saveButton;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultImageView = findViewById(R.id.resultImageView);
        homeButton = findViewById(R.id.homeButton);
        saveButton = findViewById(R.id.saveButton);

        /////////////

        // rest api로 이미지 받아오는 부분 필요
        // 받아올 동안 로딩 화면 띄우던 뭔 작업 필요
        // 대기중 이미지 띄워뒀다가 나중에 서버에서 왔다고 하면 그때 view 업데이트 좋을듯함
        showResultImage();
        //////////

        // 버튼 동작
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, ImageUploadActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageToGallery(); // imageView에 있는 이미지를 저장함
                // rest api로 가져온 이미지를 그대로 저장하면 되겠지만 일단 imageview에서 가져와서 저장하는 것으로 짜둠


                //Toast.makeText(ResultActivity.this, "저장 버튼 클릭", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveImageToGallery() {
        // ImageView에서 Drawable을 가져와서 Bitmap으로 변환
        BitmapDrawable drawable = (BitmapDrawable) resultImageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        // 내 갤러리에 저장할 파일 생성
        String fileName = "ASAP_Result_" + System.currentTimeMillis() + ".png";
        File galleryFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), fileName);

        try {
            // 파일에 비트맵 저장
            OutputStream outputStream = new FileOutputStream(galleryFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            // 갤러리에 추가하기
            MediaStore.Images.Media.insertImage(getContentResolver(), galleryFile.getAbsolutePath(), galleryFile.getName(), galleryFile.getName());

            // 성공 메시지 토스트로 표시
            Toast.makeText(ResultActivity.this, "이미지가 갤러리에 저장되었습니다.", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            // 실패 메시지 토스트로 표시
            Toast.makeText(ResultActivity.this, "이미지 저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void showResultImage() {
        Log.d(TAG, "GET");
        Call<List<NewMenuInputItem>> getCall = myAPI.get_new_menu_input();
        getCall.enqueue(new Callback<List<NewMenuInputItem>>() {
            @Override
            public void onResponse(Call<List<NewMenuInputItem>> call, Response<List<NewMenuInputItem>> response) {
                if (response.isSuccessful()) {
                    List<NewMenuInputItem> mList = response.body();
                    NewMenuInputItem item = mList.get(mList.size() - 1); // 마지막 항목 가져오기
                    String base64Image = item.getImage();
                    // Base64 문자열을 디코딩하여 Bitmap으로 변환
                    Bitmap decodedBitmap = decodeBase64ToBitmap(base64Image);
                    // Bitmap을 ImageView에 설정
                    resultImageView.setImageBitmap(decodedBitmap);
                } else {
                    Log.d(TAG, "Status Code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<NewMenuInputItem>> call, Throwable t) {
                Log.d(TAG, "Fail msg : " + t.getMessage());
            }
        });

    }

    private Bitmap decodeBase64ToBitmap(String base64Image) {
        try {
            // Base64 문자열을 디코딩하여 byte 배열로 변환
            byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);

            // byte 배열을 Bitmap으로 변환
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            resultImageView.setImageBitmap(decodedBitmap);
            return decodedBitmap;
        } catch (IllegalArgumentException e) {
            // 잘못된 Base64 문자열이 들어왔을 경우에 대한 예외처리
            Log.d(TAG, "잘못된 Base64 문자열이 들어옴");
            e.printStackTrace();
            return null;
        }

    }
}