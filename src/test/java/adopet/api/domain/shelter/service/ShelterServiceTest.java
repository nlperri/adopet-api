package adopet.api.domain.shelter.service;

import adopet.api.domain._exception.ValidatorException;
import adopet.api.domain.pet.PetType;
import adopet.api.domain.pet.dto.PetDTO;
import adopet.api.domain.pet.dto.PetRegisterDTO;
import adopet.api.domain.pet.entity.Pet;
import adopet.api.domain.pet.repository.PetRepository;
import adopet.api.domain.shelter.dto.ShelterDTO;
import adopet.api.domain.shelter.dto.ShelterRegisterDTO;
import adopet.api.domain.shelter.entity.Shelter;
import adopet.api.domain.shelter.repository.ShelterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class ShelterServiceTest {
    private PetRegisterDTO petRegisterData;

    @InjectMocks
    private ShelterService shelterService;

    @Mock
    private ShelterRepository shelterRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    List<Shelter> dataList;

    @Mock
    ShelterRegisterDTO registerData;

    @Mock
    List<Pet> petList;

    @Mock
    Shelter shelter;

    @Test
    @DisplayName("It should list all shelters")
    void listShelters() {
        BDDMockito.given(shelterRepository.findAll()).willReturn(dataList);

        shelterService.list();

        Mockito.verify(shelterRepository, Mockito.times(1)).findAll();
 }

    @Test
    @DisplayName("It should register shelter when it is not registered")
    void registerShelter() {
        BDDMockito.given(shelterRepository.existsByNameOrPhoneOrEmail(registerData.name(), registerData.phone(), registerData.email())).willReturn(false);

        shelterService.register(registerData);

        Mockito.verify(shelterRepository, Mockito.times(1)).save(any(Shelter.class));
    }

    @Test
    @DisplayName("It should throw exception when it is already registered")
    void shelterExists() {
        BDDMockito.given(shelterRepository.existsByNameOrPhoneOrEmail(registerData.name(), registerData.phone(), registerData.email())).willReturn(true);

        Assertions.assertThrows(ValidatorException.class, () -> shelterService.register(registerData));
    }

    @Test
    @DisplayName("It should list pet shelters")
    void listShelterPets() {
        BDDMockito.lenient().when(shelterRepository.findById(1L)).thenReturn(Optional.ofNullable(shelter));
        BDDMockito.lenient().when(shelterRepository.findByName("Shelter")).thenReturn(Optional.ofNullable(shelter));

        BDDMockito.given(petRepository.findByShelter(shelter)).willReturn(petList);

       shelterService.listShelterPets("1");

       Mockito.verify(petRepository, Mockito.times(1)).findByShelter(any(Shelter.class));
    }
}