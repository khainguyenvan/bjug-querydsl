package net.eusashead.bjugquerydsl.hateoas;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import net.eusashead.bjugquerydsl.config.JpaConfig;
import net.eusashead.bjugquerydsl.data.entity.StockKeepingUnit;
import net.eusashead.bjugquerydsl.data.repository.StockKeepingUnitRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.theoryinpractise.halbuilder.DefaultRepresentationFactory;
import com.theoryinpractise.halbuilder.api.Link;
import com.theoryinpractise.halbuilder.api.ReadableRepresentation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfig.class})
public class HalPageResponseEntityBuilderTest {

	@Autowired
	private StockKeepingUnitRepository repo;

	private static final Date MODIFIED_DATE = new Date(123456789l);
	private RepresentationFactory representationFactory = new DefaultRepresentationFactory();
	private MockHttpServletRequest request = new MockHttpServletRequest("GET", "/path/to/resource");


	@Test
	@Transactional
	public void testBasic() throws Exception {
		
		RepresentationConverterImpl converter = new RepresentationConverterImpl(representationFactory, new SimplePropertyConverterImpl());
	    converter.registerExtractor(new JpaEntityResourceMetadataExtractor());

		// Set up the query string parameters
		Map<String, String[]> req = new HashMap<String, String[]>();
		req.put("foo", new String[]{"bar"});
		req.put("baz", new String[]{"foo"});
		req.put("page", new String[]{"1"});
		req.put("size", new String[]{"3"});
		req.put("sort", new String[]{"name,desc"});
		request.setParameters(req);

		// Set the query string
		request.setQueryString("?foo=bar&baz=foo&page=1&size=3&sort=name,desc");

		// Get the test data from the repo
		Page<StockKeepingUnit> page = repo.findAll(new PageRequest(1, 3));
		HalPageResponseEntityBuilder builder = new HalPageResponseEntityBuilder(representationFactory, converter, request, "fields");
		ResponseEntity<ReadableRepresentation> response = 
				builder.withPage(page, "/sku/{skuId}")
				.etag(MODIFIED_DATE)
				.lastModified(MODIFIED_DATE)
				.expireIn(1000000)
				.get();

		assertHeaders(response);
		
		// Check metadata
		ReadableRepresentation body = response.getBody();
		Assert.assertEquals(Integer.valueOf(3), body.getValue("size"));
		Assert.assertEquals(Integer.valueOf(1), body.getValue("number"));
		Assert.assertEquals(Integer.valueOf(3), body.getValue("numberOfElements"));
		Assert.assertEquals(Long.valueOf(8), body.getValue("totalElements"));
		
		// Check links
		Link next = body.getLinkByRel("next");
		Assert.assertNotNull(next);
		Assert.assertEquals("/path/to/resource?sort=name,desc&baz=foo&page=2&foo=bar&size=3", next.getHref());
		Link prev = body.getLinkByRel("previous");
		Assert.assertNotNull(prev);
		Assert.assertEquals("/path/to/resource?sort=name,desc&baz=foo&page=0&foo=bar&size=3", prev.getHref());
		
		// Check results
		Assert.assertEquals(Integer.valueOf(3), Integer.valueOf(body.getResourcesByRel("content").size()));
	}

	private void assertHeaders(ResponseEntity<ReadableRepresentation> response) {
		HttpHeaders headers = response.getHeaders();
		Assert.assertEquals("w/123456789", headers.getETag());
		Assert.assertEquals(123456000, headers.getLastModified());

	}

}
