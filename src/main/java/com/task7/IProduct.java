package com.task7;

import java.math.BigDecimal;

public interface IProduct {
    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    String getName();

    void setName(String name);
}
