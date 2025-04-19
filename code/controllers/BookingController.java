package controllers;

import models.Application;
import models.FlatBooking;
import models.enums.ApplicationStatus;
import models.enums.FlatType;

public class BookingController {
    // Books a flat and generates a receipt
    public FlatBooking bookFlat(Application application, FlatType flatType) {
        // Check if the application is eligible for booking
        if (application.getStatus() != enums.ApplicationStatus.SUCCESSFUL) {
            System.out.println("Application is not in a successful state for booking.");
            return null;
        }

        // Create a new FlatBooking and associate it with the application
        FlatBooking booking = new FlatBooking(flatType);
        application.updateStatus(enums.ApplicationStatus.BOOKED);
        System.out.println("Flat booked successfully. Receipt: " + booking.getReceiptNumber());
        return booking;
    }

    // Generates a receipt for the given flat booking
    public void generateReceipt(FlatBooking booking) {
        if (booking == null) {
            System.out.println("No booking found. Cannot generate receipt.");
            return;
        }

        System.out.println("Generating receipt...");
        System.out.println("Receipt Number: " + booking.getReceiptNumber());
        System.out.println("Flat Type: " + booking.getFlatType());
        System.out.println("Booking Date: " + booking.getBookingDate());
        System.out.println("Thank you for booking with HDB!");
    }
}
