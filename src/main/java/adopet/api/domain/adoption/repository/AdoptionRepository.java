package adopet.api.domain.adoption.repository;

import adopet.api.domain.adoption.StatusAdoption;
import adopet.api.domain.adoption.entity.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
    boolean existsByPetIdAndStatus(Long petId, StatusAdoption status);
}
