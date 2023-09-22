package com.api.resistancesocialnetwork.model;

import jakarta.persistence.*;

@Entity(name = "item")
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    public Item(String name, Integer price) {
        this.name = name;
        this.price = price;
        id = 0;
    }

    public Item() {
        id = 0;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Item { " + "id=" + id + ", name=" + name + ", price=" + price + " }";
    }
}
