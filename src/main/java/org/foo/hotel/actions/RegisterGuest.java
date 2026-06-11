package org.foo.hotel.actions;

import org.foo.hotel.actions.form.RegistrationProcesor;
import org.foo.hotel.core.terminal.AboardException;
import org.foo.hotel.core.terminal.CommandAction;
import org.foo.hotel.core.terminal.TerminalEntries;
import org.foo.hotel.service.RoomOccupationService;
import org.foo.hotel.service.RoomService;

import java.util.Optional;

public class RegisterGuest implements CommandAction {

    RegistrationProcesor registrationProcesor;

    public RegisterGuest(RoomOccupationService roomOccupationService, RoomService roomService) {
        this.registrationProcesor = new RegistrationProcesor(roomOccupationService,  roomService);
    }

    @Override
    public String getName() {
        return TerminalEntries.REGISTER_GUEST;
    }


    @Override
    public boolean exec() {

                try {
                    Optional<org.foo.hotel.model.RoomOccupation> occupation = registrationProcesor.get();
                    System.out.println("Guest successfuly registrated " + occupation.get());
                } catch (AboardException e) {
                }
                
                return true;
    }
}
