package com.api.resistancesocialnetwork.model;

import jakarta.persistence.*;

@Entity(name = "location")
public class Location {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double latitude;
    private Double longitude;
    private String base;

    @OneToOne
    @JoinColumn(name = "rebel_id")
    private Rebel rebel;

    public Location(Double latitude, Double longitude, String base) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.base = base;
        id = 0;
    }

    public Location() {
        id = 0;
    }

    public void setNewLocation(Double newLatitude, Double newLongitude, String newBase) {
        this.latitude = newLatitude;
        this.longitude = newLongitude;
        this.base = newBase;
    }

    public Rebel getRebel() {
        return rebel;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRebel(Rebel rebel) {
        this.rebel = rebel;
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

    @Override
    public String toString() {
        return "Location { " + "ID=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", base='" + base + '\'' + " }";
    }
}
