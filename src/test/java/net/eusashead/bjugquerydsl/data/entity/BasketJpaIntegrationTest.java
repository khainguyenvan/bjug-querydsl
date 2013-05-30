package net.eusashead.bjugquerydsl.data.entity;

import java.util.List;

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

import net.eusashead.bjugquerydsl.data.entity.Basket;
import net.eusashead.bjugquerydsl.data.entity.BasketTestUtils;
import net.eusashead.bjugquerydsl.data.entity.Customer;
import net.eusashead.bjugquerydsl.data.entity.CustomerTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "commerce")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class BasketJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("BasketBeforeCreateDataSet.xml")
    @ExpectedDataSet("BasketAfterCreateDataSet.xml")
    public void testCreateBasket() throws Exception {
    
        // Create new entity
        Basket create = new Basket();
                    
        // Set identity
        create.setBasketId(Integer.valueOf(1));
                
        // Populate dependencies
        Customer customer = (Customer)entityManager.getReference(Customer.class, CustomerTestUtils.getDefaultIdentity());
        create.setCustomer(customer);

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("BasketBeforeUpdateDataSet.xml")
    @ExpectedDataSet("BasketAfterUpdateDataSet.xml")
    public void testUpdateBasket() throws Exception {
                    
        // Load entity and modify
        Basket result = (Basket)entityManager.find(Basket.class, BasketTestUtils.getDefaultIdentity());

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("BasketBeforeDeleteDataSet.xml")
    @ExpectedDataSet("BasketAfterDeleteDataSet.xml")
    public void testRemoveBasket() throws Exception {
                    
        // Delete
        Basket result = (Basket)entityManager.find(Basket.class, BasketTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("BasketFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllBasket() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from Basket");
                    
        // Get results 
        List<Basket> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("BasketFindDataSet.xml")
    public void testFindBasketByIdentity() throws Exception {
                    
        // Get object 
        Basket result  = (Basket)entityManager.find(Basket.class, BasketTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

    }
    
}