package com.league.foorballleagueapp.Model.TeamDetailsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.league.foorballleagueapp.Model.Area;

public class ActiveCompetition {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("area")
    @Expose
    private Area area;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("plan")
    @Expose
    private String plan;
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
