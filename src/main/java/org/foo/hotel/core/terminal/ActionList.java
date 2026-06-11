package org.foo.hotel.core.terminal;

import java.io.Console;

public abstract class ActionList implements CommandList {

    private final String commandName;
    private final Command[] commands;
    private final String back;

    protected ActionList(String commandName, Command[] commands, String back) {
        this.commandName = commandName;
        this.commands = commands;
        this.back = back;
    }

    @Override
    public String getName() {
        return commandName;
    }

    @Override
    public Command[] getCommands() {
        return commands;
    }

    @Override
    public String aboardCommand() {
        return back;
    }
}
