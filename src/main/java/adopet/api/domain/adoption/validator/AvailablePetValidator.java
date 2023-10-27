package adopet.api.domain.adoption.validator;

import adopet.api.domain._exception.ValidatorException;
import adopet.api.domain.adoption.dto.AdoptionRequestDTO;
import adopet.api.domain.pet.entity.Pet;
import adopet.api.domain.pet.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AvailablePetValidator implements AdoptionRequestValidator{

    @Autowired
    private PetRepository petRepository;

    public void validate(AdoptionRequestDTO data) {
        Pet pet = petRepository.getReferenceById(data.petId());

        if(pet.getIsAdopted()) {
            throw new ValidatorException("Pet já foi adotado.");
        }
    }
}
