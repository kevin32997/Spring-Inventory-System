package com.zndev.zn_inventory.models.notification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name="inv_activity_notification")
@Getter
@Setter
@NoArgsConstructor
public class InvActivityNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="description")
    private String description;

    @Column(name="act_from")
    private String actFrom;

    @Column(name="act_to")
    private String actTo;

    @Column(name="action")
    private String action;

    @Column(name="is_read")
    private int isRead;

    @Column(name="user_id")
    private int userId;

    @Column(name="activity_date")
    private Date activityDate=new Date();
}
