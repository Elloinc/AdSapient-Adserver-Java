package com.adsapient.reporter;

import com.adsapient.api.IMappable;

public class AbstractUniquesHourlyRecord implements IMappable {
    private Integer id;
    private int hour;
    private int entityid;
    private String entityClass;
    private int uniques;
    private int views;
    private int clicks;
    private int leads;
    private int sales;
    private double earnedspent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getEntityid() {
        return entityid;
    }

    public void setEntityid(int entityid) {
        this.entityid = entityid;
    }

    public String getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }

    public int getUniques() {
        return uniques;
    }

    public void setUniques(int uniques) {
        this.uniques = uniques;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getLeads() {
        return leads;
    }

    public void setLeads(int leads) {
        this.leads = leads;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public double getEarnedspent() {
        return earnedspent;
    }

    public void setEarnedspent(double earnedspent) {
        this.earnedspent = earnedspent;
    }
}
