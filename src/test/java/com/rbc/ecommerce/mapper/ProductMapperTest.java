package com.rbc.ecommerce.mapper;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.rbc.ecommerce.mappers.ProductMapper;
import com.rbc.ecommerce.model.Category;
import com.rbc.ecommerce.model.SaleItems;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@MybatisTest
public class ProductMapperTest {
	
	@Autowired
	private ProductMapper productMapper;
	
	@Test
	public void getSaleItems() {
		List<SaleItems> saleItemList = productMapper.getRecommendedItemsByUserId(getCategories());
		assertNotNull(saleItemList);
		assertEquals(1, saleItemList.size());
		assertEquals("PANASONIC_LAPTOP", saleItemList.get(0).getProduct().getName());
	}
	
	private List<Category> getCategories() {
		Category category = new Category();
		category.setId(3l);
		category.setName("Electronics_Laptops");
		
		return Collections.singletonList(category);
	}
}
