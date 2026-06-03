package org.foo.hotel.service;

import org.foo.hotel.exception.NoRoomFoundException;
import org.foo.hotel.model.Room;
import org.foo.hotel.model.RoomType;

import java.util.*;
import java.util.stream.Collectors;

public class RoomService {
    private static List<Room> rooms;
    private static int roomId = 0;
    private final RoomOccupationService occupationService;

    public RoomService(RoomOccupationService occupationService) {
        this.occupationService = occupationService;
        rooms = new LinkedList<>();
    }

    public void updateRoomsCount(int count) {
        int currentCount = getTotalCount();
        Map<RoomType, List<Room>> roomsByType = getAllByType();

        int adjust2StandartCount = count/2;
        int adjust2BusinessCount = count - adjust2StandartCount;

        adjustToSize(RoomType.STANDART, roomsByType.getOrDefault(RoomType.STANDART, new LinkedList<>()), adjust2StandartCount);
        adjustToSize(RoomType.BUSINESS, roomsByType.getOrDefault(RoomType.BUSINESS, new LinkedList<>()), adjust2BusinessCount);
        
    }
    
    private void adjustToSize(RoomType type, List<Room> rooms, int newTotal) {
        int oldTotal = rooms.size();
        if (oldTotal < newTotal) {
            for (int i = oldTotal; i < newTotal; i++) {
                addRoom(type);
            }
        } else if (newTotal < oldTotal) {
            List<Room> availableRooms = occupationService.getAvailableRooms(type, rooms);
            if (oldTotal-newTotal > availableRooms.size()) {
                throw new NoRoomFoundException("No free room for removal.");
            }
            int toRemove = oldTotal - newTotal;
            for (int i = 0; i < toRemove; i++) {
                removeRoomById(availableRooms.get(i).id());
            }
        }
    }

    public Room addRoom(RoomType type) {
        Room room = new Room(++roomId,type);
        rooms.add(room);
        return room;
    }

    public void removeRoomById(int id) {
        rooms.removeIf(room -> room.id() == id);
    }

    public Optional<Room> getRoomById(int id) {
        return rooms.stream().filter(r -> r.id() == id).findFirst();
    }

    public List<Room> getAll() {
        return rooms;
    }

    public int getTotalCount() {
        return rooms.size();
    }

    public Map<RoomType, List<Room>> getAllByType() {
        return rooms.stream().collect(Collectors.groupingBy(Room::type));
    }
}
