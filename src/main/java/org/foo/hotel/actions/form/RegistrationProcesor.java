package org.foo.hotel.actions.form;

import org.foo.hotel.core.terminal.ModelInputInterface;
import org.foo.hotel.model.Guest;
import org.foo.hotel.model.RoomOccupation;
import org.foo.hotel.service.RoomOccupationService;
import org.foo.hotel.service.RoomService;

import java.util.Optional;

public class RegistrationProcesor implements ModelInputInterface<RoomOccupation> {

    private final RoomOccupationService occupationService;
    private final RoomService roomService;
    private final GuestProccessor guestProccessor = new GuestProccessor();

    public RegistrationProcesor(RoomOccupationService occupationService, RoomService roomService) {
        this.occupationService = occupationService;
        this.roomService = roomService;
    }

    @Override
    public Optional<RoomOccupation> get(String abordSelection) {
        Guest guest = guestProccessor.get(abordSelection).get();
        return Optional.of(occupationService.tryOccupy(guest, roomService.getAll()));
    }
}
