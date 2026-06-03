package org.foo.hotel.actions.form;

import org.foo.hotel.core.terminal.Prompt;
import org.foo.hotel.core.terminal.TerminalEntries;
import org.foo.hotel.exception.NoRoomFoundException;
import org.foo.hotel.model.Room;

import java.util.List;
import java.util.Optional;

public class RoomIdSelector {

    public Optional<Room> get(List<Room> rooms, String abordSelection) {

        if (rooms == null || rooms.isEmpty()) {
            throw new NoRoomFoundException(TerminalEntries.NO_ROOMS_EMPTY_LIST);
        }

        StringBuffer list = new StringBuffer();
        for (Room room : rooms) {
            list.append("\n");
            list.append(room);
        }
        Integer id = Prompt.readPositiveInt("Select room id " + list, TerminalEntries.BACK);

        return rooms.stream().filter(room -> id.equals(room.id())).findFirst();
    }
}
