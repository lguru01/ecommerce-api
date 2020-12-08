package com.rbc.ecommerce.mappers.dynamic;

import java.sql.JDBCType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class UserDynamicSqlSupport {

	public static final String COLUMN_ID = "u_id";
	public static final String COLUMN_USERNAME = "u_username";
	public static final String COLUMN_PASSWORD = "u_password";
	public static final String COLUMN_FIRSTNAME = "u_firstname";
	public static final String COLUMN_LASTNAME = "u_lastname";
	public static final String COLUMN_CITY = "u_city";

	public static final UserTable user = new UserTable();
	public static final SqlColumn<Long> id = user.id;
	public static final SqlColumn<String> userName = user.userName;
	public static final SqlColumn<String> password = user.password;
	public static final SqlColumn<String> firstname = user.firstName;
	public static final SqlColumn<String> lastname = user.lastName;
	public static final SqlColumn<String> city = user.city;

	public static final List<BasicColumn> USER_COLUMNS = Collections.unmodifiableList(Arrays.asList(
			id.as(COLUMN_ID), userName.as(COLUMN_USERNAME), password.as(COLUMN_PASSWORD), 
			firstname.as(COLUMN_FIRSTNAME), lastname.as(COLUMN_LASTNAME), city.as(COLUMN_CITY)
		));

	static final class UserTable extends SqlTable {
		final SqlColumn<Long> id = column("id", JDBCType.INTEGER);
		final SqlColumn<String> userName = column("username", JDBCType.VARCHAR);
		final SqlColumn<String> password = column("password", JDBCType.VARCHAR);
		final SqlColumn<String> firstName = column("firstname", JDBCType.VARCHAR);
		final SqlColumn<String> lastName = column("lastname", JDBCType.VARCHAR);
		final SqlColumn<String> city = column("city", JDBCType.VARCHAR);

		UserTable() {
			super("user");
		}
	}
}
