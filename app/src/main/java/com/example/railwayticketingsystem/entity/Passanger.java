package com.example.railwayticketingsystem.entity;

import java.io.Serializable;

/**
 * 乘客
 */
public class Passanger implements Serializable {
    private Integer id;
    private String name;
    private String idCard;
    private String type;
    private String telphone;

    public Passanger(Integer id, String name, String idCard, String type, String telphone) {
        this.id = id;
        this.name = name;
        this.idCard = idCard;
        this.type = type;
        this.telphone = telphone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
}