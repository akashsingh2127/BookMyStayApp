/**
 * Book My Stay Application
 *
 * Use Case 7: Add-On Service Selection
 *
 * This program allows guests to select optional services for an existing reservation,
 * calculates additional cost, and maps services to the reservation.
 *
 * @author YourName
 * @version 7.0
 */

import java.util.*;

/* -------- UC7 RESERVATION CLASS -------- */
class UC7Reservation {
    private String guestName;
    private String roomType;
    private String reservationId;

    public UC7Reservation(String guestName, String roomType, String reservationId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.reservationId = reservationId;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getReservationId() { return reservationId; }
}

/* -------- UC7 SERVICE CLASS -------- */
class UC7Service {
    private String serviceName;
    private double cost;

    public UC7Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() { return serviceName; }
    public double getCost() { return cost; }
}

/* -------- UC7 ADD-ON SERVICE MANAGER -------- */
class UC7AddOnServiceManager {
    private Map<String, List<UC7Service>> reservationServices;

    public UC7AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    public void addServiceToReservation(String reservationId, UC7Service service) {
        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public void displayServicesForReservation(UC7Reservation reservation) {
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + reservation.getRoomType());
        System.out.println("Reservation ID: " + reservation.getReservationId());

        List<UC7Service> services = reservationServices.getOrDefault(reservation.getReservationId(), new ArrayList<>());
        double totalCost = 0;

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
        } else {
            System.out.println("Add-On Services:");
            for (UC7Service s : services) {
                System.out.println(" - " + s.getServiceName() + " ($" + s.getCost() + ")");
                totalCost += s.getCost();
            }
            System.out.println("Total Add-On Cost: $" + totalCost);
        }
        System.out.println("----------------------------------");
    }
}

/* -------- UC7 MAIN APPLICATION -------- */
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Welcome to Book My Stay App ");
        System.out.println(" Hotel Booking System v7.0 ");
        System.out.println("=================================");

        /* Create reservations */
        UC7Reservation r1 = new UC7Reservation("Alice", "Single Room", "S101");
        UC7Reservation r2 = new UC7Reservation("Bob", "Double Room", "D202");

        /* Initialize add-on manager */
        UC7AddOnServiceManager serviceManager = new UC7AddOnServiceManager();

        /* Define available services */
        UC7Service breakfast = new UC7Service("Breakfast", 15.0);
        UC7Service spa = new UC7Service("Spa Access", 50.0);
        UC7Service airportPickup = new UC7Service("Airport Pickup", 30.0);

        /* Add services to reservations */
        serviceManager.addServiceToReservation(r1.getReservationId(), breakfast);
        serviceManager.addServiceToReservation(r1.getReservationId(), airportPickup);

        serviceManager.addServiceToReservation(r2.getReservationId(), spa);

        /* Display reservation add-on details */
        serviceManager.displayServicesForReservation(r1);
        serviceManager.displayServicesForReservation(r2);

        System.out.println("Application terminated.");
    }
}