package org.foo.hotel.actions;

import kotlin.Pair;
import org.foo.hotel.core.terminal.CommandAction;
import org.foo.hotel.core.terminal.TerminalEntries;
import org.foo.hotel.model.Room;
import org.foo.hotel.model.RoomOccupation;
import org.foo.hotel.service.RoomOccupationService;
import org.foo.hotel.service.RoomService;

import java.util.ArrayList;
import java.util.List;

public class RoomOcuppation implements CommandAction {

    private final RoomOccupationService occupationService;
    private final RoomService roomService;

    public RoomOcuppation(RoomOccupationService occupationService, RoomService roomService) {
        this.occupationService = occupationService;
        this.roomService = roomService;
    }

    @Override
    public String getName() {
        return TerminalEntries.ROOM_OCCUPATION;
    }

    @Override
    public String aboardCommand() {
        return TerminalEntries.BACK;
    }

    @Override
    public boolean exec() {

                List<Pair<Room, RoomOccupation>> occupations = listOccupations();
                // Define formats for header and rows
                String headerFormat = "| %-10s | %-15s | %-20s | %-20s | %-12s |%n";
                String rowFormat    = "| %-10s | %-15s | %-20s | %-20s | %-20s € |%n";
                String divider = "+------------+-----------------+----------------------+----------------------+--------------+";

                // Print Table Header
                System.out.println(divider);
                System.out.printf(headerFormat, "Room ID", "Room Type", "First Name", "Surname", "Price");
                System.out.println(divider);

                // Print Table Rows (Example Data)
                for (Pair<Room, RoomOccupation> occupation  : occupations) {
                    if (occupation.getSecond() == null)
                        System.out.printf(rowFormat, occupation.getFirst().id(), occupation.getFirst().type(), "--||--", "--||--", 0);
                    else
                        System.out.printf(rowFormat, occupation.getFirst().id(), occupation.getFirst().type(), occupation.getSecond().guest().name(), occupation.getSecond().guest().surname(), occupation.getSecond().price());
                }
                return true;

    }

    private List<Pair<Room, RoomOccupation>> listOccupations() {
        List<Room> rooms = roomService.getAll();
        List<RoomOccupation> occupiedRooms = occupationService.getAll();


        List<Pair<Room, RoomOccupation>> list = new ArrayList<>(rooms.size());
        for (Room room : rooms) {
            list.add(new Pair<>(room, getOccupiedRoom(room.id(), occupiedRooms)));
        }

        return list;
    }

    private RoomOccupation getOccupiedRoom(int roomId, List<RoomOccupation> occupiedRooms) {
        for (RoomOccupation occupiedRoom : occupiedRooms) {
            if (occupiedRoom.room().id() == roomId) {
                return occupiedRoom;
            }
        }
        return null;
    }
}
