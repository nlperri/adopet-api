package adopet.api.domain.adoption.validator;

import adopet.api.domain._exception.ValidatorException;
import adopet.api.domain.adoption.StatusAdoption;
import adopet.api.domain.adoption.dto.AdoptionRequestDTO;
import adopet.api.domain.adoption.entity.Adoption;
import adopet.api.domain.adoption.repository.AdoptionRepository;
import adopet.api.domain.owner.entity.Owner;
import adopet.api.domain.owner.repository.OwnerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OwnerWithAdoptionInProgressValidatorTest {

    @Mock
    private AdoptionRepository adoptionRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    OwnerWithAdoptionInProgressValidator ownerWithAdoptionInProgressValidator;

    @Mock
    private AdoptionRequestDTO data;

    @Mock private Owner owner;

    @Test
    @DisplayName("It should validate adoption when owner doesn't have adoption in progress")
    public void adoptionInProgressFalse() {

        BDDMockito.given(ownerRepository.getReferenceById(data.ownerId())).willReturn(owner);
        BDDMockito.given(adoptionRepository.findAll()).willReturn(Collections.emptyList());

        Assertions.assertDoesNotThrow(() -> ownerWithAdoptionInProgressValidator.validate(data));
    }

    @Test
    @DisplayName("It should throw exception when owner have adoption in progress")
    public void adoptionInProgressTrue() {

        BDDMockito.given(ownerRepository.getReferenceById(data.ownerId())).willReturn(owner);

        List<Adoption> adoptions = Arrays.asList(
                createAdoption(owner, StatusAdoption.WAITING_EVALUATION)
        );
        BDDMockito.given(adoptionRepository.findAll()).willReturn(adoptions);


        Assertions.assertThrows(ValidatorException.class, () -> ownerWithAdoptionInProgressValidator.validate(data));
    }

    private Adoption createAdoption(Owner owner, StatusAdoption status) {
        Adoption adoption = Mockito.mock(Adoption.class);
        BDDMockito.given(adoption.getOwner()).willReturn(owner);
        BDDMockito.given(adoption.getStatus()).willReturn(status);
        return adoption;    }
}