package org.example.hotelbookingappbackend.service.implement;

import lombok.RequiredArgsConstructor;
import org.example.hotelbookingappbackend.entity.Room;
import org.example.hotelbookingappbackend.repository.RoomRepository;
import org.example.hotelbookingappbackend.service.interfaces.IRoomService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor// tự động tạo 1 constructor khởi tạo tất cả các thuộc tính final
public class RoomService implements IRoomService {
    private final RoomRepository roomRepository;

    @Override
    public Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice) throws SQLException, IOException {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        if (!file.isEmpty()) {
            byte[] photoBytes = file.getBytes();
            Blob phototoBlob = new SerialBlob(photoBytes);
            room.setPhoto(phototoBlob);
        }
        return roomRepository.save(room);
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }
}
