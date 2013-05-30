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

import net.eusashead.bjugquerydsl.data.entity.Attribute;
import net.eusashead.bjugquerydsl.data.entity.AttributeTestUtils;
import net.eusashead.bjugquerydsl.data.entity.Product;
import net.eusashead.bjugquerydsl.data.entity.ProductTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "commerce")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class AttributeJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("AttributeBeforeCreateDataSet.xml")
    @ExpectedDataSet("AttributeAfterCreateDataSet.xml")
    public void testCreateAttribute() throws Exception {
    
        // Create new entity
        Attribute create = new Attribute();
                    
        // Set identity
        create.setAttributeId(Integer.valueOf(1));

        // Populate simple properties
        create.setName("s");
                
        // Populate dependencies
        Product product = (Product)entityManager.getReference(Product.class, ProductTestUtils.getDefaultIdentity());
        create.setProduct(product);

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("AttributeBeforeUpdateDataSet.xml")
    @ExpectedDataSet("AttributeAfterUpdateDataSet.xml")
    public void testUpdateAttribute() throws Exception {
                    
        // Load entity and modify
        Attribute result = (Attribute)entityManager.find(Attribute.class, AttributeTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setName("t");

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("AttributeBeforeDeleteDataSet.xml")
    @ExpectedDataSet("AttributeAfterDeleteDataSet.xml")
    public void testRemoveAttribute() throws Exception {
                    
        // Delete
        Attribute result = (Attribute)entityManager.find(Attribute.class, AttributeTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("AttributeFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllAttribute() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from Attribute");
                    
        // Get results 
        List<Attribute> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("AttributeFindDataSet.xml")
    public void testFindAttributeByIdentity() throws Exception {
                    
        // Get object 
        Attribute result  = (Attribute)entityManager.find(Attribute.class, AttributeTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals("s", result.getName());

    }
    
}