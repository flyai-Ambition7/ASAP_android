package com.asap.asap;

public class TextUpload1Item {
    // 입력 페이지 공통 : 매장명, 사용 목적, 결과물 형태, 테마
    private  String storeName, theme, purpose, resultForm;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getResultForm() {
        return resultForm;
    }

    public void setResultForm(String resultForm) {
        this.resultForm = resultForm;
    }
}
