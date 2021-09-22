package com.tilismtech.tellotalk_shopping_sdk_app.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountriesPojo {

    public class Country {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("sortname")
        @Expose
        private String sortname;
        @SerializedName("name")
        @Expose
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSortname() {
            return sortname;
        }

        public void setSortname(String sortname) {
            this.sortname = sortname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }


        @SerializedName("countries")
        @Expose
        private List<Country> countries = null;

        public List<Country> getCountries() {
            return countries;
        }

        public void setCountries(List<Country> countries) {
            this.countries = countries;
        }


}
