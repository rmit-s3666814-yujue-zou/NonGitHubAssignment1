package com.mininet;

import java.util.ArrayList;

abstract class Person implements PPerson {
    private String name;
    private int age;
    private char sex;
    private String status;
    private ArrayList<Relationship> friends = new ArrayList<>();

    public Person(String name, int age, String sex, String status) {
        this.name = name.toUpperCase();
        this.age = age;
        this.sex = sex.toUpperCase().charAt(0);
        this.status = status;
    }

    public Person(String name, int age, String sex) {
        this(name, age, sex, "No Status");
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getStatus() {
        return status;
    }

    public char getSex() {
        return sex;
    }

    public ArrayList<Relationship> getFriends() {
        return friends;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public void setSex(String sex) {
        this.sex = sex.toUpperCase().charAt(0);
    }
}
