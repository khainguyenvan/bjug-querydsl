package net.eusashead.bjugquerydsl.hateoas;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;
import net.eusashead.bjugquerydsl.data.entity.Basket;
import net.eusashead.bjugquerydsl.data.entity.BasketItem;
import net.eusashead.bjugquerydsl.data.entity.Customer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import com.theoryinpractise.halbuilder.DefaultRepresentationFactory;
import com.theoryinpractise.halbuilder.api.ReadableRepresentation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

@RunWith(JUnit4.class)
public class HalResponseEntityBuilderTest {

	private static final Date MODIFIED_DATE = new Date(123456789l);
	private RepresentationFactory representationFactory = new DefaultRepresentationFactory();
	private RepresentationConverterImpl converter;
	private MockHttpServletRequest request = new MockHttpServletRequest("GET", "/path/to/resource");

	@Before
	public void before() throws Exception {
		
		// Build the converter
		this.converter = new RepresentationConverterImpl(representationFactory, new SimplePropertyConverterImpl());
		this.converter.registerExtractor(new JpaEntityResourceMetadataExtractor());
		 
	}
	
	@Test
	public void testBasic() throws Exception {
		HalResponseEntityBuilder builder = new HalResponseEntityBuilder(representationFactory, converter, request, "fields");
		ResponseEntity<ReadableRepresentation> response = 
				builder.withProperty("string", "String value")
				.withProperty("date", new Date(123456789l))
				.withProperty("int", 34)
				.withProperty("decimal", BigDecimal.valueOf(123.32d))
				.withProperty("boolean", true)
				.withRepresentation("customer", customer())
				.etag(MODIFIED_DATE)
				.lastModified(MODIFIED_DATE)
				.expireIn(1000000)
				.get();

		assertHeaders(response);
		Assert.assertEquals("String value", response.getBody().getValue("string").toString());
		Assert.assertEquals("Fri Jan 02 11:17:36 GMT 1970", response.getBody().getValue("date").toString());
		Assert.assertEquals("34", response.getBody().getValue("int").toString());
		Assert.assertEquals("123.32", response.getBody().getValue("decimal").toString());
		Assert.assertEquals("true", response.getBody().getValue("boolean").toString());
		List<? extends ReadableRepresentation> customer = response.getBody().getResourcesByRel("customer");
		Assert.assertNotNull(customer);
		Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(customer.size()));
		ReadableRepresentation cust = customer.get(0);
		Assert.assertEquals("test@domain.com", cust.getValue("email").toString());
	}
	
	@Test
	public void testWithBean() throws Exception {
		HalResponseEntityBuilder builder = new HalResponseEntityBuilder(representationFactory, converter, request, "fields");
		Customer customer = customer();
		ResponseEntity<ReadableRepresentation> response = 
				builder.withBean(customer)
				.etag(MODIFIED_DATE)
				.lastModified(MODIFIED_DATE)
				.expireIn(1000000)
				.get();

		assertHeaders(response);
		Assert.assertEquals("123", response.getBody().getValue("customerId").toString());
		Assert.assertEquals("test@domain.com", response.getBody().getValue("email").toString());
		Assert.assertEquals("First", response.getBody().getValue("firstName").toString());
		Assert.assertEquals("Last", response.getBody().getValue("lastName").toString());
		Assert.assertEquals("Fri Jan 02 11:17:36 GMT 1970", response.getBody().getValue("joined").toString());
	}
	
	@Test
	public void testWithBeanWithChildren() throws Exception {
		HalResponseEntityBuilder builder = new HalResponseEntityBuilder(representationFactory, converter, request, "fields");
		Customer customer = customerWithBaskets();
		ResponseEntity<ReadableRepresentation> response = 
				builder.withBean(customer)
				.etag(MODIFIED_DATE)
				.lastModified(MODIFIED_DATE)
				.expireIn(1000000)
				.get();

		assertHeaders(response);
		Assert.assertNotNull(response.getBody().getResourcesByRel("baskets"));
		Assert.assertEquals(Integer.valueOf(2), Integer.valueOf(response.getBody().getResourcesByRel("baskets").size()));
	}
	
	@Test
	public void testWithBeanWithChildrenIncludeFields() throws Exception {
		HalResponseEntityBuilder builder = new HalResponseEntityBuilder(representationFactory, converter, request, "fields");
		Customer customer = customerWithBaskets();
		ResponseEntity<ReadableRepresentation> response = 
				builder.withBean(customer, "firstName", "email")
				.etag(MODIFIED_DATE)
				.lastModified(MODIFIED_DATE)
				.expireIn(1000000)
				.get();

		assertHeaders(response);
		Set<String> keys = response.getBody().getProperties().keySet();
		Assert.assertTrue(keys.size() == 2);
		Assert.assertTrue(keys.contains("email"));
		Assert.assertTrue(keys.contains("firstName"));
		Assert.assertEquals(Integer.valueOf(0), Integer.valueOf(response.getBody().getResourcesByRel("baskets").size()));
	}
	
	@Test
	public void testWithBeanWithChildrenRequestFields() throws Exception {
		Customer customer = customerWithBaskets();
		request.setParameter("fields", new String[]{"lastName", "customerId"});
		HalResponseEntityBuilder builder = new HalResponseEntityBuilder(representationFactory, converter, request, "fields");
		ResponseEntity<ReadableRepresentation> response = 
				builder.withBean(customer)
				.etag(MODIFIED_DATE)
				.lastModified(MODIFIED_DATE)
				.expireIn(1000000)
				.get();

		assertHeaders(response);
		Set<String> keys = response.getBody().getProperties().keySet();
		
		System.out.println(response.getBody().toString("application/hal+xml"));
		
		Assert.assertTrue(keys.size() == 2);
		Assert.assertTrue(keys.contains("lastName"));
		Assert.assertTrue(keys.contains("customerId"));
		Assert.assertEquals(Integer.valueOf(0), Integer.valueOf(response.getBody().getResourcesByRel("baskets").size()));
	}
	
	@Test
	public void testRequestFieldsOverridesIncludedFields() throws Exception {
		Customer customer = customerWithBaskets();
		request.setParameter("fields", new String[]{"lastName", "customerId"});
		HalResponseEntityBuilder builder = new HalResponseEntityBuilder(representationFactory, converter, request, "fields");
		ResponseEntity<ReadableRepresentation> response = 
				builder.withBean(customer, "firstName", "email")
				.etag(MODIFIED_DATE)
				.lastModified(MODIFIED_DATE)
				.expireIn(1000000)
				.get();

		assertHeaders(response);
		Set<String> keys = response.getBody().getProperties().keySet();
		Assert.assertTrue(keys.size() == 2);
		Assert.assertTrue(keys.contains("lastName"));
		Assert.assertTrue(keys.contains("customerId"));
		Assert.assertEquals(Integer.valueOf(0), Integer.valueOf(response.getBody().getResourcesByRel("baskets").size()));
	}
	
	@Test
	public void testRequestInvalidField() throws Exception {
		Customer customer = customerWithBaskets();
		request.setParameter("fields", new String[]{"noChance", "firstName"});
		HalResponseEntityBuilder builder = new HalResponseEntityBuilder(representationFactory, converter, request, "fields");
		ResponseEntity<ReadableRepresentation> response = 
				builder.withBean(customer)
				.etag(MODIFIED_DATE)
				.lastModified(MODIFIED_DATE)
				.expireIn(1000000)
				.get();

		assertHeaders(response);
		Set<String> keys = response.getBody().getProperties().keySet();
		Assert.assertTrue(keys.size() == 1);
		Assert.assertTrue(keys.contains("firstName"));
		Assert.assertEquals(Integer.valueOf(0), Integer.valueOf(response.getBody().getResourcesByRel("baskets").size()));
	}

	@Test
	public void testIncludeInvalidField() throws Exception {
		HalResponseEntityBuilder builder = new HalResponseEntityBuilder(representationFactory, converter, request, "fields");
		Customer customer = customerWithBaskets();
		ResponseEntity<ReadableRepresentation> response = 
				builder.withBean(customer, "noChance", "firstName")
				.etag(MODIFIED_DATE)
				.lastModified(MODIFIED_DATE)
				.expireIn(1000000)
				.get();

		assertHeaders(response);
		Set<String> keys = response.getBody().getProperties().keySet();
		Assert.assertTrue(keys.size() == 1);
		Assert.assertTrue(keys.contains("firstName"));
		Assert.assertEquals(Integer.valueOf(0), Integer.valueOf(response.getBody().getResourcesByRel("baskets").size()));
	}
	
	private Customer customerWithBaskets() {
		final Customer customer = customer();
		final Basket basket1 = new Basket(1, customer, new HashSet<BasketItem>());
		final Basket basket2 = new Basket(2, customer, new HashSet<BasketItem>());
		customer.getBaskets().add(basket1);
		customer.getBaskets().add(basket2);
		return customer;
	}

	private Customer customer() {
		final Customer customer = new Customer(123, "test@domain.com", "First", new Date(123456789l), "Last");
		return customer;
	}

	private void assertHeaders(ResponseEntity<ReadableRepresentation> response) {
		HttpHeaders headers = response.getHeaders();
		Assert.assertEquals("w/123456789", headers.getETag());
		Assert.assertEquals(123456000, headers.getLastModified());
		Assert.assertTrue(Long.valueOf(133456789) < Long.valueOf(headers.getExpires()));
		
	}

}
