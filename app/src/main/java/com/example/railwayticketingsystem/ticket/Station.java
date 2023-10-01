package com.example.railwayticketingsystem.ticket;

public class Station {
    private Integer _id;
    private String station_name;
    private String sort_order;
    private String tele_code;
    private String province;
    private String city;

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }

    public String getTele_code() {
        return tele_code;
    }

    public void setTele_code(String tele_code) {
        this.tele_code = tele_code;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
