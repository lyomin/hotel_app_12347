package org.foo.hotel.actions;

import org.foo.hotel.core.ApplicationServiceLoader;
import org.foo.hotel.core.Validators;
import org.foo.hotel.core.terminal.ActionList;
import org.foo.hotel.core.terminal.CommandAction;
import org.foo.hotel.core.terminal.InputAction;
import org.foo.hotel.core.terminal.TerminalEntries;
import org.foo.hotel.service.RoomPriceService;
import org.foo.hotel.service.RoomService;

public class Settings extends ActionList {

    private static CommandAction[] settings = {
            new InputAction(TerminalEntries.BASE_PRICE, TerminalEntries.PRICE, Validators::isPositiveInt, v -> ApplicationServiceLoader.get(RoomPriceService.class).updatePrice(Integer.valueOf(v))),
            new InputAction(TerminalEntries.ROON_COUNT, TerminalEntries.TOTAL_ROOM_COUNT, Validators::isPositiveInt, v -> ApplicationServiceLoader.get(RoomService.class).updateRoomsCount(Integer.valueOf(v)))
    };

    public Settings() {
        super(TerminalEntries.SETTINGS, settings, TerminalEntries.BACK);
    }


}
