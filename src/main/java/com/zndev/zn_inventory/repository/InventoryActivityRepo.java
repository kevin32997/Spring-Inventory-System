package com.zndev.zn_inventory.repository;

import com.zndev.zn_inventory.models.main.InventoryActivity;
import com.zndev.zn_inventory.models.main.InventoryActivityReference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryActivityRepo extends JpaRepository<InventoryActivity,Integer> {

    InventoryActivity findTopByOrderByIdDesc();
}
