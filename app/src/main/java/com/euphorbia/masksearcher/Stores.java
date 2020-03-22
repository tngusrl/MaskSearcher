package com.euphorbia.masksearcher;

public class Stores {

    private String code;

    private String lng;

    private String stock_at;

    private String name;

    private String created_at;

    private String addr;

    private String type;

    private String remain_stat;

    private String lat;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getStock_at() {
        return stock_at;
    }

    public void setStock_at(String stock_at) {
        this.stock_at = stock_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemain_stat() {
        return remain_stat;
    }

    public void setRemain_stat(String remain_stat) {
        this.remain_stat = remain_stat;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "ClassPojo [code = " + code + ", lng = " + lng + ", stock_at = " + stock_at + ", name = " + name + ", created_at = " + created_at + ", addr = " + addr + ", type = " + type + ", remain_stat = " + remain_stat + ", lat = " + lat + "]";
    }
}
