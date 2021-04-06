package com.zndev.zn_inventory;

import com.zndev.zn_inventory.helpers.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class ZnInventoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZnInventoryApplication.class, args);
    }
}
