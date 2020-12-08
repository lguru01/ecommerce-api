package com.rbc.ecommerce.mappers;

import static com.rbc.ecommerce.mappers.dynamic.CategoryDynamicSqlSupport.category;
import static com.rbc.ecommerce.mappers.dynamic.OrderDynamicSqlSupport.order;
import static com.rbc.ecommerce.mappers.dynamic.ProductDynamicSqlSupport.product;
import static com.rbc.ecommerce.mappers.dynamic.UserDynamicSqlSupport.user;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;
import org.springframework.stereotype.Repository;

import com.rbc.ecommerce.mappers.dynamic.CategoryDynamicSqlSupport;
import com.rbc.ecommerce.mappers.dynamic.OrderDynamicSqlSupport;
import com.rbc.ecommerce.mappers.dynamic.ProductDynamicSqlSupport;
import com.rbc.ecommerce.mappers.dynamic.UserDynamicSqlSupport;
import com.rbc.ecommerce.model.Category;

@Repository
@Mapper
public interface CategoryMapper {
	
	List<BasicColumn> CATEGORY_DETAILS = Stream.of(
			OrderDynamicSqlSupport.ORDER_DETAILS_COLUMNS.stream(), UserDynamicSqlSupport.USER_COLUMNS.stream(), 
			CategoryDynamicSqlSupport.CATEGORY_COLUMNS.stream(), ProductDynamicSqlSupport.PRODUCT_COLUMNS.stream()
			).flatMap(Function.identity()).collect(Collectors.toList());

	default List<Category> getHighRatedItemCategoryListByUserId(long userId) {
		return select(userId, completer -> completer.where(UserDynamicSqlSupport.id, SqlBuilder.isEqualTo(userId)));
	}

	default List<Category> select(long userId, SelectDSLCompleter completer) {
		QueryExpressionDSL.FromGatherer<SelectModel> select = SqlBuilder.selectDistinct(CATEGORY_DETAILS);
		QueryExpressionDSL<SelectModel> initialQuery = select.from(order)
				.join(user, SqlBuilder.on(OrderDynamicSqlSupport.userId, SqlBuilder.equalTo(UserDynamicSqlSupport.id)))
				.join(product,
						SqlBuilder.on(OrderDynamicSqlSupport.productId,
								SqlBuilder.equalTo(ProductDynamicSqlSupport.id)))
				.join(category, SqlBuilder.on(ProductDynamicSqlSupport.categoryId,
						SqlBuilder.equalTo(CategoryDynamicSqlSupport.id)));
		return MyBatis3Utils.selectList(this::getRecommendedCategories, initialQuery, completer);
	}

	@SelectProvider(value = SqlProviderAdapter.class, method = "select")
	@Results(value = { @Result(property = "id", column = CategoryDynamicSqlSupport.COLUMN_ID),
			@Result(property = "name", column = CategoryDynamicSqlSupport.COLUMN_NAME)
	})
	List<Category> getRecommendedCategories(SelectStatementProvider statementProvider);
}
