package com.api.resistancesocialnetwork.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity(name = "inventory")
public class Inventory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    public Inventory(List<Item> itemList) {
        this.items = itemList;
    }

    public Inventory() {}

    public List<Item> getItems() {
        return items;
    }

    public Integer getId() {return id;}

    public void setId(Integer id) {
        this.id = id;
    }

    public Optional<Item> findItemBy(Integer itemId) {
        return items.stream().filter(item -> item.getId().equals(itemId)).findFirst();
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void replace(Item previousItem, Item newItem) {
        items.remove(previousItem);
        items.add(newItem);
    }

    @Override
    public String toString() {
        return "Inventory " +
                "(id " + id + ")" +
                "\t" + items.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(",", "") + "\n";
    }

}