package com.tilismtech.tellotalk_shopping_sdk.pojos;

public class ReceivedItemPojo {
    private String order_number;
    private String customer_name_number;
    private String customer_address;

    public ReceivedItemPojo(String order_number, String customer_name_number, String customer_address) {
        this.order_number = order_number;
        this.customer_name_number = customer_name_number;
        this.customer_address = customer_address;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getCustomer_name_number() {
        return customer_name_number;
    }

    public void setCustomer_name_number(String customer_name_number) {
        this.customer_name_number = customer_name_number;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }
}
