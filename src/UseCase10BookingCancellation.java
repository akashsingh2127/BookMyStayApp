/**
 * Book My Stay Application
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * This program allows safe cancellation of confirmed bookings,
 * restores inventory counts, and maintains consistent system state.
 *
 * @author YourName
 * @version 10.0
 */

import java.util.*;

/* -------- UC10 CUSTOM EXCEPTION -------- */
class UC10InvalidCancellationException extends Exception {
    public UC10InvalidCancellationException(String message) {
        super(message);
    }
}

/* -------- UC10 RESERVATION CLASS -------- */
class UC10Reservation {
    private String guestName;
    private String roomType;
    private String roomId;

    public UC10Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }
}

/* -------- UC10 INVENTORY SERVICE -------- */
class UC10InventoryService {
    private Map<String, Integer> inventory;

    public UC10InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) throws UC10InvalidCancellationException {
        if (!inventory.containsKey(roomType)) {
            throw new UC10InvalidCancellationException("Invalid room type: " + roomType);
        }
        return inventory.get(roomType);
    }

    public void incrementRoom(String roomType) throws UC10InvalidCancellationException {
        if (!inventory.containsKey(roomType)) {
            throw new UC10InvalidCancellationException("Invalid room type: " + roomType);
        }
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void decrementRoom(String roomType) throws UC10InvalidCancellationException {
        int available = getAvailability(roomType);
        if (available <= 0) {
            throw new UC10InvalidCancellationException("No rooms available to decrement for " + roomType);
        }
        inventory.put(roomType, available - 1);
    }
}

/* -------- UC10 BOOKING SERVICE -------- */
class UC10BookingService {
    private UC10InventoryService inventory;
    private Set<String> allocatedRoomIds;
    private Map<String, UC10Reservation> confirmedBookings; // Map roomId -> reservation
    private Stack<String> rollbackStack;

    public UC10BookingService(UC10InventoryService inventory) {
        this.inventory = inventory;
        allocatedRoomIds = new HashSet<>();
        confirmedBookings = new HashMap<>();
        rollbackStack = new Stack<>();
    }

    /* Process a new booking */
    public UC10Reservation book(String guestName, String roomType) {
        try {
            if (inventory.getAvailability(roomType) <= 0) {
                System.out.println("Booking Failed: No rooms available for " + roomType);
                return null;
            }

            String roomId = generateRoomId(roomType);
            inventory.decrementRoom(roomType);
            allocatedRoomIds.add(roomId);

            UC10Reservation reservation = new UC10Reservation(guestName, roomType, roomId);
            confirmedBookings.put(roomId, reservation);

            System.out.println("Booking Confirmed: " + guestName + " | " + roomType + " | RoomID: " + roomId);
            return reservation;

        } catch (UC10InvalidCancellationException e) {
            System.out.println("Booking Error: " + e.getMessage());
            return null;
        }
    }

    /* Cancel an existing booking */
    public void cancelBooking(String roomId) {
        try {
            if (!confirmedBookings.containsKey(roomId)) {
                throw new UC10InvalidCancellationException("No booking found with Room ID: " + roomId);
            }

            UC10Reservation reservation = confirmedBookings.get(roomId);
            rollbackStack.push(roomId); // track rollback
            inventory.incrementRoom(reservation.getRoomType());
            allocatedRoomIds.remove(roomId);
            confirmedBookings.remove(roomId);

            System.out.println("Cancellation Successful: " + reservation.getGuestName() +
                    " | Room: " + reservation.getRoomType() +
                    " | RoomID: " + roomId);

        } catch (UC10InvalidCancellationException e) {
            System.out.println("Cancellation Failed: " + e.getMessage());
        }
    }

    private String generateRoomId(String roomType) {
        String roomId;
        do {
            roomId = roomType.replace(" ", "").substring(0, 2).toUpperCase()
                    + (int) (Math.random() * 1000);
        } while (allocatedRoomIds.contains(roomId));
        return roomId;
    }
}

/* -------- UC10 MAIN APPLICATION -------- */
public class UseCase10BookingCancellation {
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println(" Welcome to Book My Stay App ");
        System.out.println(" Booking Cancellation & Rollback v10.0 ");
        System.out.println("=================================");

        UC10InventoryService inventory = new UC10InventoryService();
        UC10BookingService bookingService = new UC10BookingService(inventory);

        /* Book some rooms */
        UC10Reservation r1 = bookingService.book("Alice", "Single Room");
        UC10Reservation r2 = bookingService.book("Bob", "Double Room");
        UC10Reservation r3 = bookingService.book("Charlie", "Suite Room");

        /* Attempt cancellations */
        if (r1 != null) bookingService.cancelBooking(r1.getRoomId());
        if (r2 != null) bookingService.cancelBooking("INVALID_ID"); // invalid cancellation
        if (r3 != null) bookingService.cancelBooking(r3.getRoomId());

        System.out.println("\nAll bookings processed and cancellations applied.");
        System.out.println("Application terminated.");
    }
}