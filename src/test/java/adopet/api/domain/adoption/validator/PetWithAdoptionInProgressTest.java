package adopet.api.domain.adoption.validator;

import adopet.api.domain._exception.ValidatorException;
import adopet.api.domain.adoption.StatusAdoption;
import adopet.api.domain.adoption.dto.AdoptionRequestDTO;
import adopet.api.domain.adoption.repository.AdoptionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PetWithAdoptionInProgressTest {

    @Mock
    private AdoptionRepository adoptionRepository;

    @Mock
    private AdoptionRequestDTO data;

    @InjectMocks
    private PetWithAdoptionInProgress petWithAdoptionInProgress;

    @Test
    @DisplayName("It should validate when pet doesn't have adoption in progress")
    public void petWithAdoptionInProgressFalse() {

        BDDMockito.given(adoptionRepository.existsByPetIdAndStatus(data.petId(), StatusAdoption.WAITING_EVALUATION)).willReturn(false);

        Assertions.assertDoesNotThrow(() -> petWithAdoptionInProgress.validate(data) );
    }

    @Test
    @DisplayName("It should throw exception when pet have adoption in progress")
    public void petWithAdoptionInProgressTrue() {

        BDDMockito.given(adoptionRepository.existsByPetIdAndStatus(data.petId(), StatusAdoption.WAITING_EVALUATION)).willReturn(true);

        Assertions.assertThrows(ValidatorException.class, () -> petWithAdoptionInProgress.validate(data) );
    }

}