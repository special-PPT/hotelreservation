package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

interface Description {
    default void roomNotAvailable() {
        System.out.println("This room is not available");
    }
    default void accountNotExist() {
        System.out.println("This account does not exist");
    }
    default  void roomExist() {
        System.out.println("This room already exists");
    }
    default  void roomNotExist() {
        System.out.println("This room does not exist");
    }
}

public class ReservationService implements Description {
    private static ReservationService reservationService = null;
    private static Map<String, Collection<Reservation>> customerReservationMap = new HashMap<>() ; // <key, value> customer reservations
    private static Map<String, IRoom> roomsMap = new HashMap<>(); // <key, value> roomID IRoom
    private static Map<String, List<Reservation>> roomReservationsMap = new HashMap<>(); // <key, value> roomID, reservations
    private static Collection<IRoom> rooms = new HashSet<>();
    private static Collection<Reservation> reservations = new HashSet<>();

    public Collection<Reservation> getReservations() {
        return reservations;
    }
    public Collection<IRoom> getRooms() {
        return rooms;
    }

    private ReservationService() { }

    public static ReservationService getInstance() {
        if(reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }
    public void addRoom(IRoom room){
        if(roomsMap.containsKey(room.getRoomNumber())) {
            reservationService.roomExist();
        } else {
            roomsMap.put(room.getRoomNumber(), room);
            rooms.add(room);
            roomReservationsMap.put(room.getRoomNumber(), new ArrayList<Reservation>());
        }

    }

    public IRoom getARoom(String roomId){
        if(!roomsMap.containsKey(roomId)){
            reservationService.roomNotExist();
            return null;
        }
        return roomsMap.get(roomId);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        if(!isValidReservation(customer, room, checkInDate, checkOutDate))
            return null;

        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        if(!customerReservationMap.containsKey(customer.getEmail())) {
            customerReservationMap.put(customer.getEmail(), new ArrayList<>());
        }

        if(!roomReservationsMap.containsKey(room.getRoomNumber())) {
            roomReservationsMap.put(room.getRoomNumber(), new LinkedList<>());
        }
        customerReservationMap.get(customer.getEmail()).add(reservation);
        roomReservationsMap.get(room.getRoomNumber()).add(reservation);
        reservations.add(reservation);

        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> result = new ArrayList<>();
        for(String room : roomsMap.keySet()) {
            if(!roomReservationsMap.containsKey(room)){
                result.add(roomsMap.get(room));
            } else {
                List<Reservation> reservations = roomReservationsMap.get(room);
                if(isValidRoom(reservations, checkInDate, checkOutDate)) {
                    result.add(roomsMap.get(room));
                }
            }
        }


        return result;
    }
    private boolean isValidRoom(List<Reservation> reservations, Date checkInDate, Date checkOutDate) {
        for(Reservation reservation : reservations){
            if(reservation.getCheckInDate().before(checkOutDate)) {
                if(reservation.getCheckOutDate().after(checkInDate)) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean isValidReservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        // does not find the room
        if(!roomsMap.containsKey(room.getRoomNumber())) {
            reservationService.roomNotExist();
            return false;
        }

        // the time is not available
        if(!this.isValidRoom(roomReservationsMap.get(room.getRoomNumber()), checkInDate, checkOutDate)) {
            reservationService.roomNotAvailable();
            return false;
        }
        return true;
    }

    public Collection<Reservation> getCustomerReservation(Customer customer) {
        if(!customerReservationMap.containsKey(customer.getEmail())) {
            reservationService.accountNotExist();
            return null;
        }
        return customerReservationMap.get(customer.getEmail());
    }

    public void printAllReservation() {
        System.out.println(roomReservationsMap.values());

    }


}
