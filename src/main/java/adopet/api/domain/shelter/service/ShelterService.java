package adopet.api.domain.shelter.service;

import adopet.api.domain._exception.ValidatorException;
import adopet.api.domain.pet.dto.PetDTO;
import adopet.api.domain.pet.repository.PetRepository;
import adopet.api.domain.shelter.entity.Shelter;
import adopet.api.domain.shelter.dto.ShelterDTO;
import adopet.api.domain.shelter.dto.ShelterRegisterDTO;
import adopet.api.domain.shelter.repository.ShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShelterService {

    @Autowired
    private ShelterRepository shelterRepository;

    @Autowired
    private PetRepository petRepository;

    public List<ShelterDTO> list() {
        return shelterRepository
                .findAll()
                .stream()
                .map(ShelterDTO::new)
                .toList();
    }

    public void register(ShelterRegisterDTO data) {
        boolean isRegistered = shelterRepository.existsByNameOrPhoneOrEmail(data.name(), data.phone(), data.email());

        if(isRegistered) {
            throw new ValidatorException("Dados já cadastrados para outro abrigo.");
        }

        shelterRepository.save(new Shelter(data));
    }

    public List<PetDTO> listShelterPets(String idOrName) {
        Shelter shelter = findShelter(idOrName);

        return petRepository
                .findByShelter(shelter)
                .stream()
                .map(PetDTO::new)
                .toList();
    }

    public Shelter findShelter(String idOrName) {
        Optional<Shelter> optional;
        try {
            Long id = Long.parseLong(idOrName);
            optional = shelterRepository.findById(id);
        } catch (NumberFormatException exception) {
            optional = shelterRepository.findByName(idOrName);
        }

        return optional.orElseThrow(() -> new ValidatorException("Abrigo não encontrado."));
    }
}
