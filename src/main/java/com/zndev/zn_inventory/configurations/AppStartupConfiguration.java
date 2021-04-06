package com.zndev.zn_inventory.configurations;

import com.zndev.zn_inventory.helpers.ResourceHelper;
import com.zndev.zn_inventory.models.other.TableUpdate;
import com.zndev.zn_inventory.repository.UpdatesRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class AppStartupConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AppStartupConfiguration.class);

    @Autowired
    private UpdatesRepo updatesRepo;

    @PostConstruct
    private void init() {
        log.info("Running Configurations");


        setupUpdatesTableData();
    }

    private void setupUpdatesTableData() {
        // Check if Updates table already has data
        if (updatesRepo.count() <= 0) {
            // Initial table data is not available
            // Add | init table data
            for(String tag: ResourceHelper.GET_TAGS()){
                updatesRepo.save(new TableUpdate(tag));
            }
        }
    }

}
