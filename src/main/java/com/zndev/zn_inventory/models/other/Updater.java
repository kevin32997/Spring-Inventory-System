package com.zndev.zn_inventory.models.other;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "updates")
@Getter
@Setter
@NoArgsConstructor
public class Updater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="code")
    private String code;

    @Column(name="date_updated")
    private Date dateUpdated=new Date();

}
