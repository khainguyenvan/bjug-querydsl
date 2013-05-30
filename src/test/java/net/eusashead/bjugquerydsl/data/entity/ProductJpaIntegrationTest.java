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

import net.eusashead.bjugquerydsl.data.entity.Product;
import net.eusashead.bjugquerydsl.data.entity.ProductTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "commerce")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ProductJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("ProductBeforeCreateDataSet.xml")
    @ExpectedDataSet("ProductAfterCreateDataSet.xml")
    public void testCreateProduct() throws Exception {
    
        // Create new entity
        Product create = new Product();
                    
        // Set identity
        create.setProductId(Integer.valueOf(1));

        // Populate simple properties
        create.setName("s");

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("ProductBeforeUpdateDataSet.xml")
    @ExpectedDataSet("ProductAfterUpdateDataSet.xml")
    public void testUpdateProduct() throws Exception {
                    
        // Load entity and modify
        Product result = (Product)entityManager.find(Product.class, ProductTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setName("t");

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("ProductBeforeDeleteDataSet.xml")
    @ExpectedDataSet("ProductAfterDeleteDataSet.xml")
    public void testRemoveProduct() throws Exception {
                    
        // Delete
        Product result = (Product)entityManager.find(Product.class, ProductTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("ProductFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllProduct() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from Product");
                    
        // Get results 
        List<Product> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("ProductFindDataSet.xml")
    public void testFindProductByIdentity() throws Exception {
                    
        // Get object 
        Product result  = (Product)entityManager.find(Product.class, ProductTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals("s", result.getName());

    }
    
}