package org.foo.hotel.model;

import java.time.Instant;

public record OccupationLog(Room room, Guest guest, Price price, RoomStatus action, Instant timestamp) {
}
