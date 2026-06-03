package org.foo.hotel.model;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public record Price(BigInteger amount, String tag) {
    public Price(String price, String tag) {
        this (BigInteger.valueOf(Long.valueOf(price)), tag);
    }

    @NotNull
    @Override
    public String toString() {
        return "%8.2f %-3s".formatted(amount.doubleValue()/100, tag);
    }
}
