package com.example.carsonmap;

public class Battery {
    private String title;
    private int image;
    private int value;

    public Battery(String title, int image, int value) {
        this.title = title;
        this.image = image;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
