package com.rbc.ecommerce.mapper;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.rbc.ecommerce.mappers.CategoryMapper;
import com.rbc.ecommerce.model.Category;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@MybatisTest
public class CategoryMapperTest {
	
	@Autowired
	private CategoryMapper categoryMapper;
	
	@Test
	public void getRecommendedItemCategories() {
		List<Category> categoryList = categoryMapper.getHighRatedItemCategoryListByUserId(2);
		assertNotNull(categoryList);
		assertEquals(2, categoryList.size());
		assertEquals("Electronics_Laptops", categoryList.get(0).getName());
		assertEquals("Computer_Technology_Books", categoryList.get(1).getName());
	}
	
	@Test
	public void getRecommendedItemCategories_Empty() {
		List<Category> categoryList = categoryMapper.getHighRatedItemCategoryListByUserId(3);
		assertTrue(categoryList.isEmpty());
	}
}
