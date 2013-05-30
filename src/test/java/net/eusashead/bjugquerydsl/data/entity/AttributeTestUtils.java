package net.eusashead.bjugquerydsl.data.entity;

import net.eusashead.bjugquerydsl.data.entity.Product;
import net.eusashead.bjugquerydsl.data.entity.ProductTestUtils;

public class AttributeTestUtils {
    
    public static Attribute createDefaultInstance() {
    
        // Create new entity
        Attribute create = new Attribute();
                    
        // Set identity
        create.setAttributeId(getDefaultIdentity());

        // Populate simple properties
        create.setName("s");
                
        // Populate dependencies
        Product product = ProductTestUtils.createDefaultInstance();
        create.setProduct(product);

        // Return instance
        return create;

    }
    
    public static Integer getDefaultIdentity() {
    
        return Integer.valueOf(1);                
    
    }

    
    
    
    public static Attribute createRandomInstance() {
    
        // create new entity
        Attribute create = new Attribute();
                    
        // set identity
        create.setAttributeId(getRandomIdentity());
        
        // populate simple properties
        create.setName("t");
                
        // populate dependencies
        Product product = ProductTestUtils.createRandomInstance();
        create.setProduct(product);

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
    public static Attribute clone(Attribute attribute){

        Attribute _attribute = new Attribute();
        
        if (attribute.getAttributeId() != null) {
            _attribute.setAttributeId(attribute.getAttributeId());   
        }
        if (attribute.getName() != null) {
            _attribute.setName(attribute.getName());
        }
        if (attribute.getProduct() != null) {
            _attribute.setProduct(ProductTestUtils.clone(attribute.getProduct()));
        }

        return _attribute;
    }
    
}