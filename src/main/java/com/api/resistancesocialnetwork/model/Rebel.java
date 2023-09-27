package com.api.resistancesocialnetwork.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "rebel")
public class Rebel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;
    @Column(name = "gender")
    private String gender;
    @Column(name = "report_counter")
    private Integer reportCounter = 0;
    @OneToOne(cascade = CascadeType.ALL)
    private Location location;
    @OneToOne(cascade = CascadeType.ALL)
    private Inventory inventory;
    @Column(name = "reported_ids")
    private final List<Integer> reportedRebels = new ArrayList<>();

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

    public void addToReportedRebels(Integer reportedId) {
        reportedRebels.add(reportedId);
    }

    @Override
    public String toString() {
        return "Rebel " +
                "(id " + id + ")" +
                "\n\tname='" + name + '\'' +
                "\n\tage=" + age +
                "\n\tgender='" + gender + '\'' +
                "\n\tisTraitor=" + isTraitor() + "\n";
    }

}
