package adopet.api.controller;

import adopet.api.ApiApplication;
import adopet.api.domain.adoption.dto.AdoptionRequestDTO;
import adopet.api.domain.adoption.dto.ApprovedAdoptionDTO;
import adopet.api.domain.adoption.dto.DisapprovedAdoptionDTO;
import adopet.api.domain.adoption.entity.Adoption;
import adopet.api.domain.adoption.service.AdoptionService;
import adopet.api.domain.owner.dto.OwnerRegisterDTO;
import adopet.api.domain.owner.entity.Owner;
import adopet.api.domain.pet.PetType;
import adopet.api.domain.pet.dto.PetRegisterDTO;
import adopet.api.domain.pet.entity.Pet;
import adopet.api.domain.shelter.dto.ShelterRegisterDTO;
import adopet.api.domain.shelter.entity.Shelter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AdoptionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<AdoptionRequestDTO> adoptionRequestJson;

    @Autowired
    private JacksonTester<ApprovedAdoptionDTO> approvedAdoptionJson;

    @Autowired
    private JacksonTester<DisapprovedAdoptionDTO> disapprovedAdoptionJson;

    @MockBean
    private AdoptionService adoptionService;

    @Test
    @DisplayName("It should return 200 http status code when receive valid body data on requesting adoption")
    void requestAdoptionSuccess() throws Exception {

        Adoption adoption = adoptionMock();

        AdoptionRequestDTO adoptionData = new AdoptionRequestDTO(adoption.getOwner().getId(),
                adoption.getPet().getId(),
                "motive");

        var response = mvc.perform(post("/adoptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(adoptionRequestJson.write(
                        adoptionData
                ).getJson())
        ).andReturn().getResponse();

        var responseExpectation = "Adoção solicitada com sucesso.";

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo(responseExpectation);
    }

    @Test
    @DisplayName("It should return 400 http status code when receive invalid body data on requesting adoption")
    void requestAdoptionFailed() throws Exception {

        var response = mvc.perform(post("/adoptions")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("It should return 200 http status code when receive valid data on approval adoption")
    void approvalAdoptionSuccess() throws Exception {
        ApprovedAdoptionDTO approvedAdoptionData = approvedAdoptionMock();

        var response = mvc.perform(put("/adoptions/approval")
                .contentType(MediaType.APPLICATION_JSON)
                .content(approvedAdoptionJson.write(
                        approvedAdoptionData
                ).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("It should return 400 http status code when receive invalid body data on approval adoption")
    void approvalAdoptionFailed() throws Exception {

        var response = mvc.perform(put("/adoptions/approval")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("It should return 200 http status code when receive valid data on disapproval adoption")
    void disapprovalAdoptionSuccess() throws Exception {
        DisapprovedAdoptionDTO disapprovedAdoptionData = disapprovedAdoptionMock();

        var response = mvc.perform(put("/adoptions/disapproval")
                .contentType(MediaType.APPLICATION_JSON)
                .content(disapprovedAdoptionJson.write(
                        disapprovedAdoptionData
                ).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("It should return 400 http status code when receive invalid body data on disapproval adoption")
    void disapprovalAdoptionFailed() throws Exception {

        var response = mvc.perform(put("/adoptions/disapproval")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private Adoption adoptionMock() {
        Adoption adoption = new Adoption(ownerMock(), petMock(), "motive");
        adoption.setId(1l);
        return adoption;
    }

    private Pet petMock() {
        Pet pet = new Pet(
                petRegisterMock(),
                shelterMock()
        );
        pet.setId(1l);
        return pet;
    }

    private Shelter shelterMock() {
        return new Shelter(shelterRegisterMock());
    }

    private Owner ownerMock() {
        Owner owner = new Owner(ownerRegisterMock());
        owner.setId(1l);
        return owner;
    }

    private ApprovedAdoptionDTO approvedAdoptionMock() {
        return new ApprovedAdoptionDTO(adoptionMock().getId());
    }

    private DisapprovedAdoptionDTO disapprovedAdoptionMock() {
        return new DisapprovedAdoptionDTO(adoptionMock().getId(), "justification");
    }

    private AdoptionRequestDTO adoptionRequestMock() {
        return new AdoptionRequestDTO(
                petMock().getId(),
                ownerMock().getId(),
                "Some motive"
        );
    }

    private PetRegisterDTO petRegisterMock() {
        return new PetRegisterDTO(
                PetType.DOG,
                "Buddy",
                "Golden Retriever",
                3,
                "Golden",
                25.0f);
    }

    private ShelterRegisterDTO shelterRegisterMock() {
        return new ShelterRegisterDTO(
                "Shelter",
                "21989889898",
                "shelter@email.com"
        );
    }

    private OwnerRegisterDTO ownerRegisterMock() {
        return new OwnerRegisterDTO(
                "John Doe",
                "21987878765",
                "jhondoe@email.com"
        );
    }
}