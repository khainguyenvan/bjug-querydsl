package net.eusashead.bjugquerydsl.hateoas;

import java.util.Collection;
import java.util.Date;

import junit.framework.Assert;
import net.eusashead.bjugquerydsl.data.entity.Basket;
import net.eusashead.bjugquerydsl.data.entity.Customer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.web.util.UriTemplate;

import com.theoryinpractise.halbuilder.DefaultRepresentationFactory;
import com.theoryinpractise.halbuilder.api.ReadableRepresentation;
import com.theoryinpractise.halbuilder.api.Representation;

@RunWith(JUnit4.class)
public class RepresentationConverterImplTest {
	
	private RepresentationConverterImpl converter;
	
	private SimplePropertyConverter simplePropertyConverter = new SimplePropertyConverterImpl();
	
	@Before
	public void before() {
		this.converter = new RepresentationConverterImpl(new DefaultRepresentationFactory(), simplePropertyConverter);
		this.converter.registerExtractor(new JpaEntityResourceMetadataExtractor());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConvertNull() {
		converter.convert(null);
	}
	
	@Test
	public void testConvertNoEmbedded() {
		
		Customer customer = customer();
		Representation representation = converter.convert(customer);
		
		Assert.assertEquals("1", representation.getValue("customerId").toString());
		Assert.assertEquals("email", representation.getValue("email").toString());
		Assert.assertEquals("first", representation.getValue("firstName").toString());
		Assert.assertEquals("last", representation.getValue("lastName").toString());
		Assert.assertEquals("Fri Jan 02 11:17:36 GMT 1970", representation.getValue("joined").toString());
	}
	
	@Test
	public void testConvertNoEmbeddedWithUri() {
		
		Customer customer = customer();
		Representation representation = converter.convert(customer, new UriTemplate("/customer/{customerId}"));
		
		Assert.assertEquals("/customer/1", representation.getLinkByRel("self").getHref());
		Assert.assertEquals("1", representation.getValue("customerId").toString());
		Assert.assertEquals("email", representation.getValue("email").toString());
		Assert.assertEquals("first", representation.getValue("firstName").toString());
		Assert.assertEquals("last", representation.getValue("lastName").toString());
		Assert.assertEquals("Fri Jan 02 11:17:36 GMT 1970", representation.getValue("joined").toString());
		
	}

	@Test
	public void testConvertNoEmbeddedRestrictScope() {
		
		Customer customer = customer();
		Representation representation = converter.convert(customer, "email", "lastName");
		Assert.assertEquals("email", representation.getValue("email").toString());
		Assert.assertEquals("last", representation.getValue("lastName").toString());
		Assert.assertNull(representation.getProperties().get("firstName"));
		
	}
	
	@Test
	public void testConvertEmbeddedManyToOne() {
		
		Customer customer = customer();
		Basket basket = basket(1);
		basket.setCustomer(customer);
		customer.getBaskets().add(basket);
		
		Representation representation = converter.convert(basket, new UriTemplate("/basket/{basketId}"));
		Assert.assertEquals("1", representation.getValue("basketId").toString());
		Collection<ReadableRepresentation> reps = representation.getResourceMap().get("customer");
		Assert.assertNotNull(reps);
		Assert.assertTrue(reps.size() == 1);
		
		System.out.println(representation.toString("application/hal+xml"));
	}
	
	
	private Customer customer() {
		return new Customer(1, "email", "first", new Date(123456789l), "last");
	}
	
	private Basket basket(Integer basketId) {
		return new Basket(basketId);
	}
	
}
