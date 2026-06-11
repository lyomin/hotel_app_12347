package org.foo.hotel.actions.reports;

import org.foo.hotel.actions.form.RoomIdSelector;
import org.foo.hotel.core.terminal.CommandAction;
import org.foo.hotel.core.terminal.TerminalEntries;
import org.foo.hotel.model.RoomStats;
import org.foo.hotel.service.RoomOccupationLogService;

import java.util.List;

public class ProfitsReport implements CommandAction {
    private final RoomOccupationLogService occupationHistoryService;

    public ProfitsReport(RoomOccupationLogService occupationHistoryService) {
        this.occupationHistoryService = occupationHistoryService;
    }

    @Override
    public String getName() {
        return TerminalEntries.PROFITS_REPORT;
    }

    @Override
    public String aboardCommand() {
        return TerminalEntries.BACK;
    }

    @Override
    public boolean exec() {

            List<RoomStats> profitReport = occupationHistoryService.getRoomsOccupationStatistics();
            String headerFormat = "| %-10s | %-11s | %-14s |%n";
            String rowFormat    = "| %-10s | %11d | %-14s |%n";
            String divider = "+------------+-------------+----------------+";

            System.out.println(divider);
            System.out.printf(headerFormat, "ROOM ID", "OCCUPANCIES", "PRICE");
            System.out.println(divider);

            for (RoomStats stats : profitReport) {
                System.out.printf(rowFormat,
                        stats.room().id(),
                        stats.occupancyCount(),
                        stats.price()
                );
            }

            return true;

    }
}
