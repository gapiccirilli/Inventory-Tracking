package com.angelopicc.inventory.inventorytracking.services;

import com.angelopicc.inventory.inventorytracking.domain.Location;
import com.angelopicc.inventory.inventorytracking.repositories.LocationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationCheckingService implements CheckingService<Location> {

    private LocationRepository locRepo;

    @Autowired
    public LocationCheckingService(LocationRepository locRepo) {

        this.locRepo = locRepo;
    }


    @Override
    public boolean checkDuplicates(Location entity) {

        Iterable<Location> locations = locRepo.findAll();

        for (Location location : locations) {
            if (entity.getLocationName().equalsIgnoreCase(location.getLocationName()))
                return true;
        }

        return false;
    }
}
