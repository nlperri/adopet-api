package adopet.api.domain.pet.repository;

import adopet.api.domain.pet.entity.Pet;
import adopet.api.domain.shelter.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByIsAdoptedFalse();
    List<Pet> findByShelter(Shelter shelter);
}
