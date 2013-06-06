package net.eusashead.bjugquerydsl.data.predicate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.eusashead.bjugquerydsl.config.WebConfig;
import net.eusashead.bjugquerydsl.data.entity.Inventory;
import net.eusashead.bjugquerydsl.data.entity.Product;
import net.eusashead.bjugquerydsl.data.entity.QInventory;
import net.eusashead.bjugquerydsl.data.entity.QProduct;
import net.eusashead.bjugquerydsl.data.entity.QStockKeepingUnit;
import net.eusashead.bjugquerydsl.data.entity.StockKeepingUnit;
import net.eusashead.bjugquerydsl.dto.SkuDto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysema.query.QueryModifiers;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.query.NumberSubQuery;

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
	public void testFindAllSkusCheaperThan() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		List<StockKeepingUnit> results = query.from(path)
				.where(path.price.lt(10000.00d))
				.orderBy(path.price.desc())
				.list(path);
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results));
		
	}
	
	@Test
	@Transactional
	public void testFindAboveAverageSkuWithSubquery() throws Exception {
		JPAQuery query = new JPAQuery(em);
		
		// Subquery to find average price
		QStockKeepingUnit p = new QStockKeepingUnit("subSku");
		NumberSubQuery<Double> sub = new JPASubQuery().
						from(p).
						unique(p.price.avg());
		
		// Query to find SKUs above the average price
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		List<StockKeepingUnit> results = query.from(path)
				.where(path.price.gt(sub))
				.list(path);
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results));
		
	}
	
	@Test
	@Transactional
	public void testFindAllSkusGroupBy() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		List<BigDecimal> results = query.from(path)
				.groupBy(path.price)
				.list(path.price);
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results));
		
	}
	
	@Test
	@Transactional
	public void testFindAverageSKUPrice() throws Exception {
		
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		Double results = query.from(path)
				.uniqueResult(path.price.avg());
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results));
		
	}
	
	@Test
	@Transactional
	public void testFindAverageProductPrice() throws Exception {
		
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		List<Tuple> results = query.from(path)
				.groupBy(path.product().productId)
				.list(path.price.avg(), path.product().productId);
		
		for (Tuple tuple : results) {
			System.out.print(tuple.get(path.product().productId));
			System.out.print(": ");
			System.out.println(tuple.get(path.price.avg()));
		}
		
	}
	
	@Test
	@Transactional
	public void testFindSkuColumns() throws Exception {
		
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		List<Tuple> results = query.from(path)
				.list(path.skuId, path.name, path.price, path.product().productId);
		
		for (Tuple tuple : results) {
			String name = tuple.get(path.name);
			Integer skuId = tuple.get(path.skuId);
			BigDecimal price = tuple.get(path.price);
			System.out.println(String.format("%s[%s]: %s", name, skuId, price));
		}
		
	}
	
	@Test
	@Transactional
	public void testFindSkuDto() throws Exception {
		
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		List<SkuDto> results = query.from(path)
				.list(ConstructorExpression.create(SkuDto.class, path.skuId, path.name, path.price));
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results));
		
	}
	
	@Test
	@Transactional
	public void testFindAllSkusWithAttribute() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		BooleanExpression isNeck = path.values.any().attribute().name.eq("Neck");
		BooleanExpression isRosewood = path.values.any().name.eq("Rosewood");
		List<StockKeepingUnit> results = query.from(path)
				.where(isNeck.and(isRosewood))
				.list(path);
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results));
		Assert.assertTrue(4 == results.size());
	}
	
	
	@Test
	@Transactional
	public void testFindAllSkusPageWithOffset() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		List<StockKeepingUnit> results = query.from(path)
				.offset(20).limit(20)
				.list(path);
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results));
		
	}
	
	@Test
	@Transactional
	public void testFindAllSkusPageWithRestrict() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		List<StockKeepingUnit> results = query.from(path)
				.restrict(new QueryModifiers(20L, 20L))
				.list(path);
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results));
		
	}
	
	@Test
	@Transactional
	public void testFindProductWithJoin() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QProduct path = QProduct.product;
		QStockKeepingUnit skuPath = QStockKeepingUnit.stockKeepingUnit;
		List<StockKeepingUnit> results = query.from(path)
				.join(path.stockKeepingUnits, skuPath)
				.on(skuPath.price.gt(10.00d))
				.where(path.productId.eq(2))
				.list(skuPath);
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results));
		
	}
	
	@Test
	@Transactional
	public void testFindProductWithJoinFetch() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QProduct path = QProduct.product;
		List<Product> results = query.from(path)
				.join(path.stockKeepingUnits)
				.fetch() // Eager fetch the last join
				.list(path);
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results));
		
	}
	
	@Test
	@Transactional
	public void testFindSkuWithFetchAll() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		
		StockKeepingUnit results = query.from(path)
				.fetchAll() // Get all non-association fields eagerly
				.singleResult(path);
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results));
		
	}
	
	@Test
	@Transactional
	public void testFindSkuWithJoinFetch() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		
		StockKeepingUnit results = query.from(path)
				.join(path.product())
				.fetch() // Get the product eagerly
				.singleResult(path);
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results));
		
	}
	
	@Test
	@Transactional
	public void testInventoryQueryDelegate() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QInventory path = QInventory.inventory;
		
		List<Inventory> results = query.from(path)
				.where(path.isNotSold().and(path.inStockSince(new Date())))
				.list(path);
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results));
		
	}
	
}
