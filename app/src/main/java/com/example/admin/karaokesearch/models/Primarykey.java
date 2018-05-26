package com.example.admin.karaokesearch.models;



import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by admin on 3/8/2017.
 */
@Entity
public class Primarykey {
    private int ENT;
    private String NAME;
    private int SUPER;
    private int MAX;

    @Keep
    public Primarykey() {
    }

    @Keep
    public Primarykey(int ENT, String NAME, int SUPER, int MAX) {
        this.ENT = ENT;
        this.NAME = NAME;
        this.SUPER = SUPER;
        this.MAX = MAX;
    }

    public int getENT() {
        return ENT;
    }

    public void setENT(int ENT) {
        this.ENT = ENT;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getSUPER() {
        return SUPER;
    }

    public void setSUPER(int SUPER) {
        this.SUPER = SUPER;
    }

    public int getMAX() {
        return MAX;
    }

    public void setMAX(int MAX) {
        this.MAX = MAX;
    }
}
