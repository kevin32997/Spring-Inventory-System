package com.zndev.zn_inventory.models.notification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity(name="item_activity_notification")
@Getter
@Setter
@NoArgsConstructor
public class ItemActivityNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="description")
    private String description;

    @Column(name="action")
    private String action;

    @Column(name="type")
    private String type;

    @Column(name="isRead")
    private int isRead;

    @Column(name="user_id")
    private int userId;

    @Column(name="activity_date")
    private Date activityDate;
}
