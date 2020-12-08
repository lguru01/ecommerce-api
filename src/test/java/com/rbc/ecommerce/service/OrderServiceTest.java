package com.rbc.ecommerce.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.rbc.ecommerce.mappers.CategoryMapper;
import com.rbc.ecommerce.mappers.OrderMapper;
import com.rbc.ecommerce.mappers.ProductMapper;
import com.rbc.ecommerce.model.Category;
import com.rbc.ecommerce.model.Product;
import com.rbc.ecommerce.model.SaleItems;
import com.rbc.ecommerce.service.impl.OrderServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

	@InjectMocks
	private OrderServiceImpl service;

	@Mock
	private CategoryMapper categoryMapper;

	@Mock
	private ProductMapper productMapper;

	@Mock
	private OrderMapper orderMapper;

	@Test
	public void testGetRecommendedProducts() {
		long userId = 2l;
		
		Mockito.when(categoryMapper.getHighRatedItemCategoryListByUserId(Mockito.anyLong())).thenReturn(getCategoryList());
		Mockito.when(productMapper.getRecommendedItemsByUserId(Mockito.anyList())).thenReturn(getSaleItems());
		
		List<SaleItems> saleItems = service.getRecommendedProducts(userId);
		assertNotNull(saleItems);
		assertEquals("PANASONIC_LAPTOP", saleItems.get(0).getProduct().getName());
		assertTrue(saleItems.get(0).getSalePrice().equals(new BigDecimal(500.00)));
	}
	
	@Test
	public void testGetRecommendedProducts_Empty() {
		long userId = 2l;
		
		Mockito.when(categoryMapper.getHighRatedItemCategoryListByUserId(Mockito.anyLong())).thenReturn(Collections.emptyList());
		
		List<SaleItems> saleItems = service.getRecommendedProducts(userId);
		assertNotNull(saleItems);
		assertTrue(saleItems.size() == 0);
	}

	private List<Category> getCategoryList() {
		Category category = new Category();
		category.setId(3l);
		category.setName("Electronics_Laptops");

		return Collections.singletonList(category);
	}

	private List<SaleItems> getSaleItems() {		
		SaleItems saleItems = new SaleItems();
		saleItems.setActive(true);
		saleItems.setCategory(getCategoryList().get(0));
		saleItems.setProduct(getProduct());
		saleItems.setSalePrice(new BigDecimal(500.00));

		return Collections.singletonList(saleItems);
	}
	
	private Product getProduct() {
		Product product = new Product();
		product.setId(10l);
		product.setName("PANASONIC_LAPTOP");
		product.setDesc("Panasonic Laptop 115 inch");
		product.setPrice(new BigDecimal(999.00));
		product.setCategory(getCategoryList().get(0));
		
		return product;
	}
}
