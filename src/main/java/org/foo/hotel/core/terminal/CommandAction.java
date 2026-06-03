package org.foo.hotel.core.terminal;

public interface CommandAction {
    String getName();
    CommandAction[] getCommands();
    String aboardCommand();
    Procedure getProcedure();
    public static CommandAction[] noActions() {
        return new CommandAction[] {};
    }
}
