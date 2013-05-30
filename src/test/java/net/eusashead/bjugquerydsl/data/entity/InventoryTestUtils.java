package net.eusashead.bjugquerydsl.data.entity;

import java.util.Date;
import net.eusashead.bjugquerydsl.data.entity.StockKeepingUnit;
import net.eusashead.bjugquerydsl.data.entity.StockKeepingUnitTestUtils;

public class InventoryTestUtils {
    
    public static Inventory createDefaultInstance() {
    
        // Create new entity
        Inventory create = new Inventory();
                    
        // Set identity
        create.setInventoryId(getDefaultIdentity());

        // Populate simple properties
        create.setCreated(new Date(1230771661000L));
        create.setLabel("s");
        create.setSold(new Date(1230771661000L));
                
        // Populate dependencies
        StockKeepingUnit stockKeepingUnit = StockKeepingUnitTestUtils.createDefaultInstance();
        create.setStockKeepingUnit(stockKeepingUnit);

        // Return instance
        return create;

    }
    
    public static Integer getDefaultIdentity() {
    
        return Integer.valueOf(1);                
    
    }

    
    
    
    public static Inventory createRandomInstance() {
    
        // create new entity
        Inventory create = new Inventory();
                    
        // set identity
        create.setInventoryId(getRandomIdentity());
        
        // populate simple properties
        create.setCreated(new Date(1233540122000L));
        create.setLabel("t");
        create.setSold(new Date(1233540122000L));
                
        // populate dependencies
        StockKeepingUnit stockKeepingUnit = StockKeepingUnitTestUtils.createRandomInstance();
        create.setStockKeepingUnit(stockKeepingUnit);

        // return instance
        return create;

    }
    
    public static Integer getRandomIdentity() {
    
        return Integer.valueOf(2);                
    
    }

    /**
    * Creates a deep copy
    * of the object including 
    * all dependent fields. 
    */
    public static Inventory clone(Inventory inventory){

        Inventory _inventory = new Inventory();
        
        if (inventory.getInventoryId() != null) {
            _inventory.setInventoryId(inventory.getInventoryId());   
        }
        if (inventory.getCreated() != null) {
            _inventory.setCreated(new Date(inventory.getCreated().getTime()));
        }
        if (inventory.getLabel() != null) {
            _inventory.setLabel(inventory.getLabel());
        }
        if (inventory.getSold() != null) {
            _inventory.setSold(new Date(inventory.getSold().getTime()));
        }
        if (inventory.getStockKeepingUnit() != null) {
            _inventory.setStockKeepingUnit(StockKeepingUnitTestUtils.clone(inventory.getStockKeepingUnit()));
        }

        return _inventory;
    }
    
}