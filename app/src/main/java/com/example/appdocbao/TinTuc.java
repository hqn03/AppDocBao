package com.example.appdocbao;

import android.media.Image;

public class TinTuc {
    String TieuDe;
    String Anh;

    public TinTuc(String tieuDe, String anh) {
        TieuDe = tieuDe;
        Anh = anh;
    }

    public String getTieuDe() {
        return TieuDe;
    }

    public void setTieuDe(String tieuDe) {
        TieuDe = tieuDe;
    }

    public String getAnh() {
        return Anh;
    }

    public void setAnh(String anh) {
        Anh = anh;
    }
}
