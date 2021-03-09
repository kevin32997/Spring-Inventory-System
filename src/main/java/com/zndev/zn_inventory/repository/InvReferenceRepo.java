package com.zndev.zn_inventory.repository;

import com.zndev.zn_inventory.models.main.Brand;
import com.zndev.zn_inventory.models.main.InventoryActivityReference;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvReferenceRepo extends JpaRepository<InventoryActivityReference,Integer> {

    @Query(value="SELECT * FROM inv_activity_reference WHERE reference LIKE ?1 OR consignee LIKE ?1 LIMIT ?2", nativeQuery = true)
    List<InventoryActivityReference> search(String search, int limit);

    InventoryActivityReference findTopByOrderByIdDesc();

    List<InventoryActivityReference> findAllByLocationOrLocationFrom(int locationId, int locationFromId, Pageable pageable);

    long countByLocationOrLocationFrom(int locationId, int locationFromId);
}
