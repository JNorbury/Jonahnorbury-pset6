package jnorbury.jonahnorbury_pset6;

import java.io.Serializable;

/**
 * Created by jonah on 08-Dec-16.
 */

public class Plant implements Serializable {

    private String _id;
    private String type;
    private String nick_name;
    private String description;

    private String purchase_date;
    private String last_watered;

    private String img_url;
    private String wiki_url;

    private boolean favourite;

    public Plant() {
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
