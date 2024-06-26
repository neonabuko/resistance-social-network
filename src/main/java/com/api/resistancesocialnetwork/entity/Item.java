package com.api.resistancesocialnetwork.entity;

import jakarta.persistence.*;

@Entity(name = "item")
@Table(name = "items")
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Integer price;

    public Item(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    public Item() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}