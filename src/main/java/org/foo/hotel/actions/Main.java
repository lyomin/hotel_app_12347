package org.foo.hotel.actions;

import org.foo.hotel.core.ApplicationServiceLoader;
import org.foo.hotel.core.terminal.ActionList;
import org.foo.hotel.core.terminal.Command;
import org.foo.hotel.core.terminal.TerminalEntries;
import org.foo.hotel.service.RoomOccupationService;
import org.foo.hotel.service.RoomService;

public class Main extends ActionList {
    private static final Command[] commands = {
            new RegisterGuest(
                    ApplicationServiceLoader.get(RoomOccupationService.class),
                    ApplicationServiceLoader.get(RoomService.class)
            ),
            new GestCheckOut(
                    ApplicationServiceLoader.get(RoomOccupationService.class)),
            new RoomOcuppation(
                    ApplicationServiceLoader.get(RoomOccupationService.class),
                    ApplicationServiceLoader.get(RoomService.class)),
            new Reports(),
            new Settings()
    };

    public Main() {
        super(TerminalEntries.APPLICATION, commands);
    }
}
