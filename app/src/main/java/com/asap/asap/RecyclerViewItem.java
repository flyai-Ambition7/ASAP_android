package com.asap.asap;

public class RecyclerViewItem {
    private String imageUrl;
    private boolean isSelected;
    public RecyclerViewItem(String imageUrl, boolean isSelected){
        this.imageUrl = imageUrl;
        this.isSelected = isSelected;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
