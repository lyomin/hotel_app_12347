package org.foo.hotel.service;

import org.foo.hotel.exception.NoRoomFoundException;
import org.foo.hotel.model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RoomOccupationService {

    private final RoomPriceService priceService;
    private final RoomOccupationLogService occupationHistoryService;
    private static List<RoomOccupation> roomOccupationList = new LinkedList<>();

    public RoomOccupationService(RoomPriceService priceService, RoomOccupationLogService occupationHistoryService) {
        this.occupationHistoryService = occupationHistoryService;
        this.priceService = priceService;
    }

    public List<Room> getAvailableRooms(List<Room> rooms) {
        List<Room> occupiedRooms = getOccupiedRooms();
        ArrayList<Room> availableRooms = new ArrayList<>(rooms);

        availableRooms.removeAll(occupiedRooms);
        return availableRooms;
    }

    public List<Room> getOccupiedRooms() {
        return  roomOccupationList.stream().map(RoomOccupation::room).toList();
    }

    public List<Room> getAvailableRooms(RoomType type, List<Room> rooms) {
        List<Room> occupiedRooms = roomOccupationList.stream().filter(o -> o.room().type() == type).map(RoomOccupation::room).toList();
        ArrayList<Room> availableRooms = new ArrayList<>(rooms.stream().filter(r -> r.type() == type).toList());

        availableRooms.removeAll(occupiedRooms);
        return availableRooms;
    }

    public RoomOccupation tryOccupy(RoomType type, Guest guest, List<Room> rooms) {
        List<Room> available = getAvailableRooms(type, rooms);
        return occupy(available.isEmpty() ? null : available.get(0), guest);
    }

    public RoomOccupation tryOccupy(Guest guest, List<Room> rooms) {
        List<Room> available = getAvailableRooms(rooms);
        return occupy(available.isEmpty() ? null : available.get(0), guest);
    }

    private RoomOccupation occupy(Room room, Guest guest) {
        if (room == null) {
            throw new NoRoomFoundException("No available room for occupation");
        }
        RoomOccupation roomOccupation = new RoomOccupation(room, guest, priceService.getPrice(room.type()));
        roomOccupationList.add(roomOccupation);
        occupationHistoryService.addLog(roomOccupation, RoomStatus.OCCUPIED);
        return roomOccupation;
    }

    public void freeRoom(Room room) {
        Iterator<RoomOccupation> each = roomOccupationList.iterator();

        while(each.hasNext()) {
            RoomOccupation o = each.next();
            if (o.room().equals(room)) {
                occupationHistoryService.addLog(o, RoomStatus.FREED);
                each.remove();
            }
        }
    }

    public List<RoomOccupation> getAll() {
        return roomOccupationList;
    }

    public RoomOccupation getRoom(int roomId) {
        return roomOccupationList.stream().filter(o -> o.room().id() == roomId).findFirst().orElseGet(null);
    }
}
