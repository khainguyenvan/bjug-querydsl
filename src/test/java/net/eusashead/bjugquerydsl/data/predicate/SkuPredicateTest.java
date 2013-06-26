package net.eusashead.bjugquerydsl.data.predicate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;
import net.eusashead.bjugquerydsl.config.JpaConfig;
import net.eusashead.bjugquerydsl.controller.SkuSearchRequest;
import net.eusashead.bjugquerydsl.controller.SkuSearchRequestBuilder;
import net.eusashead.bjugquerydsl.data.entity.QStockKeepingUnit;
import net.eusashead.bjugquerydsl.data.entity.StockKeepingUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfig.class})
public class SkuPredicateTest {
	
	@PersistenceContext
	private EntityManager em;

	@Test
	@Transactional
	public void testSearchSku() throws Exception {

		// Basic search
		SkuSearchRequest search1 = new SkuSearchRequestBuilder().build();
		List<StockKeepingUnit>res1 = searchSku(search1);
		Assert.assertEquals(Integer.valueOf(8), Integer.valueOf(res1.size()));

		// Min set
		SkuSearchRequest search2 = new SkuSearchRequestBuilder().minPrice(1100d).build();
		List<StockKeepingUnit>res2 = searchSku(search2);
		Assert.assertEquals(Integer.valueOf(4), Integer.valueOf(res2.size()));

		// Max set
		SkuSearchRequest search3 = new SkuSearchRequestBuilder().maxPrice(1100d).build();
		List<StockKeepingUnit>res3 = searchSku(search3);
		Assert.assertEquals(Integer.valueOf(4), Integer.valueOf(res3.size()));

		// Min and max set
		SkuSearchRequest search4 = new SkuSearchRequestBuilder().minPrice(1100d).maxPrice(1150d).build();
		List<StockKeepingUnit>res4 = searchSku(search4);
		Assert.assertEquals(Integer.valueOf(2), Integer.valueOf(res4.size()));

		// One attr, one value
		SkuSearchRequest search5 = new SkuSearchRequestBuilder().attribute("neck", "maple").build();
		List<StockKeepingUnit>res5 = searchSku(search5);
		Assert.assertEquals(Integer.valueOf(4), Integer.valueOf(res5.size()));

		// One attr, two values
		SkuSearchRequest search6 = new SkuSearchRequestBuilder().attribute("nEck", "maplE", "Rosewood").build();
		List<StockKeepingUnit>res6 = searchSku(search6);
		Assert.assertEquals(Integer.valueOf(8), Integer.valueOf(res6.size()));

		// Two attr, one value each
		SkuSearchRequest search7 = new SkuSearchRequestBuilder().attribute("Neck", "Maple")
				.attribute("Colour", "Lake Placid Blue").build();
		List<StockKeepingUnit>res7 = searchSku(search7);
		Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(res7.size()));

		// Two attr, multi values value each
		SkuSearchRequest search8 = new SkuSearchRequestBuilder().attribute("Neck", "Maple", "Rosewood")
				.attribute("Colour", "Lake Placid Blue").build();
		List<StockKeepingUnit>res8 = searchSku(search8);
		Assert.assertEquals(Integer.valueOf(2), Integer.valueOf(res8.size()));

		// Attributes, values, prices
		SkuSearchRequest search9 = new SkuSearchRequestBuilder().attribute("Neck", "Maple")
				.minPrice(1000d).maxPrice(1050d).build();
		List<StockKeepingUnit>res9 = searchSku(search9);
		Assert.assertEquals(Integer.valueOf(2), Integer.valueOf(res9.size()));

	}

	private List<StockKeepingUnit> searchSku(
			SkuSearchRequest request) {
		
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		
		List<StockKeepingUnit> results = query.from(path)
				.where(path.search(request))
				.list(path);
		
		return results;

	}

}
