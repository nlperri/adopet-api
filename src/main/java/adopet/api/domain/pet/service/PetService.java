package adopet.api.domain.pet.service;

import adopet.api.domain.pet.dto.PetDTO;
import adopet.api.domain.pet.dto.PetRegisterDTO;
import adopet.api.domain.pet.entity.Pet;
import adopet.api.domain.pet.repository.PetRepository;
import adopet.api.domain.shelter.entity.Shelter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public List<PetDTO> searchAvailable() {
        return petRepository
                .findAllByIsAdoptedFalse()
                .stream()
                .map(PetDTO::new)
                .toList();
    }

    public void register(Shelter shelter, PetRegisterDTO data) {
        petRepository.save(new Pet(data, shelter));
    }
}
