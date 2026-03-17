/**
 * Book My Stay Application
 *
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 *
 * Demonstrates safe multi-threaded booking requests with shared inventory.
 *
 * @author YourName
 * @version 11.0
 */

import java.util.*;
import java.util.concurrent.*;


/* -------- UC11 RESERVATION CLASS -------- */
class UC11Reservation {
    private String guestName;
    private String roomType;

    public UC11Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}


/* -------- UC11 INVENTORY SERVICE -------- */
class UC11InventoryService {
    private Map<String, Integer> inventory;

    public UC11InventoryService() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    /* Synchronized methods ensure thread safety */
    public synchronized int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public synchronized boolean allocateRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public synchronized void releaseRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }
}


/* -------- UC11 BOOKING SERVICE -------- */
class UC11BookingService {

    private UC11InventoryService inventory;
    private Set<String> allocatedRoomIds;

    public UC11BookingService(UC11InventoryService inventory) {
        this.inventory = inventory;
        allocatedRoomIds = Collections.synchronizedSet(new HashSet<>());
    }

    public void processBooking(UC11Reservation reservation) {
        synchronized (this) { // critical section for allocation
            String roomType = reservation.getRoomType();
            if (inventory.allocateRoom(roomType)) {
                String roomId = generateRoomId(roomType);
                allocatedRoomIds.add(roomId);
                System.out.println("Booking Confirmed: " + reservation.getGuestName()
                        + " | Room: " + roomType
                        + " | RoomID: " + roomId);
            } else {
                System.out.println("Booking Failed (No Availability): " + reservation.getGuestName()
                        + " | Room: " + roomType);
            }
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


/* -------- UC11 MAIN APPLICATION -------- */
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=================================");
        System.out.println(" Welcome to Book My Stay App ");
        System.out.println(" Concurrent Booking Simulation v11.0 ");
        System.out.println("=================================");

        UC11InventoryService inventory = new UC11InventoryService();
        UC11BookingService bookingService = new UC11BookingService(inventory);

        // Shared queue of booking requests
        List<UC11Reservation> reservations = Arrays.asList(
                new UC11Reservation("Alice", "Single Room"),
                new UC11Reservation("Bob", "Double Room"),
                new UC11Reservation("Charlie", "Suite Room"),
                new UC11Reservation("David", "Single Room"),
                new UC11Reservation("Eva", "Double Room"),
                new UC11Reservation("Frank", "Suite Room")
        );

        // ExecutorService to simulate concurrent guests
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (UC11Reservation res : reservations) {
            executor.submit(() -> bookingService.processBooking(res));
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("\nAll booking requests processed concurrently.");
        System.out.println("Application terminated.");
    }
}