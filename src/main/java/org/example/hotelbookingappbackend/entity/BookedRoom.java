package org.example.hotelbookingappbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity// chuyển đổi thực thể này vào cơ sở dữ liệu
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookedRoom {
    @Id // bookingId là khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY) // taọ giá trị tự động tăng cho khóa chính
    private Long bookingId;

    @Column(name = "check_in")
    private LocalDate checkInDate;

    @Column(name = "check_out")
    private LocalDate checkOutDate;

    @Column(name = "guest_fullName")
    private String guestFullName;

    @Column(name = "guest_email")
    private String guestEmail;

    @Column(name = "adults")
    private int numberOfAdults;// số lượng người lớn

    @Column(name = "children")
    private int numberOfChildren;// số lượng trẻ em

    @Column(name = "total_guest")
    private int totalNumberOfGuests;// tổng số lượng khách

    @Setter
    @Column(name = "confirmation_code")
    private String bookingConfirmationCode;// mã xác nhận khi đặt phòng

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)// Room chỉ được tải khi truy cập nó, giúp cải thiện hiệu suất
    @JoinColumn(name = "room_id") // khóa ngoại trỏ đến khóa chính của Room
    private Room room;

    public void calculateTotalNumberOfGuest() {
        totalNumberOfGuests = numberOfAdults + numberOfChildren;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
        calculateTotalNumberOfGuest();
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
        calculateTotalNumberOfGuest();
    }
}
