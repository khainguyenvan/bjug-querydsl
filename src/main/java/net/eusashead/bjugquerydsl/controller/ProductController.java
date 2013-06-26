package net.eusashead.bjugquerydsl.controller;

import net.eusashead.bjugquerydsl.data.entity.Product;
import net.eusashead.bjugquerydsl.data.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/product", produces={"application/json;charset=UTF-8"})
public class ProductController {
	
	@Autowired 
	private ProductRepository productRepo;
	
	@Autowired
	private FieldInitializer initializer;
	
	@RequestMapping(method=RequestMethod.GET)
	@Transactional
	public ResponseEntity<Page<Product>> findAll(Pageable page) {
		Page<Product> products = productRepo.findAll(page);
		initializer.initializeCollection(products);
		return new ResponseEntity<Page<Product>>(products, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	@Transactional
	public ResponseEntity<Product> findOne(@PathVariable Integer id) {
		Product product = productRepo.findOne(id);
		initializer.initializeEntity(product);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

}
