package org.example.hotelbookingappbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity// chuyển đổi thực thể này vào cơ sở dữ liệu
@Getter
@Setter
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomType;
    private BigDecimal roomPrice;
    private boolean isBooked = false;

    @Lob// lưu dữ liệu lớn trong csdl như hình ảnh, video
    private Blob photo;

    /**
     * cascade = CascadeType.ALL
     * Cho phép các thao tác (INSERT, UPDATE, DELETE) trên Room
     * sẽ tự động được áp dụng cho các entity liên quan (ở đây là BookedRoom).
     * <p>
     * Ví dụ, nếu xóa bản ghi của entity hiện tại, tất cả
     * các bản ghi liên kết trong BookedRoom cũng sẽ bị xóa
     * (do CascadeType.REMOVE là một phần của CascadeType.ALL).
     */
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookedRoom> bookings;// lịch sử đặt phòng này

    public Room() {
        /**
         * khởi tạo lịch sử bằng rỗng vì ban đầu mới thêm vào
         * csdl thì phòng này chưa được đặt. Nếu truy vấn cơ sở dữ liệu
         * tới phòng này sẽ xảy ra ngoại lệ null pointer vì thế ban đâù
         * phải khởi tạo lịch sử rỗng
         */
        this.bookings = new ArrayList<>();
    }

    public void addBooking(BookedRoom booking) {
        if (bookings == null) {
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setRoom(this);
        isBooked = true;
        String bookingCode = RandomStringUtils.randomNumeric(10);
        booking.setBookingConfirmationCode(bookingCode);
    }
}
