package Console;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {

    public static Scanner input;
    public static HotelResource hotelResource = HotelResource.getInstance();
    public static AdminMenu adminMenu = new AdminMenu();

    public MainMenu() {
    }

    public static void display_Menu() {
        System.out.println("Welcome to the Hotel Reservation Application");
        System.out.println("---------------------------------------------------");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("---------------------------------------------------");
        System.out.println("Please select a number for the menu option");
    }

    public static void findAndReserveARoom()  {
        Scanner scanner = new Scanner(System.in);
        Date checkInDate = new Date();
        Date checkOutDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Please enter the day you want to check-in. format: dd/MM/yyyy");
        String checkIn = scanner.next();
        try {
            checkInDate = dateFormat.parse(checkIn);
        } catch (Exception e) {
            e.printStackTrace();
        }


        while(checkInDate.before(new Date())) {
            System.out.println("PLease enter a day after today");
            checkIn = scanner.next();
            try {
                checkInDate = dateFormat.parse(checkIn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Please enter the day you want to check-out. format: dd/MM/yyyy");
        String checkOut = scanner.next();
        try {
            checkOutDate = dateFormat.parse(checkOut);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while(checkOutDate.before(checkInDate)) {
            System.out.println("Please enter check-out date after check-in date");
            checkOut = scanner.next();
            try {
                checkOutDate = dateFormat.parse(checkOut);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Collection<IRoom> rooms = hotelResource.findARoom(checkInDate, checkOutDate);
        // if there are no available rooms, we search for rooms after 10 days
        if(rooms.size() == 0) {
            System.out.println("There are no available rooms between " + checkIn + " and " + checkOut);
            System.out.println("Available rooms after ten days");
            // checkInDate += 10 days , checkOutDate += 10 days;
            Calendar c = Calendar.getInstance();
            c.setTime(checkInDate);
            c.add(Calendar.DAY_OF_MONTH, 10);
            checkInDate = c.getTime();
            c.setTime(checkOutDate);
            c.add(Calendar.DATE, 10);
            checkInDate = c.getTime();
            rooms = hotelResource.findARoom(checkInDate, checkOutDate);
        }
        System.out.println("Available rooms between " + checkIn + " and " + checkOut);
        System.out.println(rooms);

        if(rooms.size() > 0) {
            System.out.println("Would you like to book a room y/n");
            String bookRoomOrNot = scanner.next();
            // make sure the answer to be y or n
            while(!bookRoomOrNot.equals("y") && !bookRoomOrNot.equals("n")) {
                System.out.println("Please enter y (Yes) or n (No)");
                bookRoomOrNot = scanner.next();
            }
            if(bookRoomOrNot.equals("y")) {
                System.out.println("Do you have an account with us y/n");
                String haveAnAccountOrNot = scanner.next();
                while(!bookRoomOrNot.equals("y") && !bookRoomOrNot.equals("n")) {
                    System.out.println("Please enter y (Yes) or n (No)");
                    bookRoomOrNot = scanner.next();
                }

                if(haveAnAccountOrNot.equals("y")) {
                    System.out.println("Please enter the customer email");
                    String email = scanner.next();
                    System.out.println("Please enter the room number you want to book");
                    String roomNumber = scanner.next();
                    Reservation reservation = hotelResource.bookARoom(email, roomNumber, checkInDate, checkOutDate);
                    System.out.println(reservation);
                }

            }

        }

    }

    public static void seeMyReservations() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your email");
        String email = scanner.next();
        System.out.println("The Customer:" + email);
        System.out.println(hotelResource.getCustomerReservations(email));
    }

    public static void createAnAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the email format: name@domain.com");
        String email = scanner.next();
        System.out.println("Please enter the first name");
        String firstname = scanner.next();
        System.out.println("Please enter the last name");
        String lastname = scanner.next();
        hotelResource.createACustomer(email, firstname, lastname);
        System.out.println("You have created an account");
        System.out.println(hotelResource.getCustomer(email));
    }

    public static void startMain() {
        input = new Scanner(System.in);
        display_Menu();
        int selection = input.nextInt();

        while(true) {
            switch (selection) {
                case 1:
                    findAndReserveARoom();
                    break;
                case 2:
                    seeMyReservations();
                    break;
                case 3:
                    createAnAccount();
                    break;
                case 4:
                    adminMenu.startAdmin();
                    break;
                case 5:
                    input.close();
                    System.exit(0);
                default:
                    System.out.println("Incorrect Input");
                    break;
            }
            display_Menu();
            selection = input.nextInt();
        }
    }

}


