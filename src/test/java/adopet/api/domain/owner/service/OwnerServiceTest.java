package adopet.api.domain.owner.service;

import adopet.api.domain._exception.ValidatorException;
import adopet.api.domain.owner.dto.OwnerRegisterDTO;
import adopet.api.domain.owner.dto.UpdateOwnerDTO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

    @Mock
    Owner owner;

    @Mock
    OwnerRegisterDTO data;

    @Mock
    UpdateOwnerDTO updatedData;

    @InjectMocks
    OwnerService ownerService;

    @Mock
    private OwnerRepository ownerRepository;

    @Test
    @DisplayName("It should register owner its not already registered")
    void registerOwner() {
        BDDMockito.given(ownerRepository.existsByPhoneOrEmail(data.phone(), data.email())).willReturn(false);

        ownerService.register(data);

        BDDMockito.then(ownerRepository).should(Mockito.times(1)).save(any(Owner.class));
    }

    @Test
    @DisplayName("It should throw exception when owner is already registered")
    void ownerExists() {
        BDDMockito.given(ownerRepository.existsByPhoneOrEmail(data.phone(), data.email())).willReturn(true);

        Assertions.assertThrows(ValidatorException.class, () -> ownerService.register(data));
    }

    @Test
    @DisplayName("It should update an existing owner")
    void updateOwner() {
        BDDMockito.given(ownerRepository.getReferenceById(updatedData.id())).willReturn(owner);

        ownerService.update(updatedData);

        BDDMockito.then(owner).should(Mockito.times(1)).update(updatedData);
    }
}