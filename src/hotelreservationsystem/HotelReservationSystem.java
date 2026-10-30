package hotelreservationsystem;


import java.util.ArrayList;
import java.util.Scanner;

class Room {
    int roomNumber;
    String category;
    double price;
    boolean available;

    Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.available = true;
    }
}

class Booking {
    int bookingId;
    String customerName;
    Room room;
    int days;
    double totalAmount;

    Booking(int bookingId, String customerName,
            Room room, int days) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.room = room;
        this.days = days;
        this.totalAmount = room.price * days;
    }
}

public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static int nextBookingId = 1001;

    static Scanner sc = new Scanner(System.in);

    static void initializeRooms() {
        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1500));
        rooms.add(new Room(103, "Standard", 1500));

        rooms.add(new Room(201, "Deluxe", 2500));
        rooms.add(new Room(202, "Deluxe", 2500));

        rooms.add(new Room(301, "Suite", 5000));
        rooms.add(new Room(302, "Suite", 5000));
    }

    static void displayRooms() {
        System.out.println("\n========== ROOM DETAILS ==========");

        for (Room room : rooms) {
            System.out.println(
                "Room: " + room.roomNumber
                + " | Category: " + room.category
                + " | Price per day: Rs." + room.price
                + " | Status: "
                + (room.available ? "Available" : "Booked")
            );
        }
    }

    static void searchRooms() {
        System.out.println("\nAvailable Room Categories:");
        System.out.println("1. Standard");
        System.out.println("2. Deluxe");
        System.out.println("3. Suite");

        System.out.print("Choose category: ");
        String choice = sc.nextLine();

        String category;

        switch (choice) {
            case "1":
                category = "Standard";
                break;
            case "2":
                category = "Deluxe";
                break;
            case "3":
                category = "Suite";
                break;
            default:
                System.out.println("Invalid category!");
                return;
        }

        boolean found = false;

        System.out.println("\nAvailable " + category + " Rooms:");

        for (Room room : rooms) {
            if (room.category.equalsIgnoreCase(category)
                    && room.available) {
                System.out.println(
                    "Room " + room.roomNumber
                    + " - Rs." + room.price + " per day"
                );
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms available in this category.");
        }
    }

    static void bookRoom() {
        System.out.print("Enter customer name: ");
        String name = sc.nextLine();

        System.out.print("Enter room number: ");
        int roomNumber = Integer.parseInt(sc.nextLine());

        Room selectedRoom = null;

        for (Room room : rooms) {
            if (room.roomNumber == roomNumber) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room not found!");
            return;
        }

        if (!selectedRoom.available) {
            System.out.println("Sorry, this room is already booked.");
            return;
        }

        System.out.print("Enter number of days: ");
        int days = Integer.parseInt(sc.nextLine());

        if (days <= 0) {
            System.out.println("Invalid number of days!");
            return;
        }

        double amount = selectedRoom.price * days;

        System.out.println("\n========== PAYMENT ==========");
        System.out.println("Customer: " + name);
        System.out.println("Room: " + roomNumber);
        System.out.println("Days: " + days);
        System.out.println("Total Amount: Rs." + amount);

        System.out.print("Confirm payment? (yes/no): ");
        String payment = sc.nextLine();

        if (!payment.equalsIgnoreCase("yes")) {
            System.out.println("Payment cancelled. Booking not created.");
            return;
        }

        Booking booking = new Booking(
            nextBookingId++, name, selectedRoom, days
        );

        bookings.add(booking);
        selectedRoom.available = false;

        System.out.println("\nPayment successful!");
        System.out.println("Room booked successfully!");
        System.out.println("Booking ID: " + booking.bookingId);
    }

    static void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        System.out.println("\n========== BOOKING DETAILS ==========");

        for (Booking b : bookings) {
            System.out.println("Booking ID: " + b.bookingId);
            System.out.println("Customer: " + b.customerName);
            System.out.println("Room Number: " + b.room.roomNumber);
            System.out.println("Category: " + b.room.category);
            System.out.println("Days: " + b.days);
            System.out.println("Total Amount: Rs." + b.totalAmount);
            System.out.println("-------------------------------------");
        }
    }

    static void cancelBooking() {
        System.out.print("Enter Booking ID to cancel: ");
        int id = Integer.parseInt(sc.nextLine());

        Booking selectedBooking = null;

        for (Booking b : bookings) {
            if (b.bookingId == id) {
                selectedBooking = b;
                break;
            }
        }

        if (selectedBooking == null) {
            System.out.println("Booking ID not found!");
            return;
        }

        selectedBooking.room.available = true;
        bookings.remove(selectedBooking);

        System.out.println("Booking cancelled successfully!");
        System.out.println("Room is now available.");
        System.out.println("Note: This demo does not process real refunds.");
    }

    public static void main(String[] args) {
        initializeRooms();

        while (true) {
            System.out.println("\n================================");
            System.out.println("     HOTEL RESERVATION SYSTEM   ");
            System.out.println("================================");
            System.out.println("1. Display All Rooms");
            System.out.println("2. Search Available Rooms");
            System.out.println("3. Book a Room");
            System.out.println("4. View Bookings");
            System.out.println("5. Cancel Booking");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");

            String choice = sc.nextLine();

            try {
                switch (choice) {
                    case "1":
                        displayRooms();
                        break;

                    case "2":
                        searchRooms();
                        break;

                    case "3":
                        bookRoom();
                        break;

                    case "4":
                        viewBookings();
                        break;

                    case "5":
                        cancelBooking();
                        break;

                    case "6":
                        System.out.println("Thank you for using our system!");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
