package com.example.admin.karaokesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Id;

/**
 * Created by admin on 3/10/2017.
 */

public class SongTable implements Parcelable{
    @Id()
    protected long id;                 // id bài hát trong bảng
    protected int ENT;
    protected int OPT;
    protected int ZROWID;               // Mã số bài hát
    protected int ZSVOL;                // vol
    protected String ZSABBR;           // ten bai hát viết tắt
    protected String ZSLANGUAGE;      // vn , en
    protected String ZSLYRIC;         // lời bài hát
    protected String ZSLYRICCLEAN;    // lời bài hát không dấu
    protected String ZSMANUFACTURE;   // id table song
    protected String ZSMETA;          // tác giả
    protected String ZSMETACLEAN;      // tác giả không dấu
    protected String ZSNAME;           // tên bài hát
    protected String ZSNAMECLEAN;      // tên bài hát ko dấu ,
    protected String ZYOUTUBE;
    protected int FAVORITE;            // yêu thích

    public SongTable() {
    }

    public SongTable(long id, int ENT, int OPT, int ZROWID, int ZSVOL, String ZSABBR, String ZSLANGUAGE, String ZSLYRIC, String ZSLYRICCLEAN, String ZSMANUFACTURE, String ZSMETA, String ZSMETACLEAN, String ZSNAME, String ZSNAMECLEAN, String ZYOUTUBE) {
        this.id = id;
        this.ENT = ENT;
        this.OPT = OPT;
        this.ZROWID = ZROWID;
        this.ZSVOL = ZSVOL;
        this.ZSABBR = ZSABBR;
        this.ZSLANGUAGE = ZSLANGUAGE;
        this.ZSLYRIC = ZSLYRIC;
        this.ZSLYRICCLEAN = ZSLYRICCLEAN;
        this.ZSMANUFACTURE = ZSMANUFACTURE;
        this.ZSMETA = ZSMETA;
        this.ZSMETACLEAN = ZSMETACLEAN;
        this.ZSNAME = ZSNAME;
        this.ZSNAMECLEAN = ZSNAMECLEAN;
        this.ZYOUTUBE = ZYOUTUBE;
    }

    protected SongTable(Parcel in) {
        id = in.readLong();
        ENT = in.readInt();
        OPT = in.readInt();
        ZROWID = in.readInt();
        ZSVOL = in.readInt();
        ZSABBR = in.readString();
        ZSLANGUAGE = in.readString();
        ZSLYRIC = in.readString();
        ZSLYRICCLEAN = in.readString();
        ZSMANUFACTURE = in.readString();
        ZSMETA = in.readString();
        ZSMETACLEAN = in.readString();
        ZSNAME = in.readString();
        ZSNAMECLEAN = in.readString();
        ZYOUTUBE = in.readString();
        FAVORITE = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(ENT);
        dest.writeInt(OPT);
        dest.writeInt(ZROWID);
        dest.writeInt(ZSVOL);
        dest.writeString(ZSABBR);
        dest.writeString(ZSLANGUAGE);
        dest.writeString(ZSLYRIC);
        dest.writeString(ZSLYRICCLEAN);
        dest.writeString(ZSMANUFACTURE);
        dest.writeString(ZSMETA);
        dest.writeString(ZSMETACLEAN);
        dest.writeString(ZSNAME);
        dest.writeString(ZSNAMECLEAN);
        dest.writeString(ZYOUTUBE);
        dest.writeInt(FAVORITE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SongTable> CREATOR = new Creator<SongTable>() {
        @Override
        public SongTable createFromParcel(Parcel in) {
            return new SongTable(in);
        }

        @Override
        public SongTable[] newArray(int size) {
            return new SongTable[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getENT() {
        return ENT;
    }

    public void setENT(int ENT) {
        this.ENT = ENT;
    }

    public int getOPT() {
        return OPT;
    }

    public void setOPT(int OPT) {
        this.OPT = OPT;
    }

    public int getZROWID() {
        return ZROWID;
    }

    public void setZROWID(int ZROWID) {
        this.ZROWID = ZROWID;
    }

    public int getZSVOL() {
        return ZSVOL;
    }

    public void setZSVOL(int ZSVOL) {
        this.ZSVOL = ZSVOL;
    }

    public String getZSABBR() {
        return ZSABBR;
    }

    public void setZSABBR(String ZSABBR) {
        this.ZSABBR = ZSABBR;
    }

    public String getZSLANGUAGE() {
        return ZSLANGUAGE;
    }

    public void setZSLANGUAGE(String ZSLANGUAGE) {
        this.ZSLANGUAGE = ZSLANGUAGE;
    }

    public String getZSLYRIC() {
        return ZSLYRIC;
    }

    public void setZSLYRIC(String ZSLYRIC) {
        this.ZSLYRIC = ZSLYRIC;
    }

    public String getZSLYRICCLEAN() {
        return ZSLYRICCLEAN;
    }

    public void setZSLYRICCLEAN(String ZSLYRICCLEAN) {
        this.ZSLYRICCLEAN = ZSLYRICCLEAN;
    }

    public String getZSMANUFACTURE() {
        return ZSMANUFACTURE;
    }

    public void setZSMANUFACTURE(String ZSMANUFACTURE) {
        this.ZSMANUFACTURE = ZSMANUFACTURE;
    }

    public String getZSMETA() {
        return ZSMETA;
    }

    public void setZSMETA(String ZSMETA) {
        this.ZSMETA = ZSMETA;
    }

    public String getZSMETACLEAN() {
        return ZSMETACLEAN;
    }

    public void setZSMETACLEAN(String ZSMETACLEAN) {
        this.ZSMETACLEAN = ZSMETACLEAN;
    }

    public String getZSNAME() {
        return ZSNAME;
    }

    public void setZSNAME(String ZSNAME) {
        this.ZSNAME = ZSNAME;
    }

    public String getZSNAMECLEAN() {
        return ZSNAMECLEAN;
    }

    public void setZSNAMECLEAN(String ZSNAMECLEAN) {
        this.ZSNAMECLEAN = ZSNAMECLEAN;
    }

    public String getZYOUTUBE() {
        return ZYOUTUBE;
    }

    public void setZYOUTUBE(String ZYOUTUBE) {
        this.ZYOUTUBE = ZYOUTUBE;
    }

    public int getFAVORITE() {
        return FAVORITE;
    }

    public void setFAVORITE(int FAVORITE) {
        this.FAVORITE = FAVORITE;
    }
}
