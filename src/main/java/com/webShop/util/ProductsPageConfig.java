package com.webShop.util;

import com.webShop.entity.SortOption;
import com.webShop.entity.SortOrder;

public class ProductsPageConfig {
    public static final int DEFAULT_PAGE_SIZE = 5;
    public static final int DEFAULT_PAGE = 1;
    public static final SortOption DEFAULT_SORT_OPTION = new SortOption(Constants.SORT_BY_PRICE_DESC, Parameters.PRICE, SortOrder.DESC);
    public static final int PRODUCTS_PER_LINE = 5;
    public static final int PAGINATION_RANGE = 4;
}
