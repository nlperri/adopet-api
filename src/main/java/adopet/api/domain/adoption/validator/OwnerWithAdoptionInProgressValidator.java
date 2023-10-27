package adopet.api.domain.adoption.validator;

import adopet.api.domain._exception.ValidatorException;
import adopet.api.domain.adoption.entity.Adoption;
import adopet.api.domain.adoption.repository.AdoptionRepository;
import adopet.api.domain.adoption.dto.AdoptionRequestDTO;
import adopet.api.domain.adoption.StatusAdoption;
import adopet.api.domain.owner.entity.Owner;
import adopet.api.domain.owner.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OwnerWithAdoptionInProgressValidator implements AdoptionRequestValidator{

    @Autowired
    private AdoptionRepository adoptionRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    public void validate(AdoptionRequestDTO data) {
        List<Adoption> adoptions = adoptionRepository.findAll();

        Owner owner = ownerRepository.getReferenceById(data.ownerId());

        for (Adoption a : adoptions) {
            if(a.getOwner() == owner && a.getStatus() == StatusAdoption.WAITING_EVALUATION) {
                throw new ValidatorException("Tutor já possui outra adoção aguardando avaliação.");
            }
        }
    }

}
