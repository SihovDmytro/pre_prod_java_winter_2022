package com.webShop.entity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

public class ProductsPageBean {
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String name;
    private String category;
    private String[] producers;
    private int pageSize;
    private int page;
    private SortOption sortType;

    public ProductsPageBean() {
    }

    public ProductsPageBean(BigDecimal minPrice, BigDecimal maxPrice, String name, String category, String[] producers, int pageSize, int page, SortOption sortType) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.name = name;
        this.category = category;
        this.producers = producers;
        this.pageSize = pageSize;
        this.page = page;
        this.sortType = sortType;
    }

    public boolean isFiltersEmpty() {
        return minPrice == null && maxPrice == null && name == null && category == null && producers == null;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String[] getProducers() {
        return producers;
    }

    public void setProducers(String[] producers) {
        this.producers = producers;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public SortOption getSortType() {
        return sortType;
    }

    public void setSortType(SortOption sortType) {
        this.sortType = sortType;
    }

    @Override
    public String toString() {
        return "ProductsPageBean{" +
                "minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", producers=" + Arrays.toString(producers) +
                ", pageSize=" + pageSize +
                ", page=" + page +
                ", sortType=" + sortType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductsPageBean that = (ProductsPageBean) o;
        return pageSize == that.pageSize && Objects.equals(minPrice, that.minPrice)
                && Objects.equals(maxPrice, that.maxPrice)
                && Objects.equals(name, that.name)
                && Objects.equals(category, that.category)
                && Arrays.equals(producers, that.producers)
                && sortType.equals(that.sortType);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(minPrice, maxPrice, name, category, pageSize, sortType);
        result = 31 * result + Arrays.hashCode(producers);
        return result;
    }
}
