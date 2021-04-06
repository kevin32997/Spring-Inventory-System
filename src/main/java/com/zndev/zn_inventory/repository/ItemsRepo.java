package com.zndev.zn_inventory.repository;

import com.zndev.zn_inventory.models.main.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemsRepo extends JpaRepository<Item, Integer> {

    /*
    @Query(value = "SELECT * FROM items WHERE name LIKE ?1 OR code LIKE ?1 OR model LIKE ?1 OR serial_number LIKE ?1 LIMIT ?2", nativeQuery = true)
    List<Item> search(String search, int limit,);
     */

    List<Item> findAllByNameContainsOrModelContains(String name,String model, Pageable pageable);

    List<Item> findAllByBrandId(int brandId, Pageable pageable);

    List<Item> findAllByTypeId(int typeId, Pageable pageable);

    long countByBrandId(int brandId);

    long countByTypeId(int typeId);
}
