package net.eusashead.bjugquerydsl.data.predicate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.eusashead.bjugquerydsl.config.WebConfig;
import net.eusashead.bjugquerydsl.data.entity.Product;
import net.eusashead.bjugquerydsl.data.entity.QProduct;
import net.eusashead.bjugquerydsl.data.entity.QStockKeepingUnit;
import net.eusashead.bjugquerydsl.data.entity.StockKeepingUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysema.query.jpa.impl.JPAQuery;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={WebConfig.class})
public class PredicateTest {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Test
	@Transactional
	public void testFindOneProduct() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QProduct path = QProduct.product;
		Product product = query.from(path)
				.where(path.productId.eq(1))
				.uniqueResult(path);
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(product));
		
	}
	
	@Test
	@Transactional
	public void testFindAllSkus() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		List<StockKeepingUnit> results = query.from(path)
				.list(path);
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results));
		
	}
	
	@Test
	@Transactional
	public void testFindAllSkusWithAttribute() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		List<StockKeepingUnit> results = query.from(path)
				.where(path.values.any().attribute().name.eq("Neck")
						.and(path.values.any().name.eq("Rosewood")))
				.list(path);
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results));
		Assert.assertTrue(4 == results.size());
	}

}
