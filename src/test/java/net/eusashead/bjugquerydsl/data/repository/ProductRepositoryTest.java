package net.eusashead.bjugquerydsl.data.repository;

import junit.framework.Assert;
import net.eusashead.bjugquerydsl.config.JpaConfig;
import net.eusashead.bjugquerydsl.data.entity.Product;
import net.eusashead.bjugquerydsl.data.entity.QProduct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.types.Predicate;

/**
 * Quick demonstration of the 
 * QueryDSL integration with the
 * Spring Data JPA repositories
 * 
 * @author patrickvk
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfig.class})
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository repo;

	@Test
	@Transactional
	public void testPredicate() throws Exception {
		QProduct path = QProduct.product;
		Predicate predicate = path.name.eq("Fender Telecaster");
		Sort sort = new Sort(Sort.Direction.ASC, path.name.getMetadata().getName());
		Pageable pageable = new PageRequest(0, 10, sort);
		Page<Product> results = repo.findAll(predicate, pageable);
		Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(results.getNumberOfElements()));
	}
}
