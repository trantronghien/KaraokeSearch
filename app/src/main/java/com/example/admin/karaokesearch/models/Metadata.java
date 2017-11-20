package com.example.admin.karaokesearch.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by admin on 3/8/2017.
 */
@Entity
public class Metadata {
    private int VERSION;
    private String UUID;
    private byte[] PLIST;

    @Keep
    public Metadata() {
    }

    @Keep
    public Metadata(int VERSION, String UUID, byte[] PLIST) {
        this.VERSION = VERSION;
        this.UUID = UUID;
        this.PLIST = PLIST;
    }

    public int getVERSION() {
        return VERSION;
    }

    public void setVERSION(int VERSION) {
        this.VERSION = VERSION;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public byte[] getPLIST() {
        return PLIST;
    }

    public void setPLIST(byte[] PLIST) {
        this.PLIST = PLIST;
    }
}
