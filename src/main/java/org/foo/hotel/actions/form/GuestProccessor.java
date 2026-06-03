package org.foo.hotel.actions.form;

import org.foo.hotel.core.terminal.ModelInputInterface;
import org.foo.hotel.core.terminal.Prompt;
import org.foo.hotel.model.Guest;

import java.util.Optional;

public class GuestProccessor implements ModelInputInterface<Guest> {
    @Override
    public Optional<Guest> get(String abordSelection) {
        String name = Prompt.read("Guest name", abordSelection);
        String surname = Prompt.read("Guest surname", abordSelection);
        return Optional.of(new Guest(name, surname));
    }
}
