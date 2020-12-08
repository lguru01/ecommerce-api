package com.rbc.ecommerce.model;

import java.math.BigDecimal;

public class SaleItems {
	private Long id;
	private Product product;
	private Category category;
	private BigDecimal salePrice;
	private Boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
}
