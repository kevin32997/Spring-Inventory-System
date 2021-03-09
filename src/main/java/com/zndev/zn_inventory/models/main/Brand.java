package com.zndev.zn_inventory.models.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name="brands")
@Getter @Setter @NoArgsConstructor
public class Brand implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="created_by")
    private int createdBy;

    @Column(name="date_created",updatable = false)
    private Date dateCreated=new Date();

    @Column(name="date_updated")
    private Date dateUpdated=new Date();
}
