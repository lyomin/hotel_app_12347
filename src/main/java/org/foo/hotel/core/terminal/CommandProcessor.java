package org.foo.hotel.core.terminal;

import org.foo.hotel.core.exceptions.NoOperationException;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;

public class CommandProcessor {
    CommandList rootAction;

    public CommandProcessor(CommandList rootAction) {
        this.rootAction = rootAction;
    }

    public void run() throws IOException {
        Command currentAction = rootAction;
        Deque<Command> commandActions = new LinkedList<>();


        String selected = "";
        do {
            try {
                if (currentAction instanceof Procedure procedure) {
                // Access the command. If unsuccessful repeat.

                    cleanScreen();
                    if (procedure.exec()) {
                        Prompt.read(TerminalEntries.ENTER_KEY_TO_CONTINUE);
                        // Gets list of stored commands. If it contains only procedure rollback to parent command.
                        if (!commandActions.isEmpty()) {
                            currentAction = commandActions.pop();
                            continue;
                        }
                    }
                } else if (currentAction instanceof CommandList list && (list.getCommands() != null || list.getCommands().length != 0)) {
                    cleanScreen();
                    selected = Prompt.read(getCommandsPrompt(list), currentAction.aboardCommand());

                    Command action = getSelectedCommand(selected, list.getCommands());

                    if (action != null) {
                        commandActions.push(currentAction);
                        currentAction = action;
                    }


                } else {
                    throw new NoOperationException(TerminalEntries.NO_ACTION_OR_PROCEDURE);
                }
            } catch (AboardException e) {
                if (!commandActions.isEmpty()) {
                    currentAction = commandActions.pop();
                } else {
                    return;
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                Prompt.read(TerminalEntries.ENTER_KEY_TO_CONTINUE);
                if (!commandActions.isEmpty()) {
                    currentAction = commandActions.pop();
                }
            }
        } while (!rootAction.aboardCommand().equals(selected));
    }

    private Command getSelectedCommand(String name, Command[] commands) {
        for (Command command : commands) {
            if (command.getName().equals(name))
                return command;
        }
        return null;
    }

    private String getCommandsPrompt(CommandList currentAction) {
        String commands = "Action " + currentAction.getName() + " commands: ";

        for (Command action : currentAction.getCommands()) {
            commands += "\n" + action.getName();
        }

        commands += "\nto aborad type: " + currentAction.aboardCommand();

        return commands + "\nSelect command";
    }

    private void cleanScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
