package com.api.resistancesocialnetwork.model;

import jakarta.persistence.*;

@Entity
public class RebelStuffIds {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "rebel_id")
    private Rebel rebel;

    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;
}
