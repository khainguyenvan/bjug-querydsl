package net.eusashead.bjugquerydsl.hateoas;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SimplePropertyConverterImplTest {

	@Test
	public void testDefaultConversions() {
		
		// Default constructor
		SimplePropertyConverterImpl converter = new SimplePropertyConverterImpl();
		
		// Test date to string
		Object dateConverted = converter.convert(new Date(123456789l), String.class);
		Assert.assertEquals("Fri Jan 02 11:17:36 GMT 1970", dateConverted.toString());
		
		// Test decimal to string
		Object decimalConverted = converter.convert(BigDecimal.valueOf(23.45d), String.class);
		Assert.assertEquals("23.45", decimalConverted.toString());
	}
}
