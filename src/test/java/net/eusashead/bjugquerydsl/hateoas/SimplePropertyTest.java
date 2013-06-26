package net.eusashead.bjugquerydsl.hateoas;

import java.lang.reflect.Method;
import java.sql.Date;

import net.eusashead.bjugquerydsl.data.entity.Customer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SimplePropertyTest {

	@Test
	public void testCreation() throws Exception {
		// Create test objects
		Customer customer = new Customer(1, "email", "first", new Date(123456789l), "last");
		Method accessor = customer.getClass().getMethod("getFirstName");
		String name = "firstName";
		
		// Create SimpleProperty
		SimpleProperty er = new SimpleProperty(name, accessor);
		
		// Test
		Assert.assertEquals(name, er.getName());
		Assert.assertEquals(accessor, er.getAccessor());
		Assert.assertEquals(String.class, er.getType());
		Assert.assertEquals(customer.getFirstName(), er.getValue(customer));
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullName() throws SecurityException, NoSuchMethodException {
		new SimpleProperty(null, Customer.class.getMethod("getCustomerId"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEmptyName() throws SecurityException, NoSuchMethodException {
		new SimpleProperty("", Customer.class.getMethod("getCustomerId"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullAccessor() {
		new SimpleProperty("name", null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSimpleProperty() throws SecurityException, NoSuchMethodException {
		new SimpleProperty("baskets", Customer.class.getMethod("getBaskets"));
	}
}
