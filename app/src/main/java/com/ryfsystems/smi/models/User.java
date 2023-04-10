package com.ryfsystems.smi.models;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String userName;
    private String password;
    private String name;
    private String rol;

    public User() {
    }

    public User(int id, String userName, String password, String name, String rol) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.rol = rol;
    }

    public User(int id, String name, String rol) {
        this.id = id;
        this.name = name;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
