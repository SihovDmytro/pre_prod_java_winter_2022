package com.shop.strategy;

import com.shop.util.RandomUtil;

import java.math.BigDecimal;
import java.util.Calendar;

public class RandomFiller extends Filler {
    @Override
    public String fillString() {
        return RandomUtil.randomString("string");
    }

    @Override
    public int fillInt() {
        return RandomUtil.randomInt();
    }

    @Override
    public BigDecimal fillBigDecimal() {
        return new BigDecimal(RandomUtil.randomInt());
    }

    @Override
    public Calendar fillDate() {
        return RandomUtil.randomCalendar();
    }
}
