package org.foo.hotel.core;

public enum ConfigurationMapping {
    ROOM_COUNT("6"), ROOM_BASE_PRICE("2000");

    ConfigurationMapping(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    private String defaultValue;

    public String getDefaultValue() {
        return defaultValue;
    }
}
