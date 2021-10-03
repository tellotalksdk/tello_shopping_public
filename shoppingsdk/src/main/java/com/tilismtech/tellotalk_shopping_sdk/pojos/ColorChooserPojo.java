package com.tilismtech.tellotalk_shopping_sdk.pojos;

public class ColorChooserPojo {
    private String color;
    private boolean isSelected;
    private int position;
    private boolean isAlreadySelected;
    private boolean isFirstTimeOpen;

    public ColorChooserPojo(String color, boolean isSelected) {
        this.color = color;
        this.isSelected = isSelected;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isAlreadySelected() {
        return isAlreadySelected;
    }

    public void setAlreadySelected(boolean alreadySelected , int position) {
        this.isAlreadySelected = alreadySelected;
        this.position = position;
    }

    public boolean isFirstTimeOpen() {
        return isFirstTimeOpen;
    }

    public void setFirstTimeOpen(boolean firstTimeOpen) {
        isFirstTimeOpen = firstTimeOpen;
    }
}
