package com.tilismtech.tellotalk_shopping_sdk.pojos;

public class ColorChooserPojo {
    private Integer color ;
    private boolean isSelected ;

    public ColorChooserPojo(Integer color, boolean isSelected) {
        this.color = color;
        this.isSelected = isSelected;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
