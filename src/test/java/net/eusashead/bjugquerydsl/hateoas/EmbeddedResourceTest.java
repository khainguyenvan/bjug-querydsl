package net.eusashead.bjugquerydsl.hateoas;

import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Collection;

import net.eusashead.bjugquerydsl.data.entity.Customer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.web.util.UriTemplate;

@RunWith(JUnit4.class)
public class EmbeddedResourceTest {

	@Test
	public void testCreation() throws Exception {
		// Create test objects
		Customer customer = new Customer(1, "email", "first", new Date(123456789l), "last");
		Method accessor = customer.getClass().getMethod("getBaskets");
		UriTemplate uriTemplate = new UriTemplate("/basket/{basketId}");
		String name = "baskets";
		
		// Create EmbeddedResource
		EmbeddedResource er = new EmbeddedResource(name, uriTemplate, accessor);
		
		// Test
		Assert.assertEquals(name, er.getName());
		Assert.assertEquals(accessor, er.getAccessor());
		Assert.assertEquals(uriTemplate, er.getUriTemplate());
		Assert.assertEquals(Collection.class, er.getType());
		Assert.assertEquals(customer.getBaskets(), er.getValue(customer));
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullName() throws SecurityException, NoSuchMethodException {
		new EmbeddedResource(null, new UriTemplate("/basket/{basketId}"), Customer.class.getMethod("getBaskets"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEmptyName() throws SecurityException, NoSuchMethodException {
		new EmbeddedResource("", new UriTemplate("/basket/{basketId}"), Customer.class.getMethod("getBaskets"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullAccessor() {
		new EmbeddedResource("name", new UriTemplate("/basket/{basketId}"), null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSimpleProperty() throws SecurityException, NoSuchMethodException {
		new EmbeddedResource("customerId", new UriTemplate("/basket/{basketId}"), Customer.class.getMethod("getCustomerId"));
	}
}
