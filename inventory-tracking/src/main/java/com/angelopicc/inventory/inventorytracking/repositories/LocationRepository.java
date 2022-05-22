package com.angelopicc.inventory.inventorytracking.repositories;

import com.angelopicc.inventory.inventorytracking.domain.Location;

import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
    
}
