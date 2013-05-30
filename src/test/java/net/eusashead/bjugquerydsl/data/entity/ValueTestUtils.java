package net.eusashead.bjugquerydsl.data.entity;

import net.eusashead.bjugquerydsl.data.entity.Attribute;
import net.eusashead.bjugquerydsl.data.entity.AttributeTestUtils;

public class ValueTestUtils {
    
    public static Value createDefaultInstance() {
    
        // Create new entity
        Value create = new Value();
                    
        // Set identity
        create.setValueId(getDefaultIdentity());

        // Populate simple properties
        create.setName("s");
                
        // Populate dependencies
        Attribute attribute = AttributeTestUtils.createDefaultInstance();
        create.setAttribute(attribute);

        // Return instance
        return create;

    }
    
    public static Integer getDefaultIdentity() {
    
        return Integer.valueOf(1);                
    
    }

    
    
    
    public static Value createRandomInstance() {
    
        // create new entity
        Value create = new Value();
                    
        // set identity
        create.setValueId(getRandomIdentity());
        
        // populate simple properties
        create.setName("t");
                
        // populate dependencies
        Attribute attribute = AttributeTestUtils.createRandomInstance();
        create.setAttribute(attribute);

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
    public static Value clone(Value value){

        Value _value = new Value();
        
        if (value.getValueId() != null) {
            _value.setValueId(value.getValueId());   
        }
        if (value.getName() != null) {
            _value.setName(value.getName());
        }
        if (value.getAttribute() != null) {
            _value.setAttribute(AttributeTestUtils.clone(value.getAttribute()));
        }

        return _value;
    }
    
}