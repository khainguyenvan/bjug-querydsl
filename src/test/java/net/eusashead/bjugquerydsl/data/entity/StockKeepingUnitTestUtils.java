package net.eusashead.bjugquerydsl.data.entity;

import java.math.BigDecimal;
import net.eusashead.bjugquerydsl.data.entity.Product;
import net.eusashead.bjugquerydsl.data.entity.ProductTestUtils;

public class StockKeepingUnitTestUtils {
    
    public static StockKeepingUnit createDefaultInstance() {
    
        // Create new entity
        StockKeepingUnit create = new StockKeepingUnit();
                    
        // Set identity
        create.setSkuId(getDefaultIdentity());

        // Populate simple properties
        create.setName("s");
        create.setPrice(new BigDecimal("10000000.01"));
                
        // Populate dependencies
        Product product = ProductTestUtils.createDefaultInstance();
        create.setProduct(product);

        // Return instance
        return create;

    }
    
    public static Integer getDefaultIdentity() {
    
        return Integer.valueOf(1);                
    
    }

    
    
    
    public static StockKeepingUnit createRandomInstance() {
    
        // create new entity
        StockKeepingUnit create = new StockKeepingUnit();
                    
        // set identity
        create.setSkuId(getRandomIdentity());
        
        // populate simple properties
        create.setName("t");
        create.setPrice(new BigDecimal("20000000.02"));
                
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
    public static StockKeepingUnit clone(StockKeepingUnit stockKeepingUnit){

        StockKeepingUnit _stockKeepingUnit = new StockKeepingUnit();
        
        if (stockKeepingUnit.getSkuId() != null) {
            _stockKeepingUnit.setSkuId(stockKeepingUnit.getSkuId());   
        }
        if (stockKeepingUnit.getName() != null) {
            _stockKeepingUnit.setName(stockKeepingUnit.getName());
        }
        if (stockKeepingUnit.getPrice() != null) {
            _stockKeepingUnit.setPrice(new BigDecimal(stockKeepingUnit.getPrice().toPlainString()));
        }
        if (stockKeepingUnit.getProduct() != null) {
            _stockKeepingUnit.setProduct(ProductTestUtils.clone(stockKeepingUnit.getProduct()));
        }

        return _stockKeepingUnit;
    }
    
}