package adopet.api.domain.shelter.repository;

import adopet.api.domain.shelter.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    Optional<Shelter> findByName(String name);

    boolean existsByNameOrPhoneOrEmail(String name, String phone, String email);
}
