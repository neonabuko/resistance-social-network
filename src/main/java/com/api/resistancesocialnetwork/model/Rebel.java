package com.api.resistancesocialnetwork.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "rebel")
public class Rebel {
    private final List<Integer> reportedRebels = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rebel_id")
    private Integer id;
    @Column(name = "rebel_name")
    private String name;
    @Column(name = "rebel_age")
    private Integer age;
    @Column(name = "rebel_gender")
    private String gender;
    @Column(name = "rebel_report_counter")
    private Integer reportCounter = 0;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "rebel")
    @JoinColumn(name = "location_id")
    private Location location;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "rebel")
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    public Rebel(String name, Integer age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Rebel() {}

    public void setStats(String name, Integer age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
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

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Integer getReportCounter() {
        return reportCounter;
    }

    public void setReportCounterUp() {
        this.reportCounter++;
    }

    public List<Integer> getReportedRebels() {
        return reportedRebels;
    }

    public boolean isTraitor() {
        return (reportCounter > 2);
    }

    public boolean isNotTraitor() {
        return !isTraitor();
    }

    @Override
    public String toString() {
        return "Rebel{" + "id=" + id + ", name='" + name + '\'' + ", age=" + age + ", gender='" + gender + '\'' + ", isTraitor=" + isTraitor() + '}';
    }
}
