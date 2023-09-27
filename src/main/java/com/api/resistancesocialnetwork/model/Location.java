package com.api.resistancesocialnetwork.model;

import jakarta.persistence.*;

@Entity(name = "location")
public class Location {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "base")
    private String base;

    public Location(Double latitude, Double longitude, String base) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.base = base;
    }

    public Location() {}

    public void setLocation(Double newLatitude, Double newLongitude, String newBase) {
        this.latitude = newLatitude;
        this.longitude = newLongitude;
        this.base = newBase;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getBase() {
        return base;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Location " +
                "(id " + id + ")" +
                "\n\tlatitude=" + latitude +
                "\n\tlongitude=" + longitude +
                "\n\tbase='" + base + '\'' + "\n";
    }

}