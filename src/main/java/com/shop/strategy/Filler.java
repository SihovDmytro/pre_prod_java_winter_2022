package com.shop.strategy;

import java.math.BigDecimal;
import java.util.Calendar;

public abstract class Filler {
    public abstract String fillString();

    public abstract int fillInt();

    public abstract BigDecimal fillBigDecimal();

    public abstract Calendar fillDate();
}
