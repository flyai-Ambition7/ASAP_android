package com.asap.asap;

import java.net.URI;

public class ImageResultItem {
    // 임시로 둔 이미지 변수
    // 이미지 관련 변수

    /*
    URI image;
    public URI getImage() {
        return image;
    }

    public void setImage(URI image) {
        this.image = image;
    }
*/
    String imageData; // Base64로 변환된 이미지 데이터

    public ImageResultItem(String imageData){
        this.imageData = imageData;
    }
    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }







}
