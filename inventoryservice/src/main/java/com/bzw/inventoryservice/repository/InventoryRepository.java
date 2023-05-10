package com.bzw.inventoryservice.repository;

import com.bzw.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    List<Inventory> findBySkucodeIn(List<String> skucode);
}
