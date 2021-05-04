package com.tilismtech.tellotalk_shopping_sdk.pojos;

public class ChildCategory {
    private int subCategoryNumber;
    private String subCategoryName;

    public ChildCategory(int subCategoryNumber, String subCategoryName) {
        this.subCategoryNumber = subCategoryNumber;
        this.subCategoryName = subCategoryName;
    }

    public int getSubCategoryNumber() {
        return subCategoryNumber;
    }

    public void setSubCategoryNumber(int subCategoryNumber) {
        this.subCategoryNumber = subCategoryNumber;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }
}
