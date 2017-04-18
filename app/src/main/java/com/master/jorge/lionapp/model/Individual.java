package com.master.jorge.lionapp.model;

/**
 * Created by jorge on 15/04/17.
 */

public class Individual {
    private String name;
    private String address;
    private String city;
    private String email;
    private String uid;
    private String provider;
    private String gender;
    private boolean auditor;
    private int id;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public boolean isAuditor() {
        return auditor;
    }

    public void setAuditor(boolean auditor) {
        this.auditor = auditor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Individual{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", uid='" + uid + '\'' +
                ", provider='" + provider + '\'' +
                ", gender='" + gender + '\'' +
                ", auditor=" + auditor +
                ", id=" + id +
                ", age=" + age +
                '}';
    }
}
