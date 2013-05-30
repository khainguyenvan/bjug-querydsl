package net.eusashead.bjugquerydsl.data.entity;

import java.util.Date;
import net.eusashead.bjugquerydsl.data.entity.Basket;
import net.eusashead.bjugquerydsl.data.entity.BasketTestUtils;
import net.eusashead.bjugquerydsl.data.entity.Inventory;
import net.eusashead.bjugquerydsl.data.entity.InventoryTestUtils;

public class BasketItemTestUtils {
    
    public static BasketItem createDefaultInstance() {
    
        // Create new entity
        BasketItem create = new BasketItem();
                    
        // Set identity
        create.setBasketItemId(getDefaultIdentity());

        // Populate simple properties
        create.setAdded(new Date(1230771661000L));
        create.setSaveForLater(Boolean.TRUE);
                
        // Populate dependencies
        Inventory inventory = InventoryTestUtils.createDefaultInstance();
        create.setInventory(inventory);
        Basket basket = BasketTestUtils.createDefaultInstance();
        create.setBasket(basket);

        // Return instance
        return create;

    }
    
    public static Integer getDefaultIdentity() {
    
        return Integer.valueOf(1);                
    
    }

    
    
    
    public static BasketItem createRandomInstance() {
    
        // create new entity
        BasketItem create = new BasketItem();
                    
        // set identity
        create.setBasketItemId(getRandomIdentity());
        
        // populate simple properties
        create.setAdded(new Date(1233540122000L));
        create.setSaveForLater(Boolean.FALSE);
                
        // populate dependencies
        Inventory inventory = InventoryTestUtils.createRandomInstance();
        create.setInventory(inventory);
        Basket basket = BasketTestUtils.createRandomInstance();
        create.setBasket(basket);

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
    public static BasketItem clone(BasketItem basketItem){

        BasketItem _basketItem = new BasketItem();
        
        if (basketItem.getBasketItemId() != null) {
            _basketItem.setBasketItemId(basketItem.getBasketItemId());   
        }
        if (basketItem.getAdded() != null) {
            _basketItem.setAdded(new Date(basketItem.getAdded().getTime()));
        }
        if (basketItem.getSaveForLater() != null) {
            _basketItem.setSaveForLater(basketItem.getSaveForLater());
        }
        if (basketItem.getBasket() != null) {
            _basketItem.setBasket(BasketTestUtils.clone(basketItem.getBasket()));
        }
        if (basketItem.getInventory() != null) {
            _basketItem.setInventory(InventoryTestUtils.clone(basketItem.getInventory()));
        }

        return _basketItem;
    }
    
}