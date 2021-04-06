package com.zndev.zn_inventory.repository;

import com.zndev.zn_inventory.models.main.Inventory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepo extends JpaRepository<Inventory, Integer> {

    List<Inventory> findByItemId(int itemId);

    List<Inventory> findByLocationId(int locationId);

    List<Inventory> findByItemIdAndLocationId(int itemId, int locationId);

    List<Inventory> findAllByLocationId(int locationId, Pageable pageable);

    List<Inventory> findByItem_nameContainingAndLocationId(String name, int locationId, Pageable pageable);

    //Inventory findByLocationIdAndItem_nameOrItem_serialNumber(int location_id,String item_name,String serial_number);


    // Counts
    long countByLocationId(int locationId);
    long countByItemId(int itemId);
}
