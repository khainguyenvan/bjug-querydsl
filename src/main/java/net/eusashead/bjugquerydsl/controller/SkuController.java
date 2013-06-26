package net.eusashead.bjugquerydsl.controller;

import net.eusashead.bjugquerydsl.data.entity.QStockKeepingUnit;
import net.eusashead.bjugquerydsl.data.entity.StockKeepingUnit;
import net.eusashead.bjugquerydsl.data.repository.StockKeepingUnitRepository;
import net.eusashead.bjugquerydsl.hateoas.HalPageResponseEntityBuilder;
import net.eusashead.bjugquerydsl.hateoas.HalResponseEntityBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.theoryinpractise.halbuilder.api.ReadableRepresentation;

@Controller
@RequestMapping(value="/sku")
public class SkuController {
	
	/**
	 * Repository for handling SKUs
	 */
	@Autowired 
	private StockKeepingUnitRepository skuRepo;
	
	/**
	 * Utility to detach/initialize lazy fields
	 */
	@Autowired
	private FieldInitializer initializer;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	@Transactional
	public ResponseEntity<ReadableRepresentation> findAll(SkuSearchRequest request, HalPageResponseEntityBuilder halBuilder, Pageable pageable) {
		Page<StockKeepingUnit> products = skuRepo.findAll(QStockKeepingUnit.stockKeepingUnit.search(request), pageable);
		return halBuilder.withPage(products, "/sku/{skuId}").etag().get();
	}
	
	@RequestMapping(value="/{id}", 
			method=RequestMethod.GET)
	@Transactional
	public ResponseEntity<ReadableRepresentation> findOne(@PathVariable("id") Integer id, HalResponseEntityBuilder halBuilder) {
		StockKeepingUnit sku = skuRepo.findOne(id);
		return halBuilder.withBean(sku).etag().get();
	}

}
