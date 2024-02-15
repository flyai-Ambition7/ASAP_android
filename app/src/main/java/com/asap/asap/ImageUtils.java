package com.asap.asap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

// bitmap base64로 변환하여 장고로 post 하기 위한 클래스
public class ImageUtils {
    // Bitmap을 Base64 문자열로 변환
    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    // 파일 경로를 Base64 문자열로 변환
    public static String filePathToBase64(String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        return bitmapToBase64(bitmap);
    }
}
