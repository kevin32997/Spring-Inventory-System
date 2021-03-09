package com.zndev.zn_inventory.models.other;

import com.zndev.zn_inventory.models.main.*;
import com.zndev.zn_inventory.models.notification.InvActivityNotification;
import com.zndev.zn_inventory.models.notification.ItemActivityNotification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Response {

    private String message;
    private boolean status;
    private List<?> list;

}
