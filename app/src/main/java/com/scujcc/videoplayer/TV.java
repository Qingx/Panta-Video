package com.scujcc.videoplayer;

import java.io.Serializable;

public class TV implements Serializable {
    private int iconId;
    private String tvTitle;
    private String tvDetail;
    private String tvUrl;

    public TV(int iconId, String tvTitle, String tvDetail,String tvUrl) {
        this.iconId=iconId;
        this.tvTitle=tvTitle;
        this.tvDetail=tvDetail;
        this.tvUrl = tvUrl;
    }


    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
    }

    public int setIconId() {
        return 0;
    }

    public String getTvDetail() {
        return tvDetail;
    }

    public void setTvDetail(String tvDetail) {
        this.tvDetail = tvDetail;
    }

    public String getTvUrl() {
        return tvUrl;
    }

    public void setTvUrl(String tvUrl) {
        this.tvUrl = tvUrl;
    }
}
