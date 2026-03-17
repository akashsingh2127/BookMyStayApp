/**
 * Book My Stay Application
 *
 * Use Case 9: Error Handling & Validation
 *
 * This program validates booking inputs, ensures inventory consistency,
 * throws custom exceptions for invalid operations, and handles errors gracefully.
 *
 * @author YourName
 * @version 9.0
 */

import java.util.*;

/* -------- UC9 CUSTOM EXCEPTION -------- */
class UC9InvalidBookingException extends Exception {
    public UC9InvalidBookingException(String message) {
        super(message);
    }
}

/* -------- UC9 RESERVATION CLASS -------- */
class UC9Reservation {
    private String guestName;
    private String roomType;

    public UC9Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

/* -------- UC9 INVENTORY SERVICE -------- */
class UC9InventoryService {
    private HashMap<String, Integer> inventory;

    public UC9InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) throws UC9InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new UC9InvalidBookingException("Invalid room type: " + roomType);
        }
        return inventory.get(roomType);
    }

    public void decrementRoom(String roomType) throws UC9InvalidBookingException {
        int available = getAvailability(roomType);
        if (available <= 0) {
            throw new UC9InvalidBookingException("No rooms available for " + roomType);
        }
        inventory.put(roomType, available - 1);
    }
}

/* -------- UC9 BOOKING SERVICE -------- */
class UC9BookingService {
    private UC9InventoryService inventory;

    public UC9BookingService(UC9InventoryService inventory) {
        this.inventory = inventory;
    }

    public void processBooking(UC9Reservation reservation) {
        try {
            if (reservation.getGuestName() == null || reservation.getGuestName().isEmpty()) {
                throw new UC9InvalidBookingException("Guest name cannot be empty.");
            }
            if (reservation.getRoomType() == null || reservation.getRoomType().isEmpty()) {
                throw new UC9InvalidBookingException("Room type cannot be empty.");
            }

            inventory.decrementRoom(reservation.getRoomType());

            System.out.println("Booking Confirmed for " + reservation.getGuestName() +
                    " | Room: " + reservation.getRoomType());

        } catch (UC9InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }
    }
}

/* -------- UC9 MAIN APPLICATION -------- */
public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println(" Welcome to Book My Stay App ");
        System.out.println(" Error Handling & Validation v9.0 ");
        System.out.println("=================================");

        UC9InventoryService inventory = new UC9InventoryService();
        UC9BookingService bookingService = new UC9BookingService(inventory);

        /* Sample booking requests (some invalid to demonstrate error handling) */
        UC9Reservation r1 = new UC9Reservation("Alice", "Single Room");
        UC9Reservation r2 = new UC9Reservation("Bob", "Penthouse"); // invalid room
        UC9Reservation r3 = new UC9Reservation("", "Double Room");   // empty guest name
        UC9Reservation r4 = new UC9Reservation("Charlie", "Suite Room");
        UC9Reservation r5 = new UC9Reservation("David", "Suite Room"); // may fail if no availability

        /* Process bookings */
        bookingService.processBooking(r1);
        bookingService.processBooking(r2);
        bookingService.processBooking(r3);
        bookingService.processBooking(r4);
        bookingService.processBooking(r5);

        System.out.println("\nAll booking requests processed.");
        System.out.println("Application terminated.");
    }
}