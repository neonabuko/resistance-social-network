package com.api.resistancesocialnetwork.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity(name = "inventory")
public class Inventory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "inventory")
    private List<Item> items = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "rebel_id")
    private Rebel rebel;

    public Inventory(List<Item> itemList) {
        this.items = itemList;
    }
    public Inventory() {}

    public Optional<Item> findItemByName(String itemName) {
        return items.stream().filter(item -> item.getName().equals(itemName)).findFirst();
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

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Inventory { " + "ID=" + id + ", items=" + items + " }";
    }
}
