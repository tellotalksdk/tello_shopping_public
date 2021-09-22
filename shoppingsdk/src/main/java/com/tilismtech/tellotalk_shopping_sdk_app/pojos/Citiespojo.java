package com.tilismtech.tellotalk_shopping_sdk_app.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Citiespojo {

    @SerializedName("cities")
    @Expose
    private List<Citiespojo.Cities> cities = null;

    public List<Citiespojo.Cities> getCities() {
        return cities;
    }

    public void setCountries(List<Citiespojo.Cities> cities) {
        this.cities = cities;
    }

    public class Cities {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("state_id")
        @Expose
        private String state_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getState_id() {
            return state_id;
        }

        public void setState_id(String state_id) {
            this.state_id = state_id;
        }
    }


}
