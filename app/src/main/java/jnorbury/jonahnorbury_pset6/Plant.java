package jnorbury.jonahnorbury_pset6;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jonah on 08-Dec-16.
 */

public class Plant implements Serializable {
    private String name;
    private String purchase_date;
    private String wiki_url;
    private String nick_name;
    private String last_watered;
//    private Date last_watered;
    private String next_water;
    private String last_feed;
    private String next_feed;

    public Plant(String name, String purchase_date, String wiki_url, String last_watered) {
        this.name = name;
        this.purchase_date = purchase_date;
        this.wiki_url = wiki_url;
        this.last_watered = last_watered;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLast_feed() {
        return last_feed;
    }

    public void setLast_feed(String last_feed) {
        this.last_feed = last_feed;
    }

    public String getNext_feed() {
        return next_feed;
    }

    public void setNext_feed(String next_feed) {
        this.next_feed = next_feed;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
}
