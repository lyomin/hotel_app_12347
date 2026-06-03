package org.foo.hotel;

import org.foo.hotel.actions.Main;
import org.foo.hotel.core.terminal.CommandProcessor;

import java.io.IOException;

public class Application {
    public static void main(String ... args) throws IOException {
        new CommandProcessor(new Main()).run();
    }
}
