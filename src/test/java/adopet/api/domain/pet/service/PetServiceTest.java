package adopet.api.domain.pet.service;

import adopet.api.domain.adoption.entity.Adoption;
import adopet.api.domain.owner.entity.Owner;
import adopet.api.domain.pet.PetType;
import adopet.api.domain.pet.dto.PetDTO;
import adopet.api.domain.pet.dto.PetRegisterDTO;
import adopet.api.domain.pet.entity.Pet;
import adopet.api.domain.pet.repository.PetRepository;
import adopet.api.domain.shelter.entity.Shelter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    List<Pet> petData;

    @InjectMocks
    PetService petService;

    @Mock
    private PetRepository petRepository;

    @Mock
    Shelter shelter;

    @Mock
    PetRegisterDTO data;

    @Test
    @DisplayName("It should fetch all available pets")
    void fetchAvailablePets() {

        BDDMockito.given(petRepository.findAllByIsAdoptedFalse()).willReturn(petData);

       petService.searchAvailable();

        Mockito.verify(petRepository, Mockito.times(1)).findAllByIsAdoptedFalse();
    }

    @Test
    @DisplayName("It should register pet")
    void registerPet() {
        petService.register(shelter, data);

        BDDMockito.then(petRepository).should(Mockito.times(1)).save(any(Pet.class));
    }
}