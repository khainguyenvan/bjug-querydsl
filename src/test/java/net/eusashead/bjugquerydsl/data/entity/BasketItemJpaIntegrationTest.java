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

import net.eusashead.bjugquerydsl.data.entity.BasketItem;
import net.eusashead.bjugquerydsl.data.entity.BasketItemTestUtils;
import net.eusashead.bjugquerydsl.data.entity.Basket;
import net.eusashead.bjugquerydsl.data.entity.BasketTestUtils;
import net.eusashead.bjugquerydsl.data.entity.Inventory;
import net.eusashead.bjugquerydsl.data.entity.InventoryTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "commerce")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class BasketItemJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("BasketItemBeforeCreateDataSet.xml")
    @ExpectedDataSet("BasketItemAfterCreateDataSet.xml")
    public void testCreateBasketItem() throws Exception {
    
        // Create new entity
        BasketItem create = new BasketItem();
                    
        // Set identity
        create.setBasketItemId(Integer.valueOf(1));

        // Populate simple properties
        create.setAdded(new Date(1230771661000L));
        create.setSaveForLater(Boolean.TRUE);
                
        // Populate dependencies
        Inventory inventory = (Inventory)entityManager.getReference(Inventory.class, InventoryTestUtils.getDefaultIdentity());
        create.setInventory(inventory);
        Basket basket = (Basket)entityManager.getReference(Basket.class, BasketTestUtils.getDefaultIdentity());
        create.setBasket(basket);

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("BasketItemBeforeUpdateDataSet.xml")
    @ExpectedDataSet("BasketItemAfterUpdateDataSet.xml")
    public void testUpdateBasketItem() throws Exception {
                    
        // Load entity and modify
        BasketItem result = (BasketItem)entityManager.find(BasketItem.class, BasketItemTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setAdded(new Date(1233540122000L));
        result.setSaveForLater(Boolean.FALSE);

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("BasketItemBeforeDeleteDataSet.xml")
    @ExpectedDataSet("BasketItemAfterDeleteDataSet.xml")
    public void testRemoveBasketItem() throws Exception {
                    
        // Delete
        BasketItem result = (BasketItem)entityManager.find(BasketItem.class, BasketItemTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("BasketItemFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllBasketItem() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from BasketItem");
                    
        // Get results 
        List<BasketItem> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("BasketItemFindDataSet.xml")
    public void testFindBasketItemByIdentity() throws Exception {
                    
        // Get object 
        BasketItem result  = (BasketItem)entityManager.find(BasketItem.class, BasketItemTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals(new Date(1230771661000L), result.getAdded());
        Assert.assertEquals(Boolean.TRUE, result.getSaveForLater());

    }
    
}