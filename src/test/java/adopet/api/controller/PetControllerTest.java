package adopet.api.controller;

import adopet.api.domain.pet.PetType;
import adopet.api.domain.pet.dto.PetDTO;
import adopet.api.domain.pet.dto.PetRegisterDTO;
import adopet.api.domain.pet.entity.Pet;
import adopet.api.domain.pet.service.PetService;
import adopet.api.domain.shelter.dto.ShelterRegisterDTO;
import adopet.api.domain.shelter.entity.Shelter;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class PetControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    JacksonTester<List<PetDTO>> petListJson;

    @MockBean
    private PetService petService;

   @Test
   @DisplayName("It should return 200 http status code and available pets")
   void fetchAllAvailableSuccess() throws Exception {

       List<PetDTO> mockPetList = List.of(
               petDataMock(10l),
               petDataMock(20l)
       );

       BDDMockito.when(petService.searchAvailable()).thenReturn(mockPetList);

       var response = mvc.perform(get("/pets")).andReturn().getResponse();

       assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

       var responseExpectation = petListJson.write(
               mockPetList
       ).getJson();

       assertThat(response.getContentAsString()).isEqualTo(responseExpectation);
   }

    PetDTO petDataMock(Long id) {
        return new PetDTO(petMock(id));
    }

    private Pet petMock(Long id) {
        Pet pet = new Pet(
                petRegisterMock(),
                shelterMock()
        );
        pet.setId(id);
        return pet;
    }

    private Shelter shelterMock() {
        return new Shelter(shelterRegisterMock());
    }

    private ShelterRegisterDTO shelterRegisterMock() {
        return new ShelterRegisterDTO(
                "Shelter",
                "21989889898",
                "shelter@email.com"
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

}