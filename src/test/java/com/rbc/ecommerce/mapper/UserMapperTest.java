package com.rbc.ecommerce.mapper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.rbc.ecommerce.mappers.UserMapper;
import com.rbc.ecommerce.model.User;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@MybatisTest
public class UserMapperTest {
	
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void authenticateUser() {
		Optional<User> user = userMapper.authenticateUser("maria@abc.com", "mariapass");
		assertTrue(user.isPresent());
	}
	
	@Test
	public void authenticateUser_NotFound() {
		Optional<User> user = userMapper.authenticateUser("dummy@abc.com", "mariapass");
		assertFalse(user.isPresent());
	}
}
