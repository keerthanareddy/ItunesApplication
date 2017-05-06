package com.example.keerthana.itunesapp;

import java.io.Serializable;

/**
 * Created by keerthana on 2/23/2017.
 */

public class Itunes implements Serializable {
    String title;
    String logo;
    String price;
    int checked_val;

    public int getChecked_val() {
        return checked_val;
    }

    public void setChecked_val(int checked_val) {
        this.checked_val = checked_val;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


}
