package com.zndev.zn_inventory.helpers;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ResourceHelper {

    public static final String TAG_ITEM = "item";
    public static final String TAG_BRAND = "brand";
    public static final String TAG_TYPE = "type";
    public static final String TAG_LOCATION = "location";
    public static final String TAG_INVENTORY = "inventory";

    public static String[] TAGS = {
            "item",
            "brand",
            "type",
            "location",
            "inventory"
    };

    @Bean
    public static String[] GET_TAGS() {
        return TAGS;
    }

    public static final String DIRECTION_ASCENDING = "ascending";
    public static final String DIRECTION_DESCENDING = "descending";

}   
