package net.eusashead.bjugquerydsl.controller;

import static net.eusashead.hateoas.converter.hal.HalHttpMessageConverter.HAL_JSON;
import static net.eusashead.hateoas.converter.hal.HalHttpMessageConverter.HAL_XML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.io.StringReader;

import junit.framework.Assert;
import net.eusashead.bjugquerydsl.config.WebConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theoryinpractise.halbuilder.DefaultRepresentationFactory;
import com.theoryinpractise.halbuilder.api.ReadableRepresentation;

/**
 * Tests for a Spring MVC controller
 * using QueryDSL to create JSON
 * endpoints from JPA entities.
 * 
 * @author patrickvk
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={WebConfig.class})
public class SkuControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper mapper;

	@Test
	@Transactional
	public void testSkuSearchPaging() throws Exception {
		MvcResult result = webAppContextSetup(context)
				.build()
				.perform(get("http://localhost/sku/?page=0&size=3&sort=skuId,desc")
						.contentType(HAL_JSON).accept(HAL_JSON))
						.andExpect(status().isOk())
						.andExpect(content().contentType(HAL_JSON))
						.andReturn();
		
		// Verify result
		ReadableRepresentation skus = getContent(result);
		Assert.assertEquals(Integer.valueOf(3), Integer.valueOf(skus.getResourcesByRel("content").size()));
	
	}
	
	@Test
	@Transactional
	public void testSkuSearchWithAttr() throws Exception {
		MvcResult result = webAppContextSetup(context)
				.build()
				.perform(get("http://localhost/sku/?price.min=1000&price.max=1150&attr.neck=maple&attr.colour=sonic blue")
						.contentType(HAL_XML).accept(HAL_XML))
						.andExpect(status().isOk())
						.andExpect(content().contentType(HAL_XML))
						.andReturn();
		
		// Verify result
		ReadableRepresentation skus = getContent(result);
		Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(skus.getResourcesByRel("content").size()));
		
	}
	
	@Test
	@Transactional
	public void testFindOneJson() throws Exception {
		MvcResult result = webAppContextSetup(context)
				.build()
				.perform(get("http://localhost/sku/1")
						.contentType(HAL_JSON).accept(HAL_JSON))
						.andExpect(status().isOk())
						.andExpect(content().contentType(HAL_JSON))
						.andReturn();
		
		// Verify result
		ReadableRepresentation sku = getContent(result);
		Assert.assertEquals("1", sku.getValue("skuId").toString());
	}
	
	@Test
	@Transactional
	public void testFindOneXml() throws Exception {
		MvcResult result = webAppContextSetup(context)
				.build()
				.perform(get("http://localhost/sku/1")
						.contentType(HAL_XML).accept(HAL_XML))
						.andExpect(status().isOk())
						.andExpect(content().contentType(HAL_XML))
						.andReturn();
		
		// Verify result
		ReadableRepresentation sku = getContent(result);
		Assert.assertEquals("1", sku.getValue("skuId").toString());
	}
	
	/**
	 * Extract a SKU from the response
	 * @param result
	 * @return
	 * @throws IOException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 */
	private ReadableRepresentation getContent(MvcResult result)
			throws IOException, JsonParseException, JsonMappingException {
		return new DefaultRepresentationFactory().readRepresentation(new StringReader(result.getResponse().getContentAsString()));
	}

}
