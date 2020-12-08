package com.rbc.ecommerce.service;

import static com.rbc.ecommerce.config.CachingConfig.CACHE_SALE_ITEMS;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.rbc.ecommerce.EcommerceApplication;
import com.rbc.ecommerce.config.CachingConfig;
import com.rbc.ecommerce.model.SaleItems;
import com.rbc.ecommerce.service.impl.OrderServiceImpl;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@SpringBootTest(classes = {EcommerceApplication.class})
public class OrderServiceCacheTest {
	
	@Autowired
	private OrderServiceImpl orderService;
	
	@Autowired
	private CachingConfig cachingConfig;
	
	
	@Test
	public void testGetRecommendedProducts_Cache() {
		long userId = 2l;		
	
		List<SaleItems> saleItems = orderService.getRecommendedProducts(userId);
		assertNotNull(saleItems);
		assertEquals(2, saleItems.size());
		assertEquals("PANASONIC_LAPTOP", saleItems.get(0).getProduct().getName());
		
		Cache cache = cachingConfig.cacheManager().getCache(CACHE_SALE_ITEMS);
		assertNotNull(cache.get(userId));
		
		orderService.clearSaleItemsCache();
		assertNull(cache.get(userId));
	}

}
