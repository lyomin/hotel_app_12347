package org.foo.hotel.core.terminal;

public interface Procedure {
    boolean exec();
    public static Procedure enpty() {
        return () -> true;
    }
}
