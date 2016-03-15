package com.example.hientran.karaoke_search;

/**
 * Created by hientran on 2/16/2016.
 */
public class SongQuery {
    String _id;
    String sname;
    String snameclean;
    String sabbr;
    int smanufacture;
    String slanguage;
    String slyric;
    String smeta;
    int favorite;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSnameclean() {
        return snameclean;
    }

    public void setSnameclean(String snameclean) {
        this.snameclean = snameclean;
    }

    public String getSabbr() {
        return sabbr;
    }

    public void setSabbr(String sabbr) {
        this.sabbr = sabbr;
    }

    public String getSlanguage() {
        return slanguage;
    }

    public void setSlanguage(String slanguage) {
        this.slanguage = slanguage;
    }

    public String getSlyric() {
        return slyric;
    }

    public void setSlyric(String slyric) {
        this.slyric = slyric;
    }

    public String getSmeta() {
        return smeta;
    }

    public void setSmeta(String smeta) {
        this.smeta = smeta;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getSmanufacture() {
        return smanufacture;
    }

    public void setSmanufacture(int smanufacture) {
        this.smanufacture = smanufacture;
    }
}