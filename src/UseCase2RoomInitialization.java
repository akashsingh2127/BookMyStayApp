/**
 * Book My Stay Application
 *
 * Use Case 2: Basic Room Types & Static Availability
 *
 * This program demonstrates object modeling using abstraction,
 * inheritance, encapsulation, and polymorphism.
 *
 * Different room types are created and their availability is
 * displayed using simple variables.
 *
 * @author YourName
 * @version 2.1
 */

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
}


/* -------- SINGLE ROOM CLASS -------- */
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 100.0);
    }
}


/* -------- DOUBLE ROOM CLASS -------- */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 180.0);
    }
}


/* -------- SUITE ROOM CLASS -------- */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 500, 350.0);
    }
}


/* -------- MAIN APPLICATION CLASS -------- */
public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Welcome to Book My Stay App ");
        System.out.println(" Hotel Booking System v2.1 ");
        System.out.println("=================================");

        /* Room Objects (Polymorphism) */
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        /* Static Availability Variables */
        int singleRoomAvailability = 10;
        int doubleRoomAvailability = 7;
        int suiteRoomAvailability = 3;

        System.out.println("\n--- Room Details & Availability ---\n");

        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms : " + singleRoomAvailability);
        System.out.println("-----------------------------------");

        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms : " + doubleRoomAvailability);
        System.out.println("-----------------------------------");

        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms : " + suiteRoomAvailability);
        System.out.println("-----------------------------------");

        System.out.println("\nApplication terminated.");
    }
}