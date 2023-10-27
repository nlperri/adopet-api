package adopet.api.domain.adoption.validator;

import adopet.api.domain._exception.ValidatorException;
import adopet.api.domain.adoption.repository.AdoptionRepository;
import adopet.api.domain.adoption.dto.AdoptionRequestDTO;
import adopet.api.domain.adoption.StatusAdoption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PetWithAdoptionInProgress implements AdoptionRequestValidator {
    @Autowired
    private AdoptionRepository adoptionRepository;

    public void validate(AdoptionRequestDTO data) {
        boolean petHasAdoptionInProgress = adoptionRepository.existsByPetIdAndStatus(data.petId(), StatusAdoption.WAITING_EVALUATION);

        if(petHasAdoptionInProgress) {
            throw new ValidatorException("Pet já está aguardando avaliação para ser adotado.");
        }
    }
}
