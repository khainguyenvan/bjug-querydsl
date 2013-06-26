package net.eusashead.bjugquerydsl.data.entity;

import static com.mysema.query.group.GroupBy.groupBy;
import static com.mysema.query.group.GroupBy.list;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnitUtil;

import net.eusashead.bjugquerydsl.config.JpaConfig;
import net.eusashead.bjugquerydsl.dto.SkuDto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.commons.lang.CloseableIterator;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.Projectable;
import com.mysema.query.QueryModifiers;
import com.mysema.query.ResultTransformer;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.query.NumberSubQuery;

/**
 * Demonstration of JPA 
 * query capabilities using the
 * "commerce" data model.
 * 
 * @author patrickvk
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfig.class})
public class JpaQueryTest {

	@PersistenceContext
	private EntityManager em;

	@Test
	@Transactional
	public void testFindOneProduct() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QProduct path = QProduct.product;
		Product product = query.from(path)
				.where(path.productId.eq(1))
				.uniqueResult(path);

		Assert.assertNotNull(product);
		Assert.assertEquals(Integer.valueOf(1), product.getProductId());

	}

	@Test
	@Transactional
	public void testFindAllSkus() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		List<StockKeepingUnit> results = query.from(path)
				.list(path);

		Assert.assertEquals(Integer.valueOf(8), Integer.valueOf(results.size()));

	}

	@Test
	@Transactional
	public void testFindAllSkusCheaperThan() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		List<StockKeepingUnit> results = query.from(path)
				.where(path.price.lt(1100.00d))
				.orderBy(path.price.desc())
				.list(path);

		Assert.assertEquals(Integer.valueOf(4), Integer.valueOf(results.size()));
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

		Assert.assertEquals(Integer.valueOf(4), Integer.valueOf(results.size()));

	}

	@Test
	@Transactional
	public void testFindAllSkusGroupByPrice() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		List<BigDecimal> results = query.from(path)
				.groupBy(path.price)
				.list(path.price);

		Assert.assertEquals(Integer.valueOf(4), Integer.valueOf(results.size()));

	}

	@Test
	@Transactional
	public void testFindAverageSKUPrice() throws Exception {

		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		Double results = query.from(path)
				.uniqueResult(path.price.avg());

		Assert.assertEquals(Double.valueOf(1114.99), results);

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
		// TODO how to test?
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

		// TODO how to test?

	}

	@Test
	@Transactional
	public void testFindSkuDto() throws Exception {

		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		List<SkuDto> results = query.from(path)
				.list(ConstructorExpression.create(SkuDto.class, path.skuId, path.name, path.price));

		Assert.assertEquals(Integer.valueOf(8), Integer.valueOf(results.size()));

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

		Assert.assertEquals(Integer.valueOf(4), Integer.valueOf(results.size()));

	}


	@Test
	@Transactional
	public void testFindAllSkusPageWithOffset() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		List<StockKeepingUnit> results = query.from(path)
				.offset(4).limit(4)
				.list(path);

		Assert.assertEquals(Integer.valueOf(4), Integer.valueOf(results.size()));

	}

	@Test
	@Transactional
	public void testFindAllSkusPageWithRestrict() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;
		List<StockKeepingUnit> results = query.from(path)
				.restrict(new QueryModifiers(4L, 4L))
				.list(path);

		Assert.assertEquals(Integer.valueOf(4), Integer.valueOf(results.size()));

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

		Assert.assertEquals(Integer.valueOf(4), Integer.valueOf(results.size()));

	}

	@Test
	@Transactional
	public void testFindProductWithJoinFetch() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QProduct path = QProduct.product;
		Product results = query.from(path)
				.join(path.stockKeepingUnits)
				.fetch() // Eager fetch the last join
				.where(path.productId.eq(1))
				.uniqueResult(path);

		// Get the PersistenceUnitUtil
		PersistenceUnitUtil util = em.getEntityManagerFactory().getPersistenceUnitUtil();

		// Check that the eager fetching occurred!
		Assert.assertTrue(util.isLoaded(results, "stockKeepingUnits"));

	}

	@Test
	@Transactional
	public void testFindSkuWithFetchAll() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;

		StockKeepingUnit results = query.from(path)
				.fetchAll() // Get all non-association fields eagerly
				.singleResult(path);

		// Get the PersistenceUnitUtil
		PersistenceUnitUtil util = em.getEntityManagerFactory().getPersistenceUnitUtil();

		// Check that the eager fetching occurred!
		Assert.assertTrue(util.isLoaded(results));

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

		// Get the PersistenceUnitUtil
		PersistenceUnitUtil util = em.getEntityManagerFactory().getPersistenceUnitUtil();

		// Check that the eager fetching occurred!
		Assert.assertTrue(util.isLoaded(results, "product"));

	}

	@Test
	@Transactional
	public void testInventoryQueryDelegate() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QInventory path = QInventory.inventory;

		BooleanBuilder bldr = new BooleanBuilder(path.isNotSold());
		Predicate compound = bldr.and(path.inStockSince(new Date())).getValue();

		List<Inventory> results = query.from(path)
				.where(compound)
				.list(path);

		Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(results.size()));

	}

	@Test
	@Transactional
	public void testCustomTransform() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QStockKeepingUnit path = QStockKeepingUnit.stockKeepingUnit;

		List<SkuDto> results = query.from(path)
				.transform(new ResultTransformer<List<SkuDto>>() {

					@Override
					public List<SkuDto> transform(Projectable arg0) {
						List<SkuDto> result = new ArrayList<SkuDto>();
						CloseableIterator<StockKeepingUnit> iterator = arg0.iterate(QStockKeepingUnit.stockKeepingUnit);
						while (iterator.hasNext()) {
							StockKeepingUnit sku = iterator.next();
							SkuDto dto = new SkuDto(sku.getSkuId(), sku.getName(), sku.getPrice());
							result.add(dto);
						}
						return result;
					}
				});

		Assert.assertEquals(Integer.valueOf(8), Integer.valueOf(results.size()));

	}

	@Test
	@Transactional
	public void testGroupByTransform() throws Exception {
		JPAQuery query = new JPAQuery(em);
		QProduct productPath = QProduct.product;
		QStockKeepingUnit skuPath = QStockKeepingUnit.stockKeepingUnit;

		Map<Product, List<StockKeepingUnit>> results = query.from(productPath, skuPath)
				.where(skuPath.product().productId.eq(productPath.productId))
				.transform(groupBy(productPath).as(list(skuPath)));

		Assert.assertEquals(Integer.valueOf(2), Integer.valueOf(results.size()));

	}

}
