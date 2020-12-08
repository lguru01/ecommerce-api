package com.rbc.ecommerce.mappers.dynamic;

import java.math.BigDecimal;
import java.sql.JDBCType;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class SaleItemsDynamicSqlSupport {

	public static final String COLUMN_ID = "d_id";
	public static final String COLUMN_PRODUCTID = "d_productid";
	public static final String COLUMN_CATEGORYID = "d_categoryid";
	public static final String COLUMN_SALEPRICE = "d_saleprice";
	public static final String COLUMN_ACTIVE = "d_active";
	public static final String COLUMN_CREATEDATE = "d_createdate";

	public static final SaleItemsTable saleitems = new SaleItemsTable();
	public static final SqlColumn<Long> id = saleitems.id;
	public static final SqlColumn<Long> productId = saleitems.productId;
	public static final SqlColumn<Long> categoryId = saleitems.categoryId;
	public static final SqlColumn<BigDecimal> salePrice = saleitems.salePrice;
	public static final SqlColumn<Boolean> active = saleitems.active;
	public static final SqlColumn<LocalDate> createDate = saleitems.createDate;

	public static final List<BasicColumn> DISCOUNT_COLUMNS = Collections.unmodifiableList(Arrays.asList(
			id.as(COLUMN_ID), productId.as(COLUMN_PRODUCTID), categoryId.as(COLUMN_CATEGORYID), 
			salePrice.as(COLUMN_SALEPRICE), active.as(COLUMN_ACTIVE), createDate.as(COLUMN_CREATEDATE)
		));

	static final class SaleItemsTable extends SqlTable {
		final SqlColumn<Long> id = column("id", JDBCType.INTEGER);
		final SqlColumn<Long> productId = column("productId", JDBCType.INTEGER);
		final SqlColumn<Long> categoryId = column("categoryId", JDBCType.INTEGER);
		final SqlColumn<BigDecimal> salePrice = column("salePrice", JDBCType.DECIMAL);
		final SqlColumn<Boolean> active = column("active", JDBCType.BOOLEAN);
		final SqlColumn<LocalDate> createDate = column("createDate", JDBCType.DATE);

		SaleItemsTable() {
			super("sale_items");
		}
	}
}
