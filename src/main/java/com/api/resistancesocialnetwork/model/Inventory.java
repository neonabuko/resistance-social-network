package com.api.resistancesocialnetwork.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventory")
    private List<Item> items = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "rebel_id")
    private Rebel rebel;

    public Inventory(List<Item> itemList) {
        this.items = itemList;
    }

    public Inventory() {}

    public Rebel getRebel() {
        return rebel;
    }

    public void setRebel(Rebel rebel) {
        this.rebel = rebel;
    }

    public List<Item> getItems() {
        return items;
    }

    public Integer getId() {return id;}

    public void setId(Integer id) {
        this.id = id;
    }

    public Optional<Item> findItemByName(String itemName) {
        return items.stream().filter(item -> item.getName().equals(itemName)).findFirst();
    }

    @Override
    public String toString() {
        return "Inventory { " + "ID=" + id + ", items=" + items + " }";
    }
}