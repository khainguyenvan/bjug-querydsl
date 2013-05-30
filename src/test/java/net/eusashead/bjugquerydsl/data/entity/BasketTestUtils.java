package net.eusashead.bjugquerydsl.data.entity;

import net.eusashead.bjugquerydsl.data.entity.Customer;
import net.eusashead.bjugquerydsl.data.entity.CustomerTestUtils;

public class BasketTestUtils {
    
    public static Basket createDefaultInstance() {
    
        // Create new entity
        Basket create = new Basket();
                    
        // Set identity
        create.setBasketId(getDefaultIdentity());

        // Populate simple properties
                
        // Populate dependencies
        Customer customer = CustomerTestUtils.createDefaultInstance();
        create.setCustomer(customer);

        // Return instance
        return create;

    }
    
    public static Integer getDefaultIdentity() {
    
        return Integer.valueOf(1);                
    
    }

    
    
    
    public static Basket createRandomInstance() {
    
        // create new entity
        Basket create = new Basket();
                    
        // set identity
        create.setBasketId(getRandomIdentity());
        
        // populate simple properties
                
        // populate dependencies
        Customer customer = CustomerTestUtils.createRandomInstance();
        create.setCustomer(customer);

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
    public static Basket clone(Basket basket){

        Basket _basket = new Basket();
        
        if (basket.getBasketId() != null) {
            _basket.setBasketId(basket.getBasketId());   
        }
        if (basket.getCustomer() != null) {
            _basket.setCustomer(CustomerTestUtils.clone(basket.getCustomer()));
        }

        return _basket;
    }
    
}