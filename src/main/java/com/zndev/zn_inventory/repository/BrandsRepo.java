package com.zndev.zn_inventory.repository;

import com.zndev.zn_inventory.models.main.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BrandsRepo extends JpaRepository<Brand, Integer> {


    @Query(value="SELECT * FROM brands WHERE name LIKE ?1 LIMIT ?2", nativeQuery = true)
    List<Brand> search(String search, int limit);

}
