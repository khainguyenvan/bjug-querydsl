package net.eusashead.bjugquerydsl.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.eusashead.bjugquerydsl.data.entity.Attribute;
import net.eusashead.bjugquerydsl.data.entity.Product;
import net.eusashead.bjugquerydsl.data.entity.QProduct;
import net.eusashead.bjugquerydsl.data.entity.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysema.query.jpa.impl.JPAQuery;

@Controller
@RequestMapping(value="/product", produces={"application/json;charset=UTF-8"})
public class ProductController {
	
	@PersistenceContext
	private EntityManager em;
	
	@RequestMapping(method=RequestMethod.GET)
	@Transactional
	public ResponseEntity<List<Product>> findAll() {
		JPAQuery query = new JPAQuery(em);
		QProduct qProduct = QProduct.product;
		List<Product> products = query.from(qProduct)
				.list(qProduct);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	@Transactional
	public ResponseEntity<Product> findOne(@PathVariable Integer id) {
		JPAQuery query = new JPAQuery(em);
		QProduct qProduct = QProduct.product;
		Product product = query.from(qProduct)
				.where(qProduct.productId.eq(id))
				.uniqueResult(qProduct);
		for (Attribute attr : product.getAttributes()) {
			for(Value val : attr.getValues()) {
				val.getName();
			}
		}
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	// TODO use a map of attr/values as per
	// http://stackoverflow.com/questions/7312436/spring-mvc-how-to-get-all-request-params-in-a-map-in-spring-controller
	@RequestMapping(value="/search/", method=RequestMethod.GET)
	@Transactional
	public ResponseEntity<List<Product>> search(@RequestParam("attribute") String attribute,
			@RequestParam("value") String value) {
		JPAQuery query = new JPAQuery(em);
		QProduct qProduct = QProduct.product;
		List<Product> products = query.from(qProduct)
				.where(qProduct.attributes.any().name.eq(attribute)
						.and(qProduct.attributes.any().values.any().name.eq(value)))
				.list(qProduct);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
}
