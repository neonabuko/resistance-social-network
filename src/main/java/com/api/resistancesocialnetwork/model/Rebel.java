package com.api.resistancesocialnetwork.model;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "rebel")
public class Rebel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer age;
    private String gender;
    private Integer reportCounter = 0;

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
        id = 0;
    }

    public Rebel() {
        id = 0;
    }

    public void setStats(String name, Integer age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
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

    public Inventory getInventory() { return inventory; }
    public void setLocation(Location location) {
        this.location = location;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Integer getReportCounter() {
        return reportCounter;
    }

    public void setReportCounter(Integer reportCounter) {
        this.reportCounter = reportCounter;
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
        return "Rebel { " + "ID=" + id + ", name='" + name + '\'' + ", age=" + age + ", gender='" + gender + '\'' + ", reportCounter=" + reportCounter + " }";
    }
}
