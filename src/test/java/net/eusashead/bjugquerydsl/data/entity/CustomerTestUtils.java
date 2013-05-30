package net.eusashead.bjugquerydsl.data.entity;

import java.util.Date;

public class CustomerTestUtils {
    
    public static Customer createDefaultInstance() {
    
        // Create new entity
        Customer create = new Customer();
                    
        // Set identity
        create.setCustomerId(getDefaultIdentity());

        // Populate simple properties
        create.setEmail("s");
        create.setFirstName("s");
        create.setJoined(new Date(1230771661000L));
        create.setLastName("s");

        // Return instance
        return create;

    }
    
    public static Integer getDefaultIdentity() {
    
        return Integer.valueOf(1);                
    
    }

    
    
    
    public static Customer createRandomInstance() {
    
        // create new entity
        Customer create = new Customer();
                    
        // set identity
        create.setCustomerId(getRandomIdentity());
        
        // populate simple properties
        create.setEmail("t");
        create.setFirstName("t");
        create.setJoined(new Date(1233540122000L));
        create.setLastName("t");

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
    public static Customer clone(Customer customer){

        Customer _customer = new Customer();
        
        if (customer.getCustomerId() != null) {
            _customer.setCustomerId(customer.getCustomerId());   
        }
        if (customer.getEmail() != null) {
            _customer.setEmail(customer.getEmail());
        }
        if (customer.getFirstName() != null) {
            _customer.setFirstName(customer.getFirstName());
        }
        if (customer.getJoined() != null) {
            _customer.setJoined(new Date(customer.getJoined().getTime()));
        }
        if (customer.getLastName() != null) {
            _customer.setLastName(customer.getLastName());
        }

        return _customer;
    }
    
}