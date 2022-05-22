package com.angelopicc.inventory.inventorytracking.repositories;

import com.angelopicc.inventory.inventorytracking.domain.Item;

import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
    
}
