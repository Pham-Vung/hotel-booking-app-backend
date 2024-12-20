package org.example.hotelbookingappbackend.repository;

import org.example.hotelbookingappbackend.entity.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookedRoom, Long> {
    List<BookedRoom> findByRoomId(Long roomId);
}
