package com.zndev.zn_inventory.models.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "location_id")
    private int locationId;

    @Column(name="property_number")
    private String propertyNumber;

    @Column(name="serial_number")
    private String serialNumber;

    @Column(name="code")
    private String code;

    @ManyToOne(targetEntity = Item.class)
    @JoinColumn(name="item_id", insertable = false, updatable = false)
    private Item item;

    @Column(name="created_by")
    private int createdBy;

    @Column(name="date_created", updatable = false)
    private Date dateCreated=new Date();

    @Column(name="date_updated")
    private Date dateUpdated=new Date();

}
