package org.foo.hotel;

import org.foo.hotel.exception.NoRoomFoundException;
import org.foo.hotel.model.*;
import org.foo.hotel.service.RoomOccupationLogService;
import org.foo.hotel.service.RoomOccupationService;
import org.foo.hotel.service.RoomPriceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckInCheckOutTest {

    @Mock
    private RoomPriceService priceService;

    @Mock
    private RoomOccupationLogService occupationHistoryService;

    @Spy
    @InjectMocks
    private RoomOccupationService occupationService;

    @Test
    void shouldSuccessfullyCheckInAndOutGuestWhenRoomIsAvailable() {
        Room one = new Room(1, RoomType.BUSINESS);
        Price price = new Price("2000", "$");
        Guest guest = new Guest("TEST", "TEST");

        when(priceService.getPrice(RoomType.BUSINESS)).thenReturn(price);

        RoomOccupation roomOccupation =  occupationService.tryOccupy(guest, List.of(one));

        assertEquals(roomOccupation, new RoomOccupation(one, guest, price));

        RoomOccupation target = new RoomOccupation(one, guest, price);

        verify(occupationHistoryService).addLog(target, RoomStatus.OCCUPIED);

        occupationService.freeRoom(one);

        verify(occupationHistoryService).addLog(target, RoomStatus.FREED);
    }

    @Test
    void shouldUnsuccessfullyCheckInGuestWhenRoomIsOccupied() {
        Room one = new Room(1, RoomType.BUSINESS);
        Price price = new Price("2000", "$");
        Guest guest = new Guest("TEST", "TEST");

        when(occupationService.getOccupiedRooms()).thenReturn(List.of(one));

        assertThrows(NoRoomFoundException.class, () -> {occupationService.tryOccupy(guest, List.of(one));});

    }

    /*@Test
    @DisplayName("Įregistravimas nepavyksta, jei kambarys jau užimtas")
    void shouldFailCheckInWhenRoomIsAlreadyOccupied() {
        // GIVEN
        String roomId = "204B";
        Room occupiedRoom = new Room(roomId, "Suite", true); // Kambarys JAU užimtas
        Guest guest = new Guest("Anna", "Smith");

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(occupiedRoom));

        // WHEN
        boolean isCheckedIn = hotelService.checkIn(roomId, guest);

        // THEN
        assertFalse(isCheckedIn, "Įregistravimas turėtų nepavykti");

        // Kadangi įregistravimas nepavyko, save() metodas neturėjo būti iškviestas
        verify(roomRepository, never()).save(any(Room.class));
    }*/
}
