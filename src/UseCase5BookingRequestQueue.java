/**
 * Book My Stay Application
 *
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * This program demonstrates handling booking requests using
 * a Queue to preserve request arrival order.
 *
 * @author YourName
 * @version 5.0
 */

import java.util.LinkedList;
import java.util.Queue;

/* -------- RESERVATION CLASS -------- */

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
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

/* -------- BOOKING REQUEST QUEUE -------- */

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    /* Add reservation request */
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    /* Display all pending requests */
    public void displayRequests() {

        System.out.println("\n--- Booking Request Queue ---");

        for (Reservation r : requestQueue) {
            r.displayReservation();
        }
    }
}

/* -------- MAIN APPLICATION -------- */

public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Welcome to Book My Stay App ");
        System.out.println(" Hotel Booking System v5.0 ");
        System.out.println("=================================");

        /* Initialize Booking Queue */
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        /* Guest booking requests */
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        /* Add requests to queue */
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        /* Display queue */
        bookingQueue.displayRequests();

        System.out.println("\nAll requests stored in FIFO order.");
        System.out.println("No rooms allocated at this stage.");

        System.out.println("\nApplication terminated.");
    }
}