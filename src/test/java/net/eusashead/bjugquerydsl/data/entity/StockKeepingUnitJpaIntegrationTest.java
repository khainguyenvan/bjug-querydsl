package net.eusashead.bjugquerydsl.data.entity;

import java.util.List;
import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import net.eusashead.bjugquerydsl.data.entity.StockKeepingUnit;
import net.eusashead.bjugquerydsl.data.entity.StockKeepingUnitTestUtils;
import net.eusashead.bjugquerydsl.data.entity.Product;
import net.eusashead.bjugquerydsl.data.entity.ProductTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "commerce")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class StockKeepingUnitJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("StockKeepingUnitBeforeCreateDataSet.xml")
    @ExpectedDataSet("StockKeepingUnitAfterCreateDataSet.xml")
    public void testCreateStockKeepingUnit() throws Exception {
    
        // Create new entity
        StockKeepingUnit create = new StockKeepingUnit();
                    
        // Set identity
        create.setSkuId(Integer.valueOf(1));

        // Populate simple properties
        create.setName("s");
        create.setPrice(new BigDecimal("10000000.01"));
                
        // Populate dependencies
        Product product = (Product)entityManager.getReference(Product.class, ProductTestUtils.getDefaultIdentity());
        create.setProduct(product);

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("StockKeepingUnitBeforeUpdateDataSet.xml")
    @ExpectedDataSet("StockKeepingUnitAfterUpdateDataSet.xml")
    public void testUpdateStockKeepingUnit() throws Exception {
                    
        // Load entity and modify
        StockKeepingUnit result = (StockKeepingUnit)entityManager.find(StockKeepingUnit.class, StockKeepingUnitTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setName("t");
        result.setPrice(new BigDecimal("20000000.02"));

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("StockKeepingUnitBeforeDeleteDataSet.xml")
    @ExpectedDataSet("StockKeepingUnitAfterDeleteDataSet.xml")
    public void testRemoveStockKeepingUnit() throws Exception {
                    
        // Delete
        StockKeepingUnit result = (StockKeepingUnit)entityManager.find(StockKeepingUnit.class, StockKeepingUnitTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("StockKeepingUnitFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllStockKeepingUnit() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from StockKeepingUnit");
                    
        // Get results 
        List<StockKeepingUnit> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("StockKeepingUnitFindDataSet.xml")
    public void testFindStockKeepingUnitByIdentity() throws Exception {
                    
        // Get object 
        StockKeepingUnit result  = (StockKeepingUnit)entityManager.find(StockKeepingUnit.class, StockKeepingUnitTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals("s", result.getName());
        Assert.assertEquals(new BigDecimal("10000000.01"), result.getPrice());

    }
    
}