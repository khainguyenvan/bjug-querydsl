package net.eusashead.bjugquerydsl.controller;

import java.nio.charset.Charset;

import net.eusashead.bjugquerydsl.config.WebConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={WebConfig.class})
public class ProductControllerTest {

	// MIME type and character set definitions
	private static final Charset CHARSET_UTF_8 = Charset.forName("UTF-8");
	private static MediaType APPLICATION_JSON = new MediaType("application", "json", CHARSET_UTF_8);
	
	@Autowired
	private WebApplicationContext context;

	@Test
	public void testFindAll() throws Exception {
		MvcResult result = webAppContextSetup(context)
				.build()
				.perform(get("http://localhost/product/")
						.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
	}
	
	@Test
	public void testFindOne() throws Exception {
		MvcResult result = webAppContextSetup(context)
				.build()
				.perform(get("http://localhost/product/1")
						.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
	}
}
