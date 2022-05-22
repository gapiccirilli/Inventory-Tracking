package com.angelopicc.inventory.inventorytracking.controllers;


import com.angelopicc.inventory.inventorytracking.domain.Item;
import com.angelopicc.inventory.inventorytracking.domain.Location;
import com.angelopicc.inventory.inventorytracking.repositories.ItemRepository;
import com.angelopicc.inventory.inventorytracking.repositories.LocationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    
    private LocationRepository locationRepo;

    @Autowired
    public HomeController(LocationRepository locationRepo) {
        this.locationRepo = locationRepo;
    }

    public HomeController() {
    }

    @GetMapping
    public String home() {
        return "home";
    }

    @ModelAttribute
    public void fetchLocations(Model model) {
        Iterable<Location> locations = locationRepo.findAll();

        model.addAttribute("locations", locations);
    }
}
