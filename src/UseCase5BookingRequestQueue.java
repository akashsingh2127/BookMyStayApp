/**
 * Book My Stay Application
 *
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * @author YourName
 * @version 5.0
 */

import java.util.LinkedList;
import java.util.Queue;

class UC5Reservation {

    private String guestName;
    private String roomType;

    public UC5Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Guest Name : " + guestName);
        System.out.println("Requested Room : " + roomType);
        System.out.println("-----------------------------");
    }
}

class UC5BookingRequestQueue {

    private Queue<UC5Reservation> requestQueue;

    public UC5BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(UC5Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    public void displayRequests() {
        System.out.println("\n--- Booking Request Queue ---");
        for (UC5Reservation r : requestQueue) {
            r.displayReservation();
        }
    }
}

public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App - Booking Request Queue ===");

        UC5BookingRequestQueue bookingQueue = new UC5BookingRequestQueue();

        UC5Reservation r1 = new UC5Reservation("Alice", "Single Room");
        UC5Reservation r2 = new UC5Reservation("Bob", "Double Room");
        UC5Reservation r3 = new UC5Reservation("Charlie", "Suite Room");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        bookingQueue.displayRequests();

        System.out.println("All requests stored in FIFO order.");
        System.out.println("No rooms allocated at this stage.");
        System.out.println("Application terminated.");
    }
}