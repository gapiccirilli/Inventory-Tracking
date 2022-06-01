package com.angelopicc.inventory.inventorytracking.rest;

import java.util.Optional;

import com.angelopicc.inventory.inventorytracking.domain.Location;
import com.angelopicc.inventory.inventorytracking.repositories.LocationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/locations")
public class RestLocationsController {
    
    private LocationRepository locRepo;

    @Autowired
    public RestLocationsController(LocationRepository locRepo) {
        this.locRepo = locRepo;
    }

    @GetMapping(params = "locations")
    public Iterable<Location> allItems() {
        
        return locRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> locationById(@PathVariable("id") Long id) {
        
        Optional<Location> optLocation = locRepo.findById(id);

        if (optLocation.isPresent())
            return new ResponseEntity<>(optLocation.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // add OAuth 2 Security

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Location saveLocation(@RequestBody Location location) {

        return locRepo.save(location);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable("id") Long locationId) {
        
        locRepo.deleteById(locationId);
    }
}
