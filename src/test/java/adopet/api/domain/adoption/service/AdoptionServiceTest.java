package adopet.api.domain.adoption.service;

import adopet.api.domain.adoption.dto.AdoptionRequestDTO;
import adopet.api.domain.adoption.dto.ApprovedAdoptionDTO;
import adopet.api.domain.adoption.dto.DisapprovedAdoptionDTO;
import adopet.api.domain.adoption.entity.Adoption;
import adopet.api.domain.adoption.repository.AdoptionRepository;
import adopet.api.domain.adoption.validator.AdoptionRequestValidator;
import adopet.api.domain.email.EmailService;
import adopet.api.domain.owner.entity.Owner;
import adopet.api.domain.owner.repository.OwnerRepository;
import adopet.api.domain.pet.entity.Pet;
import adopet.api.domain.pet.repository.PetRepository;
import adopet.api.domain.shelter.entity.Shelter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AdoptionServiceTest {
    private AdoptionRequestDTO data;
    private ApprovedAdoptionDTO approvedData;
    private DisapprovedAdoptionDTO disapprovedData;

    @InjectMocks
    AdoptionService adoptionService;

    @Mock
    private AdoptionRepository adoptionRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private AdoptionRequestValidator validator1;

    @Mock
    private AdoptionRequestValidator validator2;

    @Mock
    private Pet pet;

    @Mock
    private Owner owner;

    @Mock
    private Shelter shelter;

    @Mock
    private Adoption adoption;

    @Spy
    private List<AdoptionRequestValidator> validators = new ArrayList<>();

    @Captor
    private ArgumentCaptor<Adoption> adoptionCaptor;

    @Captor
    private ArgumentCaptor<String> emailCaptor;

    @Captor
    private ArgumentCaptor<String> subjectCaptor;

    @Captor
    private ArgumentCaptor<String> bodyCaptor;

    @Test
    @DisplayName("It should save adoption request in repository")
    void saveAdoptionRequest() {

        this.data = new AdoptionRequestDTO(10l, 20l, "some motive");

        BDDMockito.given(petRepository.getReferenceById(data.petId())).willReturn(pet);
        BDDMockito.given(ownerRepository.getReferenceById(data.ownerId())).willReturn(owner);
        BDDMockito.given(pet.getShelter()).willReturn(shelter);

        adoptionService.request(data);

        BDDMockito.then(adoptionRepository).should().save(adoptionCaptor.capture());
        Adoption savedAdoption = adoptionCaptor.getValue();

        Assertions.assertEquals(pet, savedAdoption.getPet());
        Assertions.assertEquals(owner, savedAdoption.getOwner());
        Assertions.assertEquals(data.motive(), savedAdoption.getMotive());
    }

    @Test
    @DisplayName("It should call injected validators")
    void validationCall() {
        this.data = new AdoptionRequestDTO(10l, 20l, "some motive");

        BDDMockito.given(petRepository.getReferenceById(data.petId())).willReturn(pet);
        BDDMockito.given(ownerRepository.getReferenceById(data.ownerId())).willReturn(owner);
        BDDMockito.given(pet.getShelter()).willReturn(shelter);

        validators.add(validator1);
        validators.add(validator2);

        adoptionService.request(data);

        BDDMockito.then(validator1).should().validate(data);
        BDDMockito.then(validator2).should().validate(data);
    }

    @Test
    @DisplayName("It should send e-mail when requested an adoption")
    void sendEmailRequest() {
        this.data = new AdoptionRequestDTO(10l, 20l, "some motive");

        BDDMockito.given(petRepository.getReferenceById(data.petId())).willReturn(pet);
        BDDMockito.given(ownerRepository.getReferenceById(data.ownerId())).willReturn(owner);
        BDDMockito.given(pet.getShelter()).willReturn(shelter);

        adoptionService.request(data);

        BDDMockito.then(emailService).should().sendEmail(
                emailCaptor.capture(),
                subjectCaptor.capture(),
                bodyCaptor.capture()
        );

        String expectedShelterEmail = shelter.getEmail();
        String expectedShelterName = shelter.getName();
        String expectedPetName = pet.getName();

        Assertions.assertEquals(expectedShelterEmail, emailCaptor.getValue());
        Assertions.assertEquals("Solicitação de adoção", subjectCaptor.getValue());
        Assertions.assertEquals("Olá" + expectedShelterName + "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " + expectedPetName + ". \nFavor avaliar para aprovação ou reprovação.", bodyCaptor.getValue());
    }

    @Test
    @DisplayName("It should mark adoption as approved when approved an adoption")
    void approveAdoption() {
        this.approvedData = new ApprovedAdoptionDTO(10l);

        BDDMockito.given(adoptionRepository.getReferenceById(approvedData.adoptionId())).willReturn(adoption);
        BDDMockito.given(adoption.getPet()).willReturn(pet);
        BDDMockito.given(adoption.getOwner()).willReturn(owner);
        BDDMockito.given(pet.getShelter()).willReturn(shelter);
        BDDMockito.given(adoption.getDate()).willReturn(LocalDateTime.now());

        adoptionService.approve(approvedData);

        BDDMockito.then(adoption).should().markAsApproved();
    }

    @Test
    @DisplayName("It should send e-mail when approved an adoption")
    void approveAdoptionEmail() {
        this.approvedData = new ApprovedAdoptionDTO(10l);

        BDDMockito.given(adoptionRepository.getReferenceById(approvedData.adoptionId())).willReturn(adoption);
        BDDMockito.given(adoption.getPet()).willReturn(pet);
        BDDMockito.given(adoption.getOwner()).willReturn(owner);
        BDDMockito.given(pet.getShelter()).willReturn(shelter);
        BDDMockito.given(adoption.getDate()).willReturn(LocalDateTime.now());
        BDDMockito.given(adoption.getPet().getShelter().getEmail()).willReturn("shelter@example.com");
        BDDMockito.given(adoption.getPet().getShelter().getName()).willReturn("ShelterName");
        BDDMockito.given(adoption.getOwner().getName()).willReturn("OwnerName");
        BDDMockito.given(adoption.getPet().getName()).willReturn("PetName");

        adoptionService.approve(approvedData);

        BDDMockito.then(emailService).should(Mockito.times(1)).sendEmail(
                anyString(),
                eq("Adoção aprovada"),
                anyString()
        );

        BDDMockito.then(emailService).should().sendEmail(
                emailCaptor.capture(),
                subjectCaptor.capture(),
                bodyCaptor.capture()
        );

        String expectedShelterEmail = "shelter@example.com";
        String ownerName = "OwnerName";
        String petName = "PetName";
        String adoptionDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String shelterName = "ShelterName";

        Assertions.assertEquals(expectedShelterEmail, emailCaptor.getValue());
        Assertions.assertEquals("Adoção aprovada", subjectCaptor.getValue());
        Assertions.assertEquals("Parabéns " + ownerName +
                "!\n\nSua adoção do pet " + petName + ", solicitada em " + adoptionDate + ", foi aprovada.\nFavor entrar em contato com o abrigo " + shelterName + " para agendar a busca do seu pet.", bodyCaptor.getValue());
    }

    @Test
    @DisplayName("It should mark adoption as disapproved when disapproved an adoption")
    void disapproveAdoption() {
        this.disapprovedData = new DisapprovedAdoptionDTO(10l, "some justification");

        BDDMockito.given(adoptionRepository.getReferenceById(disapprovedData.adoptionId())).willReturn(adoption);
        BDDMockito.given(adoption.getPet()).willReturn(pet);
        BDDMockito.given(adoption.getOwner()).willReturn(owner);
        BDDMockito.given(pet.getShelter()).willReturn(shelter);
        BDDMockito.given(adoption.getDate()).willReturn(LocalDateTime.now());

        adoptionService.disapprove(disapprovedData);

        BDDMockito.then(adoption).should().markAsDisapproved(disapprovedData.justification());
    }

    @Test
    @DisplayName("It should send e-mail when disapproved an adoption")
    void disapproveAdoptionEmail() {
        this.disapprovedData = new DisapprovedAdoptionDTO(10l, "some justification");

        BDDMockito.given(adoptionRepository.getReferenceById(disapprovedData.adoptionId())).willReturn(adoption);
        BDDMockito.given(adoption.getPet()).willReturn(pet);
        BDDMockito.given(adoption.getOwner()).willReturn(owner);
        BDDMockito.given(pet.getShelter()).willReturn(shelter);
        BDDMockito.given(adoption.getDate()).willReturn(LocalDateTime.now());
        BDDMockito.given(adoption.getPet().getShelter().getEmail()).willReturn("shelter@example.com");
        BDDMockito.given(adoption.getPet().getShelter().getName()).willReturn("ShelterName");
        BDDMockito.given(adoption.getOwner().getName()).willReturn("OwnerName");
        BDDMockito.given(adoption.getPet().getName()).willReturn("PetName");
        BDDMockito.given(adoption.getMotive()).willReturn("Some motive");

        adoptionService.disapprove(disapprovedData);

        BDDMockito.then(emailService).should(Mockito.times(1)).sendEmail(
                anyString(),
                eq("Solicitação de adoção"),
                anyString()
        );

        BDDMockito.then(emailService).should().sendEmail(
                emailCaptor.capture(),
                subjectCaptor.capture(),
                bodyCaptor.capture()
        );

        String expectedShelterEmail = "shelter@example.com";
        String ownerName = "OwnerName";
        String petName = "PetName";
        String adoptionDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String shelterName = "ShelterName";
        String motive = "Some motive";

        Assertions.assertEquals(expectedShelterEmail, emailCaptor.getValue());
        Assertions.assertEquals("Solicitação de adoção", subjectCaptor.getValue());
        Assertions.assertEquals("Olá " + ownerName + "!\n\nInfelizmente sua adoção do pet " + petName + ", solicitada em " + adoptionDate + ", foi reprovada pelo abrigo " + shelterName + " com a seguinte justificativa: " + motive, bodyCaptor.getValue());
    }

}
