package org.foo.hotel.core;

public class Validators {
    public static boolean isPositiveInt(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            int value = Integer.parseInt(str);
            return value >= 0; // Tekstas sėkmingai paverstas į int
        } catch (NumberFormatException e) {
            return false; // Tekstas nėra skaičius arba viršija int ribas
        }
    }
}
