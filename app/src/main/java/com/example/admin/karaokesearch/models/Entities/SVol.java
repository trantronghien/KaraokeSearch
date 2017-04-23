package com.example.admin.karaokesearch.models.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

/**
 * Created by admin on 3/8/2017.
 */

@Entity
public class SVol {

    private int ZSVOL;
    private String ZSNAME;
    private String ZSMANUFACTURE;    // 1 SONG_ARIRANG 2 SONG_CALIFORNIA 3 SONG_MUSICCORE 4 SONG_VIETKTV
    private String ZSLANGUAGE;

    @Generated(hash = 2030238300)
    public SVol(int ZSVOL, String ZSNAME, String ZSMANUFACTURE, String ZSLANGUAGE) {
        this.ZSVOL = ZSVOL;
        this.ZSNAME = ZSNAME;
        this.ZSMANUFACTURE = ZSMANUFACTURE;
        this.ZSLANGUAGE = ZSLANGUAGE;
    }
    @Generated(hash = 736817809)
    public SVol() {
    }
    public int getZSVOL() {
        return this.ZSVOL;
    }
    public void setZSVOL(int ZSVOL) {
        this.ZSVOL = ZSVOL;
    }
    public String getZSNAME() {
        return this.ZSNAME;
    }
    public void setZSNAME(String ZSNAME) {
        this.ZSNAME = ZSNAME;
    }
    public String getZSMANUFACTURE() {
        return this.ZSMANUFACTURE;
    }
    public void setZSMANUFACTURE(String ZSMANUFACTURE) {
        this.ZSMANUFACTURE = ZSMANUFACTURE;
    }
    public String getZSLANGUAGE() {
        return this.ZSLANGUAGE;
    }
    public void setZSLANGUAGE(String ZSLANGUAGE) {
        this.ZSLANGUAGE = ZSLANGUAGE;
    }

   
}
