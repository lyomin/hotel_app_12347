package org.foo.hotel.service;

import org.foo.hotel.model.*;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomOccupationLogService {
    private static final List<OccupationLog> occupationHistory = new LinkedList<>();
    public void addLog(RoomOccupation occupation, RoomStatus action) {
        occupationHistory.add(new OccupationLog(occupation.room(),occupation.guest(),occupation.price(),action, Instant.now()));
    }

    public List<OccupationLog> getOccupationHistory() {
        return occupationHistory;
    }

    public List<OccupationLog> getOcupiedRoomOccupationHistory(int roomId) {
        return occupationHistory.stream()
                .filter(log -> log.action() == RoomStatus.OCCUPIED && log.room().id() == roomId)
                .toList();
    }

    public List<RoomStats> getRoomsOccupationStatistics() {
        return occupationHistory.stream()

                .filter(log -> log.action() == RoomStatus.OCCUPIED)

                .collect(Collectors.groupingBy(OccupationLog::room))
                .entrySet().stream()
                .map(entry -> {
                    Room room = entry.getKey();
                    List<OccupationLog> roomLogs = entry.getValue();

                    long count = roomLogs.size();
                    // Pakeiskite .amount() į realų savo Price objekto metodo pavadinimą (pvz., .value())
                    long profit = roomLogs.stream()
                            .mapToLong(log -> log.price().amount().longValue())
                            .sum();

                    return new RoomStats(room, count, new Price(BigInteger.valueOf(profit), "$"));
                })

                .sorted(Comparator.comparingLong(RoomStats::occupancyCount).reversed())
                .toList();
    }
}
