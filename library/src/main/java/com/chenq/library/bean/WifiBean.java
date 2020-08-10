package com.chenq.library.bean;

public class WifiBean {

    String ssid ;
    String bSsid ;
    int level ;

    public WifiBean(String ssid, String bSsid) {
        this.ssid = ssid;
        this.bSsid = bSsid;
    }

    public WifiBean(String ssid, String bSsid , int level) {
        this.ssid = ssid;
        this.bSsid = bSsid;
        this.level = level ;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getbSsid() {
        return bSsid;
    }

    public void setbSsid(String bSsid) {
        this.bSsid = bSsid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
