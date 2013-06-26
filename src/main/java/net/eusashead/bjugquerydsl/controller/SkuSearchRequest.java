package net.eusashead.bjugquerydsl.controller;

import java.math.BigDecimal;
import java.util.Map;

public class SkuSearchRequest {
	
	private final BigDecimal minPrice;
	
	private final BigDecimal maxPrice;
	
	private final Map<String, String[]> attributes;

	public SkuSearchRequest(BigDecimal minPrice, BigDecimal maxPrice,
			Map<String, String[]> attributes) {
		super();
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.attributes = attributes;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public Map<String, String[]> getAttributes() {
		return attributes;
	}

}
