package jnorbury.jonahnorbury_pset6;

import java.io.Serializable;

/**
 * Created by jonah on 08-Dec-16.
 */

public class Plant implements Serializable {
    private String type;
    private String purchase_date;
    private String nick_name;
    private String last_watered;
    private String next_water;
    private String description;
    private String img_url;
    private String wiki_url;

    public Plant(String name, String purchase_date, String wiki_url, String last_watered, String description) {
        this.type = name;
        this.purchase_date = purchase_date;
        this.wiki_url = wiki_url;
        this.last_watered = last_watered;
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getWiki_url() {
        return wiki_url;
    }

    public void setWiki_url(String wiki_url) {
        this.wiki_url = wiki_url;
    }

    public String getLast_watered() {
        return last_watered;
    }

    public void setLast_watered(String last_watered) {
        this.last_watered = last_watered;
    }

    public String getNext_water() {
        return next_water;
    }

    public void setNext_water(String next_water) {
        this.next_water = next_water;
    }


    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
