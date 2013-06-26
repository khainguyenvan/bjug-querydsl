package net.eusashead.bjugquerydsl.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class SkuSearchRequestBuilder {
	
	private BigDecimal minPrice;
	
	private BigDecimal maxPrice;
	
	private Map<String, String[]> attributes = new HashMap<String, String[]>();
	
	public SkuSearchRequestBuilder minPrice(double min) {
		this.minPrice = BigDecimal.valueOf(min);
		return this;
	}
	
	public SkuSearchRequestBuilder maxPrice(double max) {
		this.maxPrice = BigDecimal.valueOf(max);
		return this;
	}

	public SkuSearchRequestBuilder attribute(String attr, String... values) {
		this.attributes.put(attr, values);
		return this;
	}
	
	public SkuSearchRequest build() {
		SkuSearchRequest request = new SkuSearchRequest(
				this.minPrice, this.maxPrice, this.attributes);
		return request;
	}
}
