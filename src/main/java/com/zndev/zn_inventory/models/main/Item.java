package com.zndev.zn_inventory.models.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity(name = "items")
@Getter @Setter @NoArgsConstructor
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="brand_id")
    private int brandId;

    @Transient
    private String brandName;

    @Column(name="model")
    private String model;

    @Column(name="serial_number")
    private String serialNumber;

    @Column(name="type_id")
    private int typeId;

    @Transient
    private String typeName;

    @Column(name="code")
    private String code;

    @Column(name="property_number")
    private String propertyNumber;

    @Column(name="remarks")
    private String remarks;

    @Column(name="image")
    private String image;

    @Column(name="created_by")
    private int createdBy;

    @Column(name="date_created", updatable = false)
    private Date dateCreated=new Date();

    @Column(name="date_updated")
    private Date dateUpdated=new Date();

}

