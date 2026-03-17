/**
 * Book My Stay Application
 *
 * Use Case 4: Room Search & Availability Check
 *
 * This program demonstrates read-only access to centralized
 * room inventory. Guests can search and view available rooms
 * without modifying the system state.
 *
 * @author YourName
 * @version 4.0
 */

import java.util.HashMap;
import java.util.Map;

/* -------- ABSTRACT ROOM CLASS -------- */
abstract class Room {

    protected String roomType;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq ft");
        System.out.println("Price     : $" + price);
    }

    public String getRoomType() {
        return roomType;
    }
}

/* -------- ROOM TYPES -------- */

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 200, 100.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 350, 180.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 500, 350.0);
    }
}

/* -------- INVENTORY CLASS -------- */

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 7);
        inventory.put("Suite Room", 0); // unavailable example
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }
}

/* -------- SEARCH SERVICE -------- */

class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory, Room[] rooms) {

        System.out.println("\n--- Available Rooms ---");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            if (available > 0) {
                room.displayRoomDetails();
                System.out.println("Available Rooms : " + available);
                System.out.println("-----------------------------------");
            }
        }
    }
}

/* -------- MAIN APPLICATION -------- */

public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Welcome to Book My Stay App ");
        System.out.println(" Hotel Booking System v4.0 ");
        System.out.println("=================================");

        /* Initialize Inventory */
        RoomInventory inventory = new RoomInventory();

        /* Create Room Objects */
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        /* Search Service */
        RoomSearchService searchService = new RoomSearchService();

        /* Perform Room Search */
        searchService.searchAvailableRooms(inventory, rooms);

        System.out.println("\nApplication terminated.");
    }
}