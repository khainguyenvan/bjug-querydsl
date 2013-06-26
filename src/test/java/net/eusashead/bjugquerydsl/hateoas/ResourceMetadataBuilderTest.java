package net.eusashead.bjugquerydsl.hateoas;

import junit.framework.Assert;
import net.eusashead.bjugquerydsl.data.entity.Customer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.web.util.UriTemplate;

@RunWith(JUnit4.class)
public class ResourceMetadataBuilderTest {

	@Test
	public void testBuild() throws SecurityException, NoSuchMethodException {
		ResourceMetadataBuilder builder = new ResourceMetadataBuilder();
		builder.uriTemplate("/customer/{customerId}");
		builder.identityProperty("customerId", Customer.class.getMethod("getCustomerId"));
		builder.simpleProperty("firstName", Customer.class.getMethod("getFirstName"));
		builder.embeddedResource("baskets", "/basket/{basketId}", Customer.class.getMethod("getBaskets"));
		ResourceMetadata metadata = builder.build();
		
		Assert.assertEquals(new UriTemplate("/customer/{customerId}").toString(), metadata.getUriTemplate().toString());
		Assert.assertEquals(1, metadata.getIdentityProperties().size());
		Assert.assertEquals(new IdentityProperty("customerId", Customer.class.getMethod("getCustomerId")), metadata.getIdentityProperties().get(0));
		Assert.assertEquals(1, metadata.getSimpleProperties().size());
		Assert.assertEquals(new SimpleProperty("firstName", Customer.class.getMethod("getFirstName")), metadata.getSimpleProperties().get(0));
		Assert.assertEquals(1, metadata.getEmbeddedResources().size());
		// UriTemplate doesn't implement equals very well
		//Assert.assertEquals(new EmbeddedResource("baskets", new UriTemplate("/basket/{basketId}"), Customer.class.getMethod("getBaskets")), metadata.getEmbeddedResources().get(0));
	}
}
