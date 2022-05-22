package com.angelopicc.inventory.inventorytracking.services;

import java.util.Optional;

import com.angelopicc.inventory.inventorytracking.domain.Item;
import com.angelopicc.inventory.inventorytracking.domain.Location;
import com.angelopicc.inventory.inventorytracking.repositories.ItemRepository;
import com.angelopicc.inventory.inventorytracking.repositories.LocationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemCheckingService implements CheckingService<Item> {

    private ItemRepository itemRepo;
    private LocationRepository locRepo;
    private Location location;

    @Autowired
    public ItemCheckingService(ItemRepository itemRepo, LocationRepository locRepo) {

        this.locRepo = locRepo;
        this.itemRepo = itemRepo;
    }


    @Override
    public boolean checkDuplicates(Item entity) {
        Optional<Location> optLocation = locRepo.findById(location.getId());
        Location aLocation = optLocation.get();

        for (Item item : aLocation.getItems())
        {
            if (item.getItemName().equalsIgnoreCase(entity.getItemName()))
                return true;
        }
        return false;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
