package com.rbc.ecommerce.mappers;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.rbc.ecommerce.model.User;

@Repository
@Mapper
public interface UserMapper {
	
	@Select("SELECT id, firstname, lastname, city FROM user WHERE username=#{username} AND password=#{password}")
	@Results(value = {
		@Result(property = "id", column = "id"),
		@Result(property = "firstName", column = "firstname"),
		@Result(property = "lastName", column = "lastname"),
		@Result(property = "city", column = "city"),
	})
	Optional<User> authenticateUser(String username, String password);
}
