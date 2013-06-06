package net.eusashead.bjugquerydsl.dto;

import java.math.BigDecimal;

public class SkuDto {
	
	private final Integer skuId;
	
	private final String name;
	
	private final BigDecimal price;

	public SkuDto(Integer skuId, String name, BigDecimal price) {
		super();
		this.skuId = skuId;
		this.name = name;
		this.price = price;
	}
	
	public Integer getSkuId() {
		return skuId;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

}
