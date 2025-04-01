package mn.partners.runtime.dal.repository;

import mn.partners.runtime.dal.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
}