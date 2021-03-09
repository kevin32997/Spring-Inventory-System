package com.zndev.zn_inventory.models.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "inv_activity_reference")
@Getter
@Setter
@NoArgsConstructor
public class InventoryActivityReference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "reference")
    private String reference;

    @Column(name = "consignee")
    private String consignee;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "location")
    private int location;

    @Column(name = "location_from")
    private int locationFrom;

    @Column(name = "action")
    private String action;

    @Column(name = "activity_date")
    private Date activityDate = new Date();
}
