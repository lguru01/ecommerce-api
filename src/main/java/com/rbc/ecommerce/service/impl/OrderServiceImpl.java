package com.rbc.ecommerce.service.impl;

import static com.rbc.ecommerce.config.CachingConfig.CACHE_SALE_ITEMS;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.rbc.ecommerce.mappers.CategoryMapper;
import com.rbc.ecommerce.mappers.OrderMapper;
import com.rbc.ecommerce.mappers.ProductMapper;
import com.rbc.ecommerce.model.Category;
import com.rbc.ecommerce.model.OrderDetails;
import com.rbc.ecommerce.model.SaleItems;
import com.rbc.ecommerce.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	private final OrderMapper orderMapper;
	private final ProductMapper productMapper;
	private final CategoryMapper categotyMapper;
	
	public OrderServiceImpl(OrderMapper orderMapper, ProductMapper productMapper, CategoryMapper categotyMapper) {
		this.orderMapper = orderMapper;
		this.productMapper = productMapper;
		this.categotyMapper = categotyMapper;
	}

	@Override
	public List<OrderDetails> getOrdersByUserId(long userId) {
		return orderMapper.getOrdersByUserId(userId);		
	}

	/**
	 * Cache sale items of the user will be clear at 5AM and new sale items will
	 * be cached for the current date.
	 */
	@Cacheable(value = CACHE_SALE_ITEMS, key="#userId", unless="#result==null or #result.size()==0")
	@Override
	public List<SaleItems> getRecommendedProducts(long userId) {
		List<Category> categories = categotyMapper.getHighRatedItemCategoryListByUserId(userId);
		if(!categories.isEmpty()) {
			return productMapper.getRecommendedItemsByUserId(categories);
		} 
		
		return Collections.emptyList();
	}	
	
	@Scheduled(cron="${cronExpression}")
	@CacheEvict(value = CACHE_SALE_ITEMS, allEntries = true)
	public void clearSaleItemsCache() {
		logger.info("Cleared Cache");
	}

}
