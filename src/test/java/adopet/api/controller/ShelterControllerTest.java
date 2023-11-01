package adopet.api.controller;

import adopet.api.domain._exception.ValidatorException;
import adopet.api.domain.pet.PetType;
import adopet.api.domain.pet.dto.PetDTO;
import adopet.api.domain.pet.dto.PetRegisterDTO;
import adopet.api.domain.pet.entity.Pet;
import adopet.api.domain.pet.service.PetService;
import adopet.api.domain.shelter.dto.ShelterDTO;
import adopet.api.domain.shelter.dto.ShelterRegisterDTO;
import adopet.api.domain.shelter.entity.Shelter;
import adopet.api.domain.shelter.service.ShelterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ShelterControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ShelterService shelterService;

    @MockBean
    private PetService petService;

    @Autowired
    JacksonTester<List<ShelterDTO>> sheltersListJson;

    @Autowired
    JacksonTester<List<PetDTO>> petsShelterList;

    @Autowired
    JacksonTester<ShelterRegisterDTO> shelterRegisterJson;

    @Autowired
    JacksonTester<PetRegisterDTO> petRegisterJson;


    @Test
    @DisplayName("It should return 200 http status and shelters")
    void fetchSuccess() throws Exception {
        List<ShelterDTO> mockShelterList = List.of(
                shelterDataMock(10L),
                shelterDataMock(20L)
        );

        BDDMockito.when(shelterService.list()).thenReturn(mockShelterList);

        var response = mvc.perform(get("/shelters")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var responseExpectation = sheltersListJson.write(mockShelterList).getJson();

        assertThat(response.getContentAsString()).isEqualTo(responseExpectation);
    }

    @Test
    @DisplayName("It should return 200 http status and pets from shelter when provided valid shelter id")
    void fetchPetsWithIdSuccess() throws Exception {
        List<PetDTO> mockPetList = List.of(
                petDataMock(20L, "Shelter"),
                petDataMock(20L, "Shelter")
        );

        BDDMockito.when(shelterService.listShelterPets("Shelter")).thenReturn(mockPetList);

        var response = mvc.perform(get("/shelters/{name}/pets", "Shelter")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var responseExpectation = petsShelterList.write(mockPetList).getJson();

        assertThat(response.getContentAsString()).isEqualTo(responseExpectation);
    }

    @Test
    @DisplayName("It should return 200 http status and pets from shelter when provided valid shelter name")
    void fetchPetsWithNameSuccess() throws Exception {
        List<PetDTO> mockPetList = List.of(
                petDataMock(20L, "Shelter"),
                petDataMock(20L, "Shelter")
        );

        BDDMockito.when(shelterService.listShelterPets("20")).thenReturn(mockPetList);

        var response = mvc.perform(get("/shelters/{id}/pets", "20")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var responseExpectation = petsShelterList.write(mockPetList).getJson();

        assertThat(response.getContentAsString()).isEqualTo(responseExpectation);
    }

    @Test
    @DisplayName("It should throw an exception when provided invalid shelter id or name")
    void fetchPetsFailed() throws Exception {

        BDDMockito.given(shelterService.listShelterPets("20")).willThrow(ValidatorException.class);

        var response = mvc.perform(get("/shelters/{id}/pets", "20")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("It should return 200 http status code when provided valid data to register shelter")
    void registerSuccess() throws Exception {

        ShelterRegisterDTO shelterRegisterData = shelterRegisterMock("Shelter");

        var response = mvc.perform(post("/shelters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(shelterRegisterJson.write(
                        shelterRegisterData
                ).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("It should return 400 http status code when provided invalid data to register shelter")
    void registerFailed() throws Exception {

        var response = mvc.perform(post("/shelters")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("It should return 200 http status code when provided valid data to register pet and valid shelter id")
    void registerPetWithShelterIdSuccess() throws Exception {
        Shelter shelter = shelterMock(10L, "Shelter");

        BDDMockito.when(shelterService.findShelter("10")).thenReturn(shelter);

        PetRegisterDTO petRegisterData = petRegisterMock();

        var response = mvc.perform(post("/shelters/{name}/pets", "Shelter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        petRegisterJson.write(
                                petRegisterData
                        ).getJson()
                )
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("It should return 200 http status code when provided valid data to register pet and valid shelter name")
    void registerPetWithShelterNameSuccess() throws Exception {
        Shelter shelter = shelterMock(10L, "Shelter");

        BDDMockito.when(shelterService.findShelter("Shelter")).thenReturn(shelter);

        PetRegisterDTO petRegisterData = petRegisterMock();

        var response = mvc.perform(post("/shelters/{name}/pets", "Shelter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        petRegisterJson.write(
                                petRegisterData
                        ).getJson()
                )
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("It should return 400 http status code when provided valid data to register pet and invalid shelter id or name")
    void registerPetFailed() throws Exception {


        BDDMockito.when(shelterService.findShelter("Shelter")).thenThrow(ValidatorException.class);

        var response = mvc.perform(post("/shelters/{name}/pets", "Shelter")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private PetDTO petDataMock(Long shelterId, String name) {
        return new PetDTO(
                pet(shelterId, name)
        );
    }

    private Pet pet (Long shelterId, String name) {
        return new Pet(
                petRegisterMock(),
                shelterMock(shelterId, name)
        );
    }

    private PetRegisterDTO petRegisterMock() {
        return new PetRegisterDTO(
                PetType.CAT,
                "Cat",
                "Siameses",
                5,
                "black",
                10F
        );
    }

    private Shelter shelterMock(Long id, String name) {
        Shelter shelter = new Shelter(
                shelterRegisterMock(name)
        );
        shelter.setId(id);

        return shelter;
    };

    private ShelterDTO shelterDataMock(Long id) {
        return new ShelterDTO(
                id,
                "Shelter"
        );
    }

    private ShelterRegisterDTO shelterRegisterMock(String name) {
        return new ShelterRegisterDTO(
                name,
                "21989897878",
                "shelter@email.com"
        );
    }
}