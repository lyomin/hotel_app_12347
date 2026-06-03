package org.foo.hotel.service;

import org.foo.hotel.model.Price;
import org.foo.hotel.model.RoomType;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class RoomPriceService {
    private static Map<RoomType, Price> typeToPrice = new HashMap<>();
    public void updatePrice(int bacePrice) {
        typeToPrice.put(RoomType.STANDART, new Price(BigInteger.valueOf(bacePrice), "$"));
        typeToPrice.put(RoomType.BUSINESS, new Price(BigInteger.valueOf(bacePrice*150/100), "$"));
    }
    public Price getPrice(RoomType type) {
        return typeToPrice.get(type);
    }
}
