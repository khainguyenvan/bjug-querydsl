package net.eusashead.bjugquerydsl.data.entity;


public class ProductTestUtils {
    
    public static Product createDefaultInstance() {
    
        // Create new entity
        Product create = new Product();
                    
        // Set identity
        create.setProductId(getDefaultIdentity());

        // Populate simple properties
        create.setName("s");

        // Return instance
        return create;

    }
    
    public static Integer getDefaultIdentity() {
    
        return Integer.valueOf(1);                
    
    }

    
    
    
    public static Product createRandomInstance() {
    
        // create new entity
        Product create = new Product();
                    
        // set identity
        create.setProductId(getRandomIdentity());
        
        // populate simple properties
        create.setName("t");

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
    public static Product clone(Product product){

        Product _product = new Product();
        
        if (product.getProductId() != null) {
            _product.setProductId(product.getProductId());   
        }
        if (product.getName() != null) {
            _product.setName(product.getName());
        }

        return _product;
    }
    
}