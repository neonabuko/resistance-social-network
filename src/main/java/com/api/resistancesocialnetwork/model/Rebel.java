package com.api.resistancesocialnetwork.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Rebel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer age;
    private String gender;
    private Integer reportCounter;

    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;
    private final List<Integer> reportedRebels = new ArrayList<>();

    public Rebel(String name, Integer age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.reportCounter = 0;
    }

    public Rebel() {
    }
    public Inventory getInventory() { return inventory; }
    public void setId(Integer id) {
        this.id = id;
    }

    public void setReportCounter(Integer reportCounter) {
        this.reportCounter = reportCounter;
    }

    public void setName(String newName) {
        name = newName;
    }
    public void setAge(Integer newAge) {
        age = newAge;
    }
    public void setGender(String newGender) {
        gender = newGender;
    }
    public boolean isTraitor() {
        return (reportCounter > 2);
    }

    public boolean isNotTraitor() {
        return !isTraitor();
    }

    public Integer getReportCounter() {
        return reportCounter;
    }

    public List<Integer> getReportedRebels() {
        return reportedRebels;
    }

    public void setReportCounterUp() {
        this.reportCounter++;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Rebel { " + "name='" + name + '\'' + ", age=" + age + ", gender='" + gender + '\'' + ", reportCounter=" + reportCounter +  ", ID=" + id + " }";
    }
}
