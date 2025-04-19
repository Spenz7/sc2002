package models;

import models.enums.FlatType;

import java.util.Date;
import java.util.UUID;

public class FlatBooking {
    // Attributes
    private String receiptNumber; // Unique identifier for the receipt
    private Date bookingDate;     // Date when the flat was booked
    private FlatType flatType;    // Type of flat booked

    // Constructor
    public FlatBooking(FlatType flatType) {
        this.receiptNumber = generateReceiptNumber(); // Auto-generated unique receipt number
        this.bookingDate = new Date(); // Sets the booking date to the current date
        this.flatType = flatType;
    }

    // Auto-generates a unique receipt number using UUID
    private String generateReceiptNumber() {
        return UUID.randomUUID().toString();
    }

    // Getter for receipt number
    public String getReceiptNumber() {
        return receiptNumber;
    }

    // Getter for booking date
    public Date getBookingDate() {
        return bookingDate;
    }

    // Getter for flat type
    public FlatType getFlatType() {
        return flatType;
    }

    // Override toString for displaying flat booking details
    @Override
    public String toString() {
        return "FlatBooking{" +
                "receiptNumber='" + receiptNumber + '\'' +
                ", bookingDate=" + bookingDate +
                ", flatType=" + flatType +
                '}';
    }
}
