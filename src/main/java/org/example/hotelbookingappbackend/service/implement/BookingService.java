package org.example.hotelbookingappbackend.service.implement;

import lombok.RequiredArgsConstructor;
import org.example.hotelbookingappbackend.entity.BookedRoom;
import org.example.hotelbookingappbackend.repository.BookingRepository;
import org.example.hotelbookingappbackend.service.interfaces.IBookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository;

    public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }
}
