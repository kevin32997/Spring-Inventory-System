package com.zndev.zn_inventory.repository;

import com.zndev.zn_inventory.models.other.TableUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdatesRepo extends JpaRepository<TableUpdate,Integer> {

        TableUpdate findByCode(String code);
}
