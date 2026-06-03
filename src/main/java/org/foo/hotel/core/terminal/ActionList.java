package org.foo.hotel.core.terminal;

import java.io.Console;

public abstract class ActionList implements CommandAction {

    private final String commandName;
    private final CommandAction[] commands;
    private final String back;

    protected ActionList(String commandName, CommandAction[] commands, String back) {
        this.commandName = commandName;
        this.commands = commands;
        this.back = back;
    }

    @Override
    public String getName() {
        return commandName;
    }

    @Override
    public CommandAction[] getCommands() {
        return commands;
    }

    @Override
    public String aboardCommand() {
        return back;
    }

    @Override
    public Procedure getProcedure() {
            return null;
    }
}
