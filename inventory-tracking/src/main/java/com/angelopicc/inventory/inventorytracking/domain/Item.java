package com.angelopicc.inventory.inventorytracking.domain;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;


@Entity
public class Item {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String itemName;
    private String description;
    private int quantity;

    @Transient
    private Long locationId;
    

    public Item(String itemName, String description, int quantity) {
        this.itemName = itemName;
        this.description = description;
        this.quantity = quantity;
    }
    
    public Item() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public boolean isNull() {
        if (itemName.isEmpty() || description.isEmpty() || quantity == 0)
            return true;
        else
            return false;

    }

    @Override
    public String toString() {
        return "Item [description=" + description + ", itemName=" + itemName + ", location=" + ", quantity="
                + quantity + "]";
    }

    
}
