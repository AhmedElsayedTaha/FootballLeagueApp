package com.league.foorballleagueapp.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class Team implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private Integer id;

    @Ignore
    @SerializedName("area")
    @Expose
    private Area area;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    private String name;

    @ColumnInfo(name = "shortName")
    @SerializedName("shortName")
    @Expose
    private String shortName;

    @ColumnInfo(name = "tla")
    @SerializedName("tla")
    @Expose
    private String tla;

    @ColumnInfo(name = "crestUrl")
    @SerializedName("crestUrl")
    @Expose
    private String crestUrl;

    @ColumnInfo(name = "address")
    @SerializedName("address")
    @Expose
    private String address;

    @ColumnInfo(name = "phone")
    @SerializedName("phone")
    @Expose
    private String phone;

    @ColumnInfo(name = "website")
    @SerializedName("website")
    @Expose
    private String website;

    @ColumnInfo(name = "email")
    @SerializedName("email")
    @Expose
    private String email;

    @ColumnInfo(name = "founded")
    @SerializedName("founded")
    @Expose
    private Integer founded;

    @ColumnInfo(name = "clubColors")
    @SerializedName("clubColors")
    @Expose
    private String clubColors;

    @ColumnInfo(name = "venue")
    @SerializedName("venue")
    @Expose
    private String venue;

    @Ignore
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Ignore
    public Area getArea() {
        return area;
    }
    @Ignore
    public void setArea(Area area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTla() {
        return tla;
    }

    public void setTla(String tla) {
        this.tla = tla;
    }

    public String getCrestUrl() {
        return crestUrl;
    }

    public void setCrestUrl(String crestUrl) {
        this.crestUrl = crestUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFounded() {
        return founded;
    }

    public void setFounded(Integer founded) {
        this.founded = founded;
    }

    public String getClubColors() {
        return clubColors;
    }

    public void setClubColors(String clubColors) {
        this.clubColors = clubColors;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
    @Ignore
    public String getLastUpdated() {
        return lastUpdated;
    }
    @Ignore
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}


