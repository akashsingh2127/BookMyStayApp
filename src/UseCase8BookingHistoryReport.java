/**
 * Book My Stay Application
 *
 * Use Case 8: Booking History & Reporting
 *
 * This program maintains historical booking records and generates summary reports.
 *
 * @author YourName
 * @version 8.0
 */

import java.util.*;

/* -------- UC8 RESERVATION CLASS -------- */
class UC8Reservation {
    private String guestName;
    private String roomType;
    private String reservationId;

    public UC8Reservation(String guestName, String roomType, String reservationId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.reservationId = reservationId;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getReservationId() { return reservationId; }
}

/* -------- UC8 BOOKING HISTORY -------- */
class UC8BookingHistory {
    private List<UC8Reservation> confirmedBookings;

    public UC8BookingHistory() {
        confirmedBookings = new ArrayList<>();
    }

    public void addBooking(UC8Reservation reservation) {
        confirmedBookings.add(reservation);
        System.out.println("Booking added to history for " + reservation.getGuestName());
    }

    public List<UC8Reservation> getAllBookings() {
        return new ArrayList<>(confirmedBookings); // return copy to avoid external modification
    }
}

/* -------- UC8 REPORT SERVICE -------- */
class UC8BookingReportService {
    public void generateReport(List<UC8Reservation> reservations) {
        System.out.println("\n===== Booking History Report =====");
        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (UC8Reservation r : reservations) {
                System.out.println("Reservation ID: " + r.getReservationId());
                System.out.println("Guest: " + r.getGuestName());
                System.out.println("Room Type: " + r.getRoomType());
                System.out.println("----------------------------------");
            }
        }
        System.out.println("===== End of Report =====\n");
    }
}

/* -------- UC8 MAIN APPLICATION -------- */
public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println(" Welcome to Book My Stay App ");
        System.out.println(" Booking History & Reporting v8.0 ");
        System.out.println("=================================");

        /* Initialize booking history and report service */
        UC8BookingHistory history = new UC8BookingHistory();
        UC8BookingReportService reportService = new UC8BookingReportService();

        /* Simulate confirmed reservations */
        UC8Reservation r1 = new UC8Reservation("Alice", "Single Room", "S101");
        UC8Reservation r2 = new UC8Reservation("Bob", "Double Room", "D202");
        UC8Reservation r3 = new UC8Reservation("Charlie", "Suite Room", "SU303");

        /* Add to history */
        history.addBooking(r1);
        history.addBooking(r2);
        history.addBooking(r3);

        /* Admin requests report */
        List<UC8Reservation> allBookings = history.getAllBookings();
        reportService.generateReport(allBookings);

        System.out.println("Application terminated.");
    }
}