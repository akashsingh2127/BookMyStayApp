/**
 * Book My Stay Application
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * This program demonstrates centralized inventory management
 * using HashMap to store and manage room availability.
 *
 * @author YourName
 * @version 3.1
 */

import java.util.HashMap;
import java.util.Map;

/* -------- INVENTORY MANAGEMENT CLASS -------- */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    /* Constructor initializes room inventory */
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 7);
        inventory.put("Suite Room", 3);
    }

    /* Retrieve availability of a specific room */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /* Update availability of a room */
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    /* Display current inventory */
    public void displayInventory() {

        System.out.println("\n--- Current Room Inventory ---");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}

/* -------- APPLICATION ENTRY POINT -------- */
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Welcome to Book My Stay App ");
        System.out.println(" Hotel Booking System v3.1 ");
        System.out.println("=================================");

        /* Initialize Inventory */
        RoomInventory inventory = new RoomInventory();

        /* Display Inventory */
        inventory.displayInventory();

        /* Example update */
        System.out.println("\nUpdating inventory for Single Room...");

        inventory.updateAvailability("Single Room", 8);

        inventory.displayInventory();

        System.out.println("\nApplication terminated.");
    }
}