package org.foo.hotel.actions;

import org.foo.hotel.actions.form.RoomIdSelector;
import org.foo.hotel.core.terminal.CommandAction;
import org.foo.hotel.core.terminal.TerminalEntries;
import org.foo.hotel.model.Room;
import org.foo.hotel.model.RoomOccupation;
import org.foo.hotel.service.RoomOccupationService;

import java.util.List;
import java.util.Optional;

public class GestCheckOut implements CommandAction {
    private final RoomOccupationService occupationService;
    private final RoomIdSelector roomForm = new RoomIdSelector();

    public GestCheckOut(RoomOccupationService occupationService) {
        this.occupationService = occupationService;
    }

    @Override
    public String getName() {
        return TerminalEntries.RESIDENT_CHECKOUT;
    }


    @Override
    public boolean exec() {
            List<RoomOccupation> occupations = occupationService.getAll();

            Optional<Room> room = roomForm.get(occupations.stream().map(RoomOccupation::room).toList());

            if (room.isEmpty()) {
                System.out.println("Room not found.");
                return false;
            }

            occupationService.freeRoom(room.get());
            System.out.println("Occupation freed.");
            return true;
    }
}
