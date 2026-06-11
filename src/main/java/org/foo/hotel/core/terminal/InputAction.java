package org.foo.hotel.core.terminal;

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


    public boolean exec() {

                String input = Prompt.read(prompt);
                if (TerminalEntries.BACK.equals(input)) {
                    return true;
                }

                if(!validator.test(input)) {
                    return false;
                }

                valueSetter.accept(input);

            return true;
    }
}
