package com.asap.asap;

import java.io.Serializable;

public class NewMenuInputItem implements Serializable {
    private static final long serialVersionUID = 1L;
  //  String result_image_url; // 생성된 이미지 url 지안 테스트

    String image; // Base64로 변환된 이미지 데이터
  //  String user_input_image;

    // TextUpload1Activiy에서 받는 것들
    String store_name,purpose, result_type, theme;

    // 별도 - 신메뉴일 때
    String product_name,price,description, business_hours, location, contact;

    /*
    public String getResult_image_url() {
        return result_image_url;
    }

    public void setResult_image_url(String result_image_url) {
        this.result_image_url = result_image_url;
    }

     */



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
/*

    public String getUser_input_image() {
        return user_input_image;
    }

    public void setUser_input_image(String user_input_image) {
        this.user_input_image = user_input_image;
    }
*/
    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getResult_type() {
        return result_type;
    }

    public void setResult_type(String result_type) {
        this.result_type = result_type;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBusiness_hours() {
        return business_hours;
    }

    public void setBusiness_hours(String business_hours) {
        this.business_hours = business_hours;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    /*
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
*/
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
