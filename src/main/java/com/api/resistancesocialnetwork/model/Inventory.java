package com.api.resistancesocialnetwork.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Inventory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "rebel_id")
    private Rebel rebel;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private List<Item> items;

    public Inventory(List<Item> itemList) {
        this.items = itemList;
    }
    public Inventory() {}

    public List<Item> getItems() {
        return items;
    }

    public void setNewItemList(List<Item> newItemList) {
        items = newItemList;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Inventory { " + "itemList=" + items + ", ID=" + id + '}';
    }
}
