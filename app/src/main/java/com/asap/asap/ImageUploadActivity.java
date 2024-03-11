package com.asap.asap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* 카메라 촬영 관련 */
// 카메라 촬영하여 uri 얻고 제대로 화면에 표시하는 등에 필요한 것들
import androidx.core.content.FileProvider;
import android.graphics.Matrix;
import android.media.ExifInterface;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageUploadActivity extends AppCompatActivity {
    ImageView imageUploadImageView;
    ImageButton leftButton, rightButton;
    Button cameraButton, galleryButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    boolean isImageChanged = false;
    // 이미지 변경 관련
    Uri imageUri;
    // 카메라 관련
    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private String imageFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);
        // 객체 가져오기
        imageUploadImageView = findViewById(R.id.imageUploadImageView);
        leftButton = findViewById(R.id.imageUploadLeftImageButton);
        rightButton = findViewById(R.id.imageUploadRightImageButton);
        cameraButton = findViewById(R.id.cameraButton);
        galleryButton = findViewById(R.id.galleryButton);

        // 버튼 이동 처리
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    // 이미지 비트맵이나 base64로 보내면 intent 사이즈 제한에 걸림 uri로 보내기
                    intent.putExtra("imageUri",imageUri.toString());
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
               // openGallery();
                // 인텐트를 사용하여 갤러리에서 뭘 가져올건지 수행함
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1) 사용자 갤러리 열어서 이미지 삽입하게 함
                // openGallery();
                // 2) 인텐트를 사용하여 갤러리에서 뭘 가져올건지 수행함
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    if (photoFile != null) {
                        imageUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName()+".fileprovider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                    }
                }
            }
        });

    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 위에서 버튼을 눌렀을 때 이쪽으로 와서 case 1일 때로 들어가게 됨.
        // 이때 선택한 이미지 uri를 넣어주기
        switch(requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    imageUri = data.getData();
                    imageUploadImageView.setImageURI(imageUri);
                    isImageChanged = true;
                }
                break;
            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);

                    ExifInterface exif = null;
                    try {
                        exif = new ExifInterface(imageFilePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int exifOrientation;
                    int exifDegree;

                    if (exif != null) {
                        exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        exifDegree = exifOrientationToDegrees(exifOrientation);
                    } else {
                        exifDegree = 0;
                    }
                    ((ImageView)findViewById(R.id.imageUploadImageView)).setImageBitmap(rotate(bitmap, exifDegree));
                    saveImageToGallery(rotate(bitmap, exifDegree));
                    isImageChanged = true;
                    // 카메라로 촬영한 이미지의 URI를 저장
                    imageUri = Uri.fromFile(new File(imageFilePath));
                }
        }
    }


    // 카메라 관련
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",         /* suffix */
                storageDir          /* directory */
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }


    private void saveImageToGallery(Bitmap bitmap) {
        // Create a new file for the image
        String imageFileName = "ASAP_Image_" + System.currentTimeMillis() + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(storageDir, imageFileName);

        try {
            // Save the bitmap to the file
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            // Notify the gallery about the new image
            MediaScannerConnection.scanFile(this,
                    new String[]{imageFile.getAbsolutePath()},
                    new String[]{"image/jpeg"}, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}