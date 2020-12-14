package com.gds.swe.challenge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

public class FileInfo {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "login")
    private String login;

    @Column(name = "name")
    private String name;

    @Column(name = "salary")
    private double salary;

    public FileInfo(){}

    public FileInfo(String id, String login, String name, double salary) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.salary = salary;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "FileInfo [id=" + id + ", login=" + login + ", name=" + name + ", salary=" + salary + "]";
    }
}
