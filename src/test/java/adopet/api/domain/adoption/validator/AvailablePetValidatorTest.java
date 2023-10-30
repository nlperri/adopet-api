package adopet.api.domain.adoption.validator;

import adopet.api.domain._exception.ValidatorException;
import adopet.api.domain.adoption.dto.AdoptionRequestDTO;

import adopet.api.domain.pet.entity.Pet;
import adopet.api.domain.pet.repository.PetRepository;
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
class AvailablePetValidatorTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private AvailablePetValidator availablePetValidator;

    @Mock
    private Pet pet;

    @Mock
    private AdoptionRequestDTO data;

    @Test
    @DisplayName("It should validate pet to get adopted when it is available")
    public void availablePetTrue() {

        BDDMockito.given(petRepository.getReferenceById(data.petId())).willReturn(pet);
        BDDMockito.given(pet.getIsAdopted()).willReturn(false);

        Assertions.assertDoesNotThrow(() -> availablePetValidator.validate(data));

    }
    @Test
    @DisplayName("It should throw exception when pet is not available")
    public void availablePetFalse() {
        BDDMockito.given(petRepository.getReferenceById(data.petId())).willReturn(pet);
        BDDMockito.given(pet.getIsAdopted()).willReturn(true);

        Assertions.assertThrows(ValidatorException.class, () -> availablePetValidator.validate(data));
    }
}