package com.parth.billingsoftware.repository;

import com.parth.billingsoftware.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    // Fetch item by its String ID (UUID)
    Optional<ItemEntity> findByItemId(String itemId);


    // Count items by category ID
    Integer countAllByCategoryId(Long categoryId);
}
