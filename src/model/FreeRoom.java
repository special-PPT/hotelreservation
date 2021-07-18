package model;

public class FreeRoom extends Room{
    public FreeRoom(String roomNumber, RoomType roomType, Double price) {
        super(roomNumber, roomType, price);
    }

    @Override
    public String toString() {
        return "this is a freeRoom";
    }
}
