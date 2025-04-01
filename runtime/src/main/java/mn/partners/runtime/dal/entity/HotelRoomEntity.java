package mn.partners.runtime.dal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mn.partners.runtime.common.enums.RoomType;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "hotel_rooms", uniqueConstraints = @UniqueConstraint(columnNames = {"hotel_id", "room_type"},
        name = "uk_hotel_room_type"))
public class HotelRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelEntity hotelEntity;

    private double price;

    @Enumerated(STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;

    @Column(name = "total_count_of_rooms")
    private int totalCountOfRooms;

    @Column(name = "reserved_rooms")
    private int reservedRooms ;
}