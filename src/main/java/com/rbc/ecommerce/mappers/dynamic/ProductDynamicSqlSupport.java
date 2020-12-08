package com.rbc.ecommerce.mappers.dynamic;

import java.math.BigDecimal;
import java.sql.JDBCType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class ProductDynamicSqlSupport {

	public static final String COLUMN_ID = "p_id";
	public static final String COLUMN_NAME = "p_name";
	public static final String COLUMN_DESC = "p_desc";
	public static final String COLUMN_PRICE = "p_price";
	public static final String COLUMN_CATEGORYID = "p_categoryid";

	public static final ProductTable product = new ProductTable();
	public static final SqlColumn<Long> id = product.id;
	public static final SqlColumn<String> name = product.name;
	public static final SqlColumn<String> desc = product.desc;
	public static final SqlColumn<BigDecimal> price = product.price;
	public static final SqlColumn<Long> categoryId = product.categoryId;

	public static final List<BasicColumn> PRODUCT_COLUMNS = Collections.unmodifiableList(Arrays.asList(
			id.as(COLUMN_ID), name.as(COLUMN_NAME), desc.as(COLUMN_DESC), 
			price.as(COLUMN_PRICE), categoryId.as(COLUMN_CATEGORYID)
		));

	static final class ProductTable extends SqlTable {
		final SqlColumn<Long> id = column("id", JDBCType.INTEGER);
		final SqlColumn<String> name = column("name", JDBCType.VARCHAR);
		final SqlColumn<String> desc = column("desc", JDBCType.VARCHAR);
		final SqlColumn<BigDecimal> price = column("price", JDBCType.DECIMAL);
		final SqlColumn<Long> categoryId = column("categoryId", JDBCType.INTEGER);

		ProductTable() {
			super("product");
		}
	}
}
