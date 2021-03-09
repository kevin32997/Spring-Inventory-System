package com.zndev.zn_inventory.models.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "item_id")
    private int itemId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "location_id")
    private int locationId;

    @ManyToOne(targetEntity = Item.class)
    @JoinColumn(name="item_id", insertable = false, updatable = false)
    private Item item;

}
