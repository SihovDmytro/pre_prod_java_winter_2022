package com.webShop.entity;

import java.util.Objects;

public class SortOption {
    private String sortName;
    private String fieldName;
    private SortOrder sortOrder;

    public SortOption() {
    }

    public SortOption(String sortName, String fieldName, SortOrder sortOrder) {
        this.sortName = sortName;
        this.fieldName = fieldName;
        this.sortOrder = sortOrder;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return "SortOption{" +
                "sortName='" + sortName + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", sortOrder=" + sortOrder +
                '}';
    }

    public static SortOption valueOf(Object object) {
        return (SortOption) object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortOption that = (SortOption) o;
        return Objects.equals(sortName, that.sortName)
                && Objects.equals(fieldName, that.fieldName)
                && sortOrder == that.sortOrder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sortName, fieldName, sortOrder);
    }
}
