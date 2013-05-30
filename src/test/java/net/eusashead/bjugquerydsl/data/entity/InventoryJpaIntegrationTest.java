package net.eusashead.bjugquerydsl.data.entity;

import java.util.List;
import java.util.Date;

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

import net.eusashead.bjugquerydsl.data.entity.Inventory;
import net.eusashead.bjugquerydsl.data.entity.InventoryTestUtils;
import net.eusashead.bjugquerydsl.data.entity.StockKeepingUnit;
import net.eusashead.bjugquerydsl.data.entity.StockKeepingUnitTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "commerce")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class InventoryJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("InventoryBeforeCreateDataSet.xml")
    @ExpectedDataSet("InventoryAfterCreateDataSet.xml")
    public void testCreateInventory() throws Exception {
    
        // Create new entity
        Inventory create = new Inventory();
                    
        // Set identity
        create.setInventoryId(Integer.valueOf(1));

        // Populate simple properties
        create.setCreated(new Date(1230771661000L));
        create.setLabel("s");
                
        // Populate dependencies
        StockKeepingUnit stockKeepingUnit = (StockKeepingUnit)entityManager.getReference(StockKeepingUnit.class, StockKeepingUnitTestUtils.getDefaultIdentity());
        create.setStockKeepingUnit(stockKeepingUnit);

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("InventoryBeforeUpdateDataSet.xml")
    @ExpectedDataSet("InventoryAfterUpdateDataSet.xml")
    public void testUpdateInventory() throws Exception {
                    
        // Load entity and modify
        Inventory result = (Inventory)entityManager.find(Inventory.class, InventoryTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setCreated(new Date(1233540122000L));
        result.setLabel("t");

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("InventoryBeforeDeleteDataSet.xml")
    @ExpectedDataSet("InventoryAfterDeleteDataSet.xml")
    public void testRemoveInventory() throws Exception {
                    
        // Delete
        Inventory result = (Inventory)entityManager.find(Inventory.class, InventoryTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("InventoryFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllInventory() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from Inventory");
                    
        // Get results 
        List<Inventory> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("InventoryFindDataSet.xml")
    public void testFindInventoryByIdentity() throws Exception {
                    
        // Get object 
        Inventory result  = (Inventory)entityManager.find(Inventory.class, InventoryTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals(new Date(1230771661000L), result.getCreated());
        Assert.assertEquals("s", result.getLabel());

    }
    
}