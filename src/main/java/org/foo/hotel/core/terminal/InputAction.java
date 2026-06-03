package org.foo.hotel.core.terminal;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class InputAction implements CommandAction {

    final String commandName;
    final String prompt;
    private final Predicate<String> validator;
    private final Consumer<String> valueSetter;

    public InputAction(String commandName, String prompt, Predicate<String> validator, Consumer<String> valueSetter) {
        this.commandName = commandName;
        this.prompt = prompt;
        this.validator = validator;
        this.valueSetter = valueSetter;
    }

    @Override
    public String getName() {
        return commandName;
    }

    @Override
    public CommandAction[] getCommands() {
        return CommandAction.noActions();
    }

    @Override
    public String aboardCommand() {
        return TerminalEntries.BACK;
    }

    public Procedure getProcedure() {
        return () -> {

                String input = Prompt.read(prompt, aboardCommand());
                if (aboardCommand().equals(input)) {
                    return true;
                }

                if(!validator.test(input)) {
                    return false;
                }

                valueSetter.accept(input);

            return true;
        };
    }
}
