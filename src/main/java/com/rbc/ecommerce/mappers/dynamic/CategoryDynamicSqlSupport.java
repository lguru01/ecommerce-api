package com.rbc.ecommerce.mappers.dynamic;

import java.sql.JDBCType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class CategoryDynamicSqlSupport {

	public static final String COLUMN_ID = "c_id";
	public static final String COLUMN_NAME = "c_name";

	public static final CategoryTable category = new CategoryTable();
	public static final SqlColumn<Long> id = category.id;
	public static final SqlColumn<String> name = category.name;

	public static final List<BasicColumn> CATEGORY_COLUMNS = Collections.unmodifiableList(Arrays.asList(
			id.as(COLUMN_ID), name.as(COLUMN_NAME)
		));

	static final class CategoryTable extends SqlTable {
		final SqlColumn<Long> id = column("id", JDBCType.INTEGER);
		final SqlColumn<String> name = column("name", JDBCType.VARCHAR);

		CategoryTable() {
			super("category");
		}
	}
}
