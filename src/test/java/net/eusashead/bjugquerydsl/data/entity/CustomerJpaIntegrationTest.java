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

import net.eusashead.bjugquerydsl.data.entity.Customer;
import net.eusashead.bjugquerydsl.data.entity.CustomerTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "commerce")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class CustomerJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("CustomerBeforeCreateDataSet.xml")
    @ExpectedDataSet("CustomerAfterCreateDataSet.xml")
    public void testCreateCustomer() throws Exception {
    
        // Create new entity
        Customer create = new Customer();
                    
        // Set identity
        create.setCustomerId(Integer.valueOf(1));

        // Populate simple properties
        create.setEmail("s");
        create.setFirstName("s");
        create.setJoined(new Date(1230771661000L));
        create.setLastName("s");

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("CustomerBeforeUpdateDataSet.xml")
    @ExpectedDataSet("CustomerAfterUpdateDataSet.xml")
    public void testUpdateCustomer() throws Exception {
                    
        // Load entity and modify
        Customer result = (Customer)entityManager.find(Customer.class, CustomerTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setEmail("t");
        result.setFirstName("t");
        result.setJoined(new Date(1233540122000L));
        result.setLastName("t");

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("CustomerBeforeDeleteDataSet.xml")
    @ExpectedDataSet("CustomerAfterDeleteDataSet.xml")
    public void testRemoveCustomer() throws Exception {
                    
        // Delete
        Customer result = (Customer)entityManager.find(Customer.class, CustomerTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("CustomerFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllCustomer() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from Customer");
                    
        // Get results 
        List<Customer> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("CustomerFindDataSet.xml")
    public void testFindCustomerByIdentity() throws Exception {
                    
        // Get object 
        Customer result  = (Customer)entityManager.find(Customer.class, CustomerTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals("s", result.getEmail());
        Assert.assertEquals("s", result.getFirstName());
        Assert.assertEquals(new Date(1230771661000L), result.getJoined());
        Assert.assertEquals("s", result.getLastName());

    }
    
}