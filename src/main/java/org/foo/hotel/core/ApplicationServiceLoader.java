package org.foo.hotel.core;

import org.foo.hotel.core.exceptions.ServiceNotFoundException;
import org.foo.hotel.core.terminal.InputAction;
import org.foo.hotel.service.RoomOccupationLogService;
import org.foo.hotel.service.RoomOccupationService;
import org.foo.hotel.service.RoomPriceService;
import org.foo.hotel.service.RoomService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationServiceLoader {
    private static Map<Class, Object> services = new HashMap<>();

    static {
        RoomOccupationLogService occupationHistoryService = new RoomOccupationLogService();
        RoomPriceService priceService = new RoomPriceService();
        RoomOccupationService occupationService = new RoomOccupationService(priceService, occupationHistoryService);

        register(priceService);
        register(occupationService);
        register(new RoomService(occupationService));
        register(occupationHistoryService);

        get(RoomPriceService.class).updatePrice(Integer.valueOf(2000));
        get(RoomService.class).updateRoomsCount(Integer.valueOf(6));
    }

    public static void register(Object service) {
        if (service == null) {
            throw new NullPointerException();
        }
        services.put(service.getClass(), service);
    }

    public static <T> T get(Class<T> servoiceClass) {
        if (!services.containsKey(servoiceClass)) {
            throw new ServiceNotFoundException("Service of class " + servoiceClass.getSimpleName() + " not registered.");
        }
        return (T) services.get(servoiceClass);
    }
}
