package org.sc.cbm.e193.community.pojo;


import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class LifeguardTower {
    public enum Weather {CLEAN, CLOUDY, RAINY}
    public enum JellyFish {NONE, LITTLE, MUCH}
    public enum HazardFlag {BLACK, GREEN, YELLOW, RED}

    private String city;
    private String beach;
    private LatLng latLng;
    private Drawable sea;
    private Drawable sand;
    private int lifeguardsNum;

    private Date lastModified;
    private HazardFlag hazardFlag;
    private Weather weather;
    private JellyFish jellyFish;

    public LifeguardTower(String city, String beach, LatLng latLng, Drawable sea, Drawable sand, int lifeguardsNum, Date lastModified, HazardFlag hazardFlag, Weather weather, JellyFish jellyFish) {
        this.city = city;
        this.beach = beach;
        this.latLng = latLng;
        this.sea = sea;
        this.sand = sand;
        this.lifeguardsNum = lifeguardsNum;
        this.lastModified = lastModified;
        this.hazardFlag = hazardFlag;
        this.weather = weather;
        this.jellyFish = jellyFish;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBeach() {
        return beach;
    }

    public void setBeach(String beach) {
        this.beach = beach;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public Drawable getSea() {
        return sea;
    }

    public void setSea(Drawable sea) {
        this.sea = sea;
    }

    public Drawable getSand() {
        return sand;
    }

    public void setSand(Drawable sand) {
        this.sand = sand;
    }

    public int getLifeguardsNum() {
        return lifeguardsNum;
    }

    public void setLifeguardsNum(int lifeguardsNum) {
        this.lifeguardsNum = lifeguardsNum;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public HazardFlag getHazardFlag() {
        return hazardFlag;
    }

    public void setHazardFlag(HazardFlag hazardFlag) {
        this.hazardFlag = hazardFlag;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public JellyFish getJellyFish() {
        return jellyFish;
    }

    public void setJellyFish(JellyFish jellyFish) {
        this.jellyFish = jellyFish;
    }
}
