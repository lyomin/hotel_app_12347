package org.foo.hotel.actions.reports;

import org.foo.hotel.actions.form.RoomIdSelector;
import org.foo.hotel.core.terminal.CommandAction;
import org.foo.hotel.core.terminal.TerminalEntries;
import org.foo.hotel.model.OccupationLog;
import org.foo.hotel.model.Room;
import org.foo.hotel.service.RoomOccupationLogService;
import org.foo.hotel.service.RoomOccupationService;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class RoomOcupationReport implements CommandAction {
    private final RoomOccupationService occupationService;
    private final RoomOccupationLogService occupationHistoryService;
    private final RoomIdSelector roomForm = new RoomIdSelector();

    public RoomOcupationReport(RoomOccupationService occupationService, RoomOccupationLogService occupationHistoryService) {
        this.occupationService = occupationService;
        this.occupationHistoryService = occupationHistoryService;
    }

    @Override
    public String getName() {
        return TerminalEntries.ROOM_REPORT;
    }

    @Override
    public boolean exec() {
            List<Room> rooms = occupationHistoryService.getOccupationHistory().stream().map(OccupationLog::room)
                    .distinct().toList();

            Optional<Room> room = roomForm.get(rooms);

            if (room.isEmpty()) {
                System.out.println(TerminalEntries.ROOM_NOT_FOUND);
                return false;
            }

            String status = occupationService.getRoom(room.get().id()) == null ? "FREE" : "OCUPIED";
            System.out.println("Room " + room.get() + " Status " + status);

            List<OccupationLog> roomHistory = occupationHistoryService.getOcupiedRoomOccupationHistory(room.get().id()).stream()
                    .sorted(Comparator.comparing(OccupationLog::timestamp)).toList();

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    .withZone(ZoneId.systemDefault());

            String formatter    = "| %-45s | %-20s |%n";
            String divider = "+-----------------------------------------------+----------------------+";


            System.out.println(divider);
            System.out.printf(formatter, "GUEST (Name & Surname)", "TIMESTAMP");
            System.out.println(divider);

            for (OccupationLog log : roomHistory) {
                // Sujungiame vardą ir pavardę iš Guest objekto
                String fullGuestName = log.guest().name() + " " + log.guest().surname();
                // Formatuojame Instant laiko žymą
                String formattedTime = dateFormatter.format(log.timestamp());

                System.out.printf(formatter, fullGuestName, formattedTime);
            }


            return true;

    }
}
