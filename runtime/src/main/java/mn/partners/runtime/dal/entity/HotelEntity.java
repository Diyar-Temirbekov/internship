package mn.partners.runtime.dal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "hotels", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "address"},
        name = "uk_name_address"))
public class HotelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "hotelEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HotelRoomEntity> rooms = new ArrayList<>();
}