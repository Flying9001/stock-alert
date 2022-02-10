package com.ljq.stock.alert.common.constant;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

class StockIndexEnumTest {

    @Test
    void testToString() {
        System.out.println(Arrays.toString(StockIndexEnum.values()));
        System.out.println("----------------");
        StringBuilder indexBuilder = new StringBuilder();
        for (StockIndexEnum indexEnum : StockIndexEnum.values()) {
            indexBuilder.append(indexEnum.name()).append(",");
        }
        System.out.println(indexBuilder);
    }
}