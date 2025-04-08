package mn.partners.runtime.dal.repository;

import mn.partners.runtime.dal.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {

    Optional<HotelEntity> findByName(String name);
}