package com.wu.crawler.entity;

import java.util.HashMap;
import java.util.Map;

public class Product {
    private String name;
    private double price;
    private double goal;
    private String mainUrl;
    private String type;
    private Map<String,String> detail;
    public Product(){
        detail = new HashMap<>();
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized double getPrice() {
        return price;
    }

    public synchronized void setPrice(double price) {
        this.price = price;
    }

    public synchronized double getGoal() {
        return goal;
    }

    public synchronized void setGoal(double goal) {
        this.goal = goal;
    }

    public synchronized String getMainUrl() {
        return mainUrl;
    }

    public synchronized void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public synchronized Map<String, String> getDetail() {
        return detail;
    }

    public synchronized void setDetail(Map<String, String> detail) {
        this.detail = detail;
    }

    public synchronized String getType() {
        return type;
    }

    public synchronized void setType(String type) {
        this.type = type;
    }
}
