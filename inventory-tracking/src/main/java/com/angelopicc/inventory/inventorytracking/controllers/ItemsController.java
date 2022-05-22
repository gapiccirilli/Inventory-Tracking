package com.angelopicc.inventory.inventorytracking.controllers;

import java.util.List;
import java.util.Optional;

import com.angelopicc.inventory.inventorytracking.domain.Item;
import com.angelopicc.inventory.inventorytracking.domain.Location;
import com.angelopicc.inventory.inventorytracking.repositories.ItemRepository;
import com.angelopicc.inventory.inventorytracking.repositories.LocationRepository;
import com.angelopicc.inventory.inventorytracking.services.CheckingService;
import com.angelopicc.inventory.inventorytracking.services.ItemCheckingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"itemsToLocation", "itemForLocation"})
@RequestMapping("/inventory")
public class ItemsController {
    
    private ItemRepository repo;
    private LocationRepository locRepo;
    private ItemCheckingService itemCheckingService;
    private CheckingService<Location> locationCheckingService;

    private Long currentLocationId;
    
    @Autowired
    public ItemsController(ItemRepository repo, LocationRepository locRepo, ItemCheckingService itemCheckingService, 
    CheckingService<Location> locationCheckingService) {
        this.repo = repo;
        this.locRepo = locRepo;
        this.itemCheckingService = itemCheckingService;
        this.locationCheckingService = locationCheckingService;
    }

    public ItemsController() {
    }

    @ModelAttribute(name="newItem")
    public Item setItem() {
        
        return new Item();
    }

    @GetMapping
    public String getAddLocationPage(Model model) {
        
        model.addAttribute("locationCreate", new Location());
        return "createlocation";
    }

    @PostMapping
    public String getAddInventoryPage(Long id, Model model) {

        Optional<Location> optLocation = locRepo.findById(id);
        Location location = optLocation.get();
        Item newItem = new Item();

        model.addAttribute("itemsToLocation", location);
        model.addAttribute("itemForLocation", newItem);

        return "create";
    }

    @PostMapping("/view")
    public String view(@RequestParam Long id, Model model) {

        Optional<Location> optLocation = locRepo.findById(id);
        Location location = optLocation.get();
        // for getting the current location
        currentLocationId = id;

        List<Item> items = location.getItems();

        model.addAttribute("itemList", items);

        return "itemlist";
    }

    @PostMapping("/itemid")
    public String update(@RequestParam Long id, Model model) {
        Optional<Item> optItem = repo.findById(id);
        Item anItem = optItem.get();

        model.addAttribute("itemForLocation", anItem);
        return "update";
    }

    @PostMapping("/itemid-d")
    public String delete(@RequestParam Long id) {

        Optional<Item> optItem = repo.findById(id);
        Item anItem = optItem.get();

        try {
            repo.delete(anItem);
            return "deletesuccess";
        }
        catch(IllegalArgumentException e) {
            return "deletefail";
        }
    }


    @PostMapping("/item")
    public String saveItem(Item item, Model model) {
        if (item.isNull())
                return "failure";

        Location location;
        try {
            location = (Location)model.getAttribute("itemsToLocation");

            // new code

            itemCheckingService.setLocation(location);
            boolean checkMatch = itemCheckingService.checkDuplicates(item);

            if(checkMatch)
                return "duplicateitem";

            // ends here 
            location.addItem(item);
            locRepo.save(location);
        }
        catch(NullPointerException npe) {
            
        }


        return "itemsuccess";
    }

    @PostMapping("/item/update")
    public String updateItem(@RequestParam Long id, Item item, Model model) {
        
        Optional<Item> optItem = repo.findById(id);
        Item anItem = optItem.get();

        anItem.setItemName(item.getItemName());
        anItem.setDescription(item.getDescription());
        anItem.setQuantity(item.getQuantity());

        try {
            repo.save(anItem);
        }
        catch(IllegalArgumentException e) {
            return "failure";
        }

        return "itemsuccess";
    }

    @PostMapping("/location")
    public String addLocation(Location locationCreate) {

        if (locationCheckingService.checkDuplicates(locationCreate)) {
            return "duplicate";
        }

        try {
            locRepo.save(locationCreate);
        }
        catch(IllegalArgumentException e) {
            return "failure";
        }
        
        
        return "success";
    }
}
