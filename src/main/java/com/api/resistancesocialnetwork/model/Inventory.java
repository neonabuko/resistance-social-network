package com.api.resistancesocialnetwork.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventory")
    private List<Item> items;
    @OneToOne
    @JoinColumn(name = "rebel_id")
    private Rebel rebel;

    public Inventory(List<Item> itemList) {
        this.items = itemList;
    }

    public Inventory() {
    }

    public Rebel getRebel() {
        return rebel;
    }

    public void setRebel(Rebel rebel) {
        this.rebel = rebel;
    }

    public List<Item> getItems() {
        return items;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Inventory { " + "ID=" + id + ", items=" + items + " }";
    }
}
