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

import net.eusashead.bjugquerydsl.data.entity.Value;
import net.eusashead.bjugquerydsl.data.entity.ValueTestUtils;
import net.eusashead.bjugquerydsl.data.entity.Attribute;
import net.eusashead.bjugquerydsl.data.entity.AttributeTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "commerce")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ValueJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("ValueBeforeCreateDataSet.xml")
    @ExpectedDataSet("ValueAfterCreateDataSet.xml")
    public void testCreateValue() throws Exception {
    
        // Create new entity
        Value create = new Value();
                    
        // Set identity
        create.setValueId(Integer.valueOf(1));

        // Populate simple properties
        create.setName("s");
                
        // Populate dependencies
        Attribute attribute = (Attribute)entityManager.getReference(Attribute.class, AttributeTestUtils.getDefaultIdentity());
        create.setAttribute(attribute);

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("ValueBeforeUpdateDataSet.xml")
    @ExpectedDataSet("ValueAfterUpdateDataSet.xml")
    public void testUpdateValue() throws Exception {
                    
        // Load entity and modify
        Value result = (Value)entityManager.find(Value.class, ValueTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setName("t");

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("ValueBeforeDeleteDataSet.xml")
    @ExpectedDataSet("ValueAfterDeleteDataSet.xml")
    public void testRemoveValue() throws Exception {
                    
        // Delete
        Value result = (Value)entityManager.find(Value.class, ValueTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("ValueFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllValue() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from Value");
                    
        // Get results 
        List<Value> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("ValueFindDataSet.xml")
    public void testFindValueByIdentity() throws Exception {
                    
        // Get object 
        Value result  = (Value)entityManager.find(Value.class, ValueTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals("s", result.getName());

    }
    
}