package Console;

import api.AdminResource;
import model.IRoom;
import model.Room;
import model.RoomType;
import service.CustomerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    public static Scanner input;
    public static AdminResource adminResource = AdminResource.getInstance();
    public static MainMenu mainMenu = new MainMenu();

    public AdminMenu() {
    }

    public static void display_Menu() {
        System.out.println("Admin Menu");
        System.out.println("---------------------------------------------------");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
        System.out.println("---------------------------------------------------");
        System.out.println("Please select a number for the menu option");
    }

    public static void seeAllCustomers() {
        System.out.println(adminResource.getAllCustomer());
    }

    public static void seeAllRooms() {
        System.out.println(adminResource.getAllRooms());
    }

    public static void seeAllReservations() {
        adminResource.displayAllReservations();
    }

    public static void addARoom() {
        List<IRoom> listOfRoom = new ArrayList<>();
        RoomType roomType = null;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the room number");
        String roomId = scanner.next();
        System.out.println("Please enter the roomType 1 for SINGLE, 2 for DOUBLE");
        int roomTp = scanner.nextInt();
        // make sure the answer to be 1 or 2
        while(roomTp != 1 && roomTp != 2) {
            System.out.println("Please enter 1 (SINGLE) or 2 (DOUBLE)");
            roomTp = scanner.nextInt();
        }
        if(roomTp == 1) {
            roomType = RoomType.SINGLE;
        } else if(roomTp == 2) {
            roomType = RoomType.DOUBLE;
        }
        System.out.println("Please enter the price per night");
        Double price = scanner.nextDouble();
        listOfRoom.add(new Room(roomId, roomType, price));
        adminResource.addRoom(listOfRoom);

        System.out.println("Would you like to add another room y/n");
        String answer = scanner.next();
        // make sure the answer to be y or n
        while(!answer.equals("y") && !answer.equals("n")) {
            System.out.println("Please enter y (Yes) or n (No)");
            answer = scanner.next();
        }
        if(answer.equals("y")) {
            addARoom();
        }
    }

    public static void startAdmin() {
        display_Menu();
        input = new Scanner(System.in);
        int selection = input.nextInt();

        while(true) {
            switch (selection) {
                case 1 :
                    seeAllCustomers();
                    break;
                case 2 :
                    seeAllRooms();
                    break;
                case 3 :
                    seeAllReservations();
                    break;
                case 4 :
                    addARoom();
                    break;
                case 5 :
                    mainMenu.startMain();
                    break;
                default:
                    System.out.println("Incorrect Input");
                    break;
            }

            display_Menu();
            selection = input.nextInt();
        }

    }

}
