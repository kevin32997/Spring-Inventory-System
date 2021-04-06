package com.zndev.zn_inventory.repository;

import com.zndev.zn_inventory.models.main.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reactor.core.publisher.Flux;

import java.util.List;

public interface LocationsRepo extends JpaRepository<Location, Integer> {

    @Query(value="SELECT * FROM locations WHERE name LIKE ?1 LIMIT ?2", nativeQuery = true)
    List<Location> search(String search, int limit);
}
