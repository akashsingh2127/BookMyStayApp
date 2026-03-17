/**
 * Book My Stay Application
 *
 * Use Case 12: Data Persistence & System Recovery
 *
 * Demonstrates saving and restoring system state using Serialization.
 *
 * @author YourName
 * @version 12.0
 */

import java.io.*;
import java.util.*;

/* -------- UC12 RESERVATION (SERIALIZABLE) -------- */
class UC12Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    private String guestName;
    private String roomType;

    public UC12Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Room: " + roomType;
    }
}

/* -------- UC12 SYSTEM STATE (DATA WRAPPER) -------- */
class UC12SystemState implements Serializable {
    private static final long serialVersionUID = 1L;
    public Map<String, Integer> inventory;
    public List<UC12Reservation> bookings;

    public UC12SystemState(Map<String, Integer> inventory, List<UC12Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

/* -------- UC12 PERSISTENCE SERVICE -------- */
class UC12PersistenceService {
    private static final String FILE_NAME = "hotel_state.ser";

    public void saveState(Map<String, Integer> inventory, List<UC12Reservation> bookings) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            UC12SystemState state = new UC12SystemState(inventory, bookings);
            oos.writeObject(state);
            System.out.println("[System] State persisted successfully to " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("[Error] Failed to save state: " + e.getMessage());
        }
    }

    public UC12SystemState loadState() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("[System] No persistence file found. Starting with fresh state.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (UC12SystemState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[Error] Recovery failed: " + e.getMessage());
            return null;
        }
    }
}

/* -------- UC12 MAIN APPLICATION -------- */
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println(" Welcome to Book My Stay App ");
        System.out.println(" Data Persistence & Recovery v12.0 ");
        System.out.println("=================================");

        UC12PersistenceService persistence = new UC12PersistenceService();
        Map<String, Integer> inventory;
        List<UC12Reservation> bookings;

        // --- STEP 1: SYSTEM RECOVERY ---
        UC12SystemState savedState = persistence.loadState();

        if (savedState != null) {
            inventory = savedState.inventory;
            bookings = savedState.bookings;
            System.out.println("Restored Inventory: " + inventory);
            System.out.println("Restored Bookings count: " + bookings.size());
        } else {
            inventory = new HashMap<>();
            inventory.put("Single Room", 5);
            inventory.put("Double Room", 3);
            bookings = new ArrayList<>();
            System.out.println("Initialized default inventory.");
        }

        // --- STEP 2: SIMULATE NEW TRANSACTION ---
        System.out.println("\nProcessing new booking for 'Elena'...");
        String room = "Single Room";
        if (inventory.get(room) > 0) {
            inventory.put(room, inventory.get(room) - 1);
            bookings.add(new UC12Reservation("Elena", room));
            System.out.println("Booking successful.");
        }

        // --- STEP 3: PERSISTENCE BEFORE SHUTDOWN ---
        System.out.println("\nPreparing for system shutdown...");
        persistence.saveState(inventory, bookings);
        System.out.println("Application terminated safely.");
    }
}