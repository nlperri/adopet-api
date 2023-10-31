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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdoptionService {

    @Autowired
    private AdoptionRepository adoptionRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private List<AdoptionRequestValidator> validators;

    public void request(AdoptionRequestDTO data) {
        Pet pet = petRepository.getReferenceById(data.petId());
        Owner owner = ownerRepository.getReferenceById(data.ownerId());

        validators.forEach(v -> v.validate(data));

        Adoption adoption = new Adoption(owner, pet, data.motive());

        adoptionRepository.save(adoption);

        String shelterEmail = adoption.getPet().getShelter().getEmail();
        String shelterName = adoption.getPet().getShelter().getName();
        String petName = adoption.getPet().getName();

        emailService.sendEmail(
                shelterEmail,
                "Solicitação de adoção",
                "Olá" + shelterName + "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " + petName + ". \nFavor avaliar para aprovação ou reprovação.");

    }

    public void approve(ApprovedAdoptionDTO data) {
        Adoption adoption = adoptionRepository.getReferenceById(data.adoptionId());
        adoption.markAsApproved();

        String shelterEmail = adoption.getPet().getShelter().getEmail();
        String ownerName = adoption.getOwner().getName();
        String petName = adoption.getPet().getName();
        String adoptionDate = adoption.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String shelterName = adoption.getPet().getShelter().getName();

        emailService.sendEmail(
                shelterEmail,
                "Adoção aprovada",
                "Parabéns " + ownerName +
                "!\n\nSua adoção do pet " + petName + ", solicitada em " + adoptionDate + ", foi aprovada.\nFavor entrar em contato com o abrigo " + shelterName + " para agendar a busca do seu pet."
        );
    }

    public void disapprove(DisapprovedAdoptionDTO data) {
        Adoption adoption = adoptionRepository.getReferenceById(data.adoptionId());
        adoption.markAsDisapproved(data.justification());

        String shelterEmail = adoption.getPet().getShelter().getEmail();
        String ownerName = adoption.getOwner().getName();
        String petName = adoption.getPet().getName();
        String adoptionDate = adoption.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String shelterName = adoption.getPet().getShelter().getName();
        String motive = adoption.getMotive();

        emailService.sendEmail(
                shelterEmail,
                "Solicitação de adoção",
                "Olá " + ownerName + "!\n\nInfelizmente sua adoção do pet " + petName + ", solicitada em " + adoptionDate + ", foi reprovada pelo abrigo " + shelterName + " com a seguinte justificativa: " + motive
        );
    }


}
