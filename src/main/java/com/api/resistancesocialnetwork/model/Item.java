package com.api.resistancesocialnetwork.model;

import jakarta.persistence.*;

@Entity(name = "item")
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer id;
    @Column(name = "item_name")
    private String name;
    @Column(name = "item_price")
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

    @Override
    public String toString() {
        return "\n\tItem { " +
                "\n\t\tid=" + id +
                "\n\t\tname=" + name +
                "\n\t\tprice=" + price + "\n" +
                "\t}";
    }

}