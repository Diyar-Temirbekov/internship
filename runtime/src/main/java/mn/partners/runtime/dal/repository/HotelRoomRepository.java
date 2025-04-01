package mn.partners.runtime.dal.repository;

import mn.partners.runtime.common.enums.RoomType;
import mn.partners.runtime.dal.entity.HotelRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRoomRepository extends JpaRepository<HotelRoomEntity, Long> {

    HotelRoomEntity findByHotelEntityIdAndRoomType(Long hotelId, RoomType roomType);
}