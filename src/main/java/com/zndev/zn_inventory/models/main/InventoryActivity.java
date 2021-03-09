package com.zndev.zn_inventory.models.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;
import java.util.Date;

@Entity(name="inv_activity")
@Getter
@Setter
@NoArgsConstructor
public class InventoryActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="item_id")
    private int itemId;

    @Column(name="reference_id")
    private int referenceId;

    @Column(name="location_id")
    private int locationId;

    @Column(name="other_location_id")
    private int otherLocationId;

    @Column(name="total")
    private int total;

    @Column(name="action")
    private String action;

    @Column(name="activity_date", updatable = false)
    private Date activityDate=new Date();

}
