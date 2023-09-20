package com.api.resistancesocialnetwork.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "rebel")
public class Rebel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer age;
    private String gender;
    private Integer reportCounter;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "rebel")
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "rebel")
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    private final List<Integer> reportedRebels = new ArrayList<>();

    public Rebel(String name, Integer age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Rebel() {
    }

    public void setStats(String name, Integer age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
    public Location getLocation() {
        return location;
    }
    public Inventory getInventory() { return inventory; }

    public void setLocation(Location location) {
        this.location = location;
    }
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setReportCounter(Integer reportCounter) {
        this.reportCounter = reportCounter;
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
        return "Rebel { " + "ID=" + id + ", name='" + name + '\'' + ", age=" + age + ", gender='" + gender + '\'' + ", reportCounter=" + reportCounter + " }";
    }
}
