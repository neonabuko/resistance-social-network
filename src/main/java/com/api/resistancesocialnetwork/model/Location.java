package com.api.resistancesocialnetwork.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Location {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double latitude;
    private Double longitude;
    private String base;

    public Location(Double latitude, Double longitude, String base) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.base = base;
    }

    public Location() {
    }

    public void setNewLocation(Double newLatitude, Double newLongitude, String newBase) {
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

    public void setLatitude(Double newLatitude) {
        latitude = newLatitude;
    }
    public void setLongitude(Double newLongitude){
        longitude = newLongitude;
    }
    public void setBase(String newBase) {
        base = newBase;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Location { " + "latitude=" + latitude + ", longitude=" + longitude + ", base='" + base + '\'' + ", ID=" + id + " }";
    }
}
