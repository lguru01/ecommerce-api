package com.rbc.ecommerce.mappers;

import static com.rbc.ecommerce.mappers.dynamic.CategoryDynamicSqlSupport.category;
import static com.rbc.ecommerce.mappers.dynamic.ProductDynamicSqlSupport.product;
import static com.rbc.ecommerce.mappers.dynamic.SaleItemsDynamicSqlSupport.saleitems;

import java.time.LocalDate;
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
import org.mybatis.dynamic.sql.select.SimpleSortSpecification;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;
import org.springframework.stereotype.Repository;

import com.rbc.ecommerce.mappers.dynamic.CategoryDynamicSqlSupport;
import com.rbc.ecommerce.mappers.dynamic.ProductDynamicSqlSupport;
import com.rbc.ecommerce.mappers.dynamic.SaleItemsDynamicSqlSupport;
import com.rbc.ecommerce.model.Category;
import com.rbc.ecommerce.model.SaleItems;

@Repository
@Mapper
public interface ProductMapper {
	
	List<BasicColumn> PRODUCT_DETAILS = Stream.of(ProductDynamicSqlSupport.PRODUCT_COLUMNS.stream(),
			SaleItemsDynamicSqlSupport.DISCOUNT_COLUMNS.stream(), 
			CategoryDynamicSqlSupport.CATEGORY_COLUMNS.stream()).flatMap(Function.identity()).collect(Collectors.toList());
	
	default List<SaleItems> getRecommendedItemsByUserId(List<Category> categories) {
		return select(completer -> completer
				.where(SaleItemsDynamicSqlSupport.categoryId, 
						SqlBuilder.isIn(categories.stream().map(Category::getId).collect(Collectors.toList())))
				.and(SaleItemsDynamicSqlSupport.active, SqlBuilder.isTrue())
				.and(SaleItemsDynamicSqlSupport.createDate, SqlBuilder.isEqualTo(LocalDate.now()))
				.orderBy(SimpleSortSpecification.of(CategoryDynamicSqlSupport.COLUMN_ID).descending()));
	}
	
	default List<SaleItems> select(SelectDSLCompleter completer) {
		QueryExpressionDSL.FromGatherer<SelectModel> select = SqlBuilder.selectDistinct(PRODUCT_DETAILS);
		QueryExpressionDSL<SelectModel> initialQuery = select
				.from(saleitems)
				.join(product, SqlBuilder.on(SaleItemsDynamicSqlSupport.productId, SqlBuilder.equalTo(ProductDynamicSqlSupport.id)))
				.join(category, SqlBuilder.on(ProductDynamicSqlSupport.categoryId, SqlBuilder.equalTo(CategoryDynamicSqlSupport.id)));
		return MyBatis3Utils.selectList(this::getItemDetailsOnSale, initialQuery, completer);
	}
	
	@SelectProvider(value = SqlProviderAdapter.class, method = "select")
	@Results(value = {
		@Result(property = "id", column = SaleItemsDynamicSqlSupport.COLUMN_ID),
		@Result(property = "product.id", column = ProductDynamicSqlSupport.COLUMN_ID),
		@Result(property = "product.name", column = ProductDynamicSqlSupport.COLUMN_NAME),
		@Result(property = "product.desc", column = ProductDynamicSqlSupport.COLUMN_DESC),
		@Result(property = "salePrice", column = SaleItemsDynamicSqlSupport.COLUMN_SALEPRICE),
		@Result(property = "product.price", column = ProductDynamicSqlSupport.COLUMN_PRICE),
		@Result(property = "category.name", column = CategoryDynamicSqlSupport.COLUMN_NAME)
	})
	List<SaleItems> getItemDetailsOnSale(SelectStatementProvider statementProvider);
}
