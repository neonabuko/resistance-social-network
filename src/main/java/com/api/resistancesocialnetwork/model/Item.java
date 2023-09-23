package com.api.resistancesocialnetwork.model;

import jakarta.persistence.*;

@Entity(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer id;
    @Column(name = "item_name")
    private String name;
    @Column(name = "item_price")
    private Integer price;
    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    public Item(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    public Item() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item { " + "id=" + id + ", name=" + name + ", price=" + price + " }";
    }
}
