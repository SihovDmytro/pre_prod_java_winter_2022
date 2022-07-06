package com.webShop.util;

import com.webShop.entity.ProductsPageBean;
import com.webShop.entity.SortOption;

public class SQLGenerator {
    private static final String PRODUCT = "product";
    private static final String AND = "and";
    private static final String LIMIT = " limit ?,?";
    private static final String ORDER_BY = " order by %s.%s %s";
    private static final String LIKE = "%s %s.%s like ?";
    private static final String GREATER_OR_EQUAL = " %s %s.%s >= ?";
    private static final String LESS_OR_EQUAL = " %s %s.%s <= ?";
    private static final String EQUAL = " %s %s.%s = ?";
    private static final String OR = "or";
    private static final String OPEN_BRACKET = " (";
    private static final String CLOSE_BRACKET = ")";
    private static final String WHERE = " where";

    public static String countProductsQuery(ProductsPageBean bean) {

        return SQLCommands.COUNT_PRODUCTS + getFilters(bean);
    }

    public static String getProductsQuery(ProductsPageBean bean) {

        return SQLCommands.GET_ALL_PRODUCTS + getFilters(bean) +
                getSort(bean) +
                getLimit();
    }

    private static String getLimit() {
        return LIMIT;
    }

    private static String getSort(ProductsPageBean bean) {
        SortOption sortOption = ProductsPageConfig.DEFAULT_SORT_OPTION;
        if (bean.getSortType() != null) {
            sortOption = bean.getSortType();
        }
        return String.format(ORDER_BY, PRODUCT, sortOption.getFieldName(), sortOption.getSortOrder());
    }

    private static String getFilters(ProductsPageBean bean) {
        StringBuilder query = new StringBuilder();
        String operator = "";
        if (!bean.isFiltersEmpty()) {
            query.append(WHERE);
            if (bean.getName() != null) {
                query.append(String.format(LIKE, operator, PRODUCT, Parameters.NAME));
                operator = AND;
            }
            if (bean.getMinPrice() != null) {
                query.append(String.format(GREATER_OR_EQUAL, operator, PRODUCT, Parameters.PRICE));
                operator = AND;
            }
            if (bean.getMaxPrice() != null) {
                query.append(String.format(LESS_OR_EQUAL, operator, PRODUCT, Parameters.PRICE));
                operator = AND;
            }
            if (bean.getCategory() != null) {
                query.append(String.format(EQUAL, operator, Parameters.CATEGORY, Parameters.NAME));
                operator = AND;
            }
            if (bean.getProducers() != null) {
                operator = operator + OPEN_BRACKET;
                for (String producer : bean.getProducers()) {
                    query.append(String.format(EQUAL, operator, Parameters.PRODUCERS, Parameters.NAME));
                    operator = OR;
                }
                query.append(CLOSE_BRACKET);
            }
        }
        return query.toString();
    }

}
