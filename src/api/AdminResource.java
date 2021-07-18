package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    public static final CustomerService customerService = CustomerService.getInstance();
    public static final ReservationService reservationService = ReservationService.getInstance();
    public static AdminResource adminResource = null;

    private AdminResource() {
    }

    public static AdminResource getInstance() {
        if(adminResource == null) {
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    public Customer getCustomer(String email) {

        return customerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms) {
        try {
            for(IRoom room : rooms) {
                reservationService.addRoom(room);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getRooms();
    }

    public Collection<Customer> getAllCustomer() {
        return customerService.getAllCustomer();
    }

    public void displayAllReservations() {
        try {
            for(Reservation reservation : reservationService.getReservations()) {
                System.out.println(reservation);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
