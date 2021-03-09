package com.zndev.zn_inventory.repository;

import com.zndev.zn_inventory.models.main.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypesRepo extends JpaRepository<Type,Integer> {

    @Query(value="SELECT * FROM type WHERE name LIKE ?1 LIMIT ?2", nativeQuery = true)
    List<Type> search(String search, int limit);

}
