package com.angelopicc.inventory.inventorytracking.services;

import org.springframework.stereotype.Service;

@Service
public interface CheckingService<T> {

    boolean checkDuplicates(T entity);
    
}
