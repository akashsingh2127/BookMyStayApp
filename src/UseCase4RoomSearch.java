/**
 * Book My Stay Application
 *
 * Use Case 4: Room Search & Availability Check
 *
 * @author YourName
 * @version 4.0
 */

import java.util.*;

abstract class UC4Room {
    private String roomType;
    private int price;

    public UC4Room(String roomType, int price) {
        this.roomType = roomType;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getPrice() {
        return price;
    }

    public abstract String getDetails();
}

class UC4SingleRoom extends UC4Room {
    public UC4SingleRoom() {
        super("Single Room", 100);
    }

    @Override
    public String getDetails() {
        return "Single Room - 1 bed";
    }
}

class UC4DoubleRoom extends UC4Room {
    public UC4DoubleRoom() {
        super("Double Room", 150);
    }

    @Override
    public String getDetails() {
        return "Double Room - 2 beds";
    }
}

class UC4SuiteRoom extends UC4Room {
    public UC4SuiteRoom() {
        super("Suite Room", 300);
    }

    @Override
    public String getDetails() {
        return "Suite Room - 3 beds, living area";
    }
}

class UC4RoomInventory {
    private HashMap<String, Integer> inventory;

    public UC4RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App - Room Search & Availability ===");

        UC4RoomInventory inventory = new UC4RoomInventory();

        List<UC4Room> rooms = List.of(
                new UC4SingleRoom(),
                new UC4DoubleRoom(),
                new UC4SuiteRoom()
        );

        for (UC4Room room : rooms) {
            int avail = inventory.getAvailability(room.getRoomType());
            if (avail > 0) {
                System.out.println(room.getRoomType() + " is available (" + avail + " rooms)");
                System.out.println("Details: " + room.getDetails());
                System.out.println("Price: $" + room.getPrice());
                System.out.println("-----------------------------");
            }
        }

        System.out.println("Application terminated.");
    }
}