package org.foo.hotel.core.terminal;


public abstract class ActionList implements CommandList {

    private final String commandName;
    private final Command[] commands;

    protected ActionList(String commandName, Command[] commands) {
        this.commandName = commandName;
        this.commands = commands;
    }

    @Override
    public String getName() {
        return commandName;
    }

    @Override
    public Command[] getCommands() {
        return commands;
    }

}
