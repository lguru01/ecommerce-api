package com.rbc.ecommerce.service;

import java.util.List;

import com.rbc.ecommerce.model.OrderDetails;
import com.rbc.ecommerce.model.SaleItems;

public interface OrderService {
	List<OrderDetails> getOrdersByUserId(long id);
	
	List<SaleItems> getRecommendedProducts(long id);
}
