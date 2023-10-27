package adopet.api.domain.adoption.validator;

import adopet.api.domain.adoption.dto.AdoptionRequestDTO;

public interface AdoptionRequestValidator {

    void validate(AdoptionRequestDTO data);
}
