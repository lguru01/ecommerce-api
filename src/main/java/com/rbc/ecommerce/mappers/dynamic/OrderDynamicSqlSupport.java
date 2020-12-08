package com.rbc.ecommerce.mappers.dynamic;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class OrderDynamicSqlSupport {

	public static final String COLUMN_ID = "o_id";
	public static final String COLUMN_USERID = "o_userid";
	public static final String COLUMN_PRODUCTID = "o_productid";
	public static final String COLUMN_RATING = "o_rating";
	public static final String COLUMN_QUANTITY = "o_quantity";
	public static final String COLUMN_CREATEDATEIME = "o_createdatetime";

	public static final OrderDetailsTable order = new OrderDetailsTable();
	public static final SqlColumn<Long> id = order.id;
	public static final SqlColumn<Long> userId = order.userId;
	public static final SqlColumn<Long> productId = order.productId;
	public static final SqlColumn<Long> rating = order.rating;
	public static final SqlColumn<Long> quantity = order.quantity;
	public static final SqlColumn<LocalDateTime> createDateTime = order.createDateTime;

	public static final List<BasicColumn> ORDER_DETAILS_COLUMNS = Collections.unmodifiableList(Arrays.asList(
			id.as(COLUMN_ID), userId.as(COLUMN_USERID), productId.as(COLUMN_PRODUCTID), 
			rating.as(COLUMN_RATING), quantity.as(COLUMN_QUANTITY), createDateTime.as(COLUMN_CREATEDATEIME)
		));

	static final class OrderDetailsTable extends SqlTable {
		final SqlColumn<Long> id = column("id", JDBCType.INTEGER);
		final SqlColumn<Long> userId = column("userId", JDBCType.INTEGER);
		final SqlColumn<Long> productId = column("productId", JDBCType.INTEGER);
		final SqlColumn<Long> rating = column("rating", JDBCType.INTEGER);
		final SqlColumn<Long> quantity = column("quantity", JDBCType.INTEGER);
		final SqlColumn<LocalDateTime> createDateTime = column("createDateTime", JDBCType.TIMESTAMP);

		OrderDetailsTable() {
			super("order_details");
		}
	}
}
