package org.foo.hotel.actions;

import org.foo.hotel.actions.reports.ProfitsReport;
import org.foo.hotel.actions.reports.RoomOcupationReport;
import org.foo.hotel.core.ApplicationServiceLoader;
import org.foo.hotel.core.terminal.ActionList;
import org.foo.hotel.core.terminal.Command;
import org.foo.hotel.core.terminal.TerminalEntries;
import org.foo.hotel.service.RoomOccupationLogService;
import org.foo.hotel.service.RoomOccupationService;

public class Reports extends ActionList {

    private static final Command[] settings = {
            new ProfitsReport(ApplicationServiceLoader.get(RoomOccupationLogService.class)),
            new RoomOcupationReport(ApplicationServiceLoader.get(RoomOccupationService.class), ApplicationServiceLoader.get(RoomOccupationLogService.class))
    };

    public Reports() {
        super(TerminalEntries.REPORTS, settings, TerminalEntries.BACK);
    }
}