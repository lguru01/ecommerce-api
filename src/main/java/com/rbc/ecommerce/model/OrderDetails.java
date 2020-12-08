package com.rbc.ecommerce.model;

import java.util.List;

public class OrderDetails {

	private Long id;
	private User user;
	private Product product;
	private int rating;
	private int quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
