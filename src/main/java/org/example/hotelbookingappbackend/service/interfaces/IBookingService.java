package org.example.hotelbookingappbackend.service.interfaces;

import org.example.hotelbookingappbackend.entity.BookedRoom;

import java.util.List;

public interface IBookingService {
    List<BookedRoom> getAllBookingsByRoomId(Long roomId);
}
