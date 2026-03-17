/**
 * Book My Stay Application
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * @author YourName
 * @version 6.0
 */

import java.util.*;

class UC6Reservation {

    private String guestName;
    private String roomType;

    public UC6Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class UC6BookingRequestQueue {

    private Queue<UC6Reservation> queue;

    public UC6BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(UC6Reservation reservation) {
        queue.add(reservation);
    }

    public UC6Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}

class UC6RoomInventoryService {

    private HashMap<String, Integer> inventory;

    public UC6RoomInventoryService() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

class UC6BookingService {

    private UC6RoomInventoryService inventory;

    private Set<String> allocatedRoomIds;
    private HashMap<String, Set<String>> roomAllocations;

    public UC6BookingService(UC6RoomInventoryService inventory) {
        this.inventory = inventory;
        allocatedRoomIds = new HashSet<>();
        roomAllocations = new HashMap<>();
    }

    public void processReservation(UC6Reservation reservation) {
        String roomType = reservation.getRoomType();

        if (inventory.getAvailability(roomType) > 0) {
            String roomId = generateRoomId(roomType);
            allocatedRoomIds.add(roomId);

            roomAllocations.computeIfAbsent(roomType, k -> new HashSet<>()).add(roomId);

            inventory.decrementRoom(roomType);

            System.out.println("Reservation Confirmed for " + reservation.getGuestName());
            System.out.println("Room Type : " + roomType);
            System.out.println("Assigned Room ID : " + roomId);
            System.out.println("----------------------------------");
        } else {
            System.out.println("Reservation Failed for " + reservation.getGuestName());
            System.out.println("No rooms available for " + roomType);
            System.out.println("----------------------------------");
        }
    }

    private String generateRoomId(String roomType) {
        String roomId;
        do {
            roomId = roomType.replace(" ", "").substring(0, 2).toUpperCase()
                    + (int)(Math.random() * 1000);
        } while (allocatedRoomIds.contains(roomId));
        return roomId;
    }
}

public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App - Reservation & Room Allocation ===");

        UC6RoomInventoryService inventory = new UC6RoomInventoryService();
        UC6BookingRequestQueue queue = new UC6BookingRequestQueue();
        UC6BookingService bookingService = new UC6BookingService(inventory);

        queue.addRequest(new UC6Reservation("Alice", "Single Room"));
        queue.addRequest(new UC6Reservation("Bob", "Double Room"));
        queue.addRequest(new UC6Reservation("Charlie", "Suite Room"));
        queue.addRequest(new UC6Reservation("David", "Suite Room")); // may fail

        while (queue.hasRequests()) {
            UC6Reservation reservation = queue.getNextRequest();
            bookingService.processReservation(reservation);
        }

        System.out.println("All booking requests processed.");
        System.out.println("Application terminated.");
    }
}