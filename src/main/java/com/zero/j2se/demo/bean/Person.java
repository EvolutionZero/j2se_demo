package com.zero.j2se.demo.bean;

public class Person {

    private String name;

    private String country;

    private String language;

    private int worksNum;


    public Person(String name, String country, String language, int worksNum) {
        this.name = name;
        this.country = country;
        this.language = language;
        this.worksNum = worksNum;
    }


    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Person setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public Person setLanguage(String language) {
        this.language = language;
        return this;
    }

    public int getWorksNum() {
        return worksNum;
    }

    public Person setWorksNum(int worksNum) {
        this.worksNum = worksNum;
        return this;
    }
}
