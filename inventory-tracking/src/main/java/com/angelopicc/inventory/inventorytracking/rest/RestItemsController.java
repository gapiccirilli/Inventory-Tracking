package com.angelopicc.inventory.inventorytracking.rest;

import java.util.Optional;

import com.angelopicc.inventory.inventorytracking.domain.Item;
import com.angelopicc.inventory.inventorytracking.repositories.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
public class RestItemsController {
    
    private ItemRepository itemRepo;

    @Autowired
    public RestItemsController(ItemRepository itemRepo) {
        this.itemRepo = itemRepo;
    }

    @GetMapping(params = "items")
    public Iterable<Item> allItems() {
        
        return itemRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> itemById(@PathVariable("id") Long id) {
        
        Optional<Item> optItem = itemRepo.findById(id);

        if (optItem.isPresent())
            return new ResponseEntity<>(optItem.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
