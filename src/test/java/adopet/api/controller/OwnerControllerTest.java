package adopet.api.controller;

import adopet.api.domain.owner.dto.OwnerRegisterDTO;
import adopet.api.domain.owner.dto.UpdateOwnerDTO;
import adopet.api.domain.owner.entity.Owner;
import adopet.api.domain.owner.service.OwnerService;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class OwnerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    JacksonTester<OwnerRegisterDTO> ownerRegisterJson;

    @Autowired
    JacksonTester<UpdateOwnerDTO> updateOwnerJson;

    @MockBean
    private OwnerService ownerService;

    @Test
    @DisplayName("It should return 200 http status code when receive valid data to register owner")
    void registerOwnerSuccess() throws Exception {
        OwnerRegisterDTO ownerData = ownerRegisterMock();

        var response = mvc.perform(post("/owners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ownerRegisterJson.write(
                        ownerData
                ).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("It should return 400 http status code when receive invalid data to register owner")
    void registerOwnerFailed() throws Exception {

        var response = mvc.perform(post("/owners")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("It should return 200 http status code when receive valid data to update owner")
    void updateOwnerSuccess() throws Exception {
        UpdateOwnerDTO ownerData = updateOwnerMock();

        var response = mvc.perform(put("/owners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateOwnerJson.write(
                        ownerData
                ).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("It should return 400 http status code when receive invalid data to update owner")
    void updateOwnerFailed() throws Exception {

        var response = mvc.perform(put("/owners")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    private UpdateOwnerDTO updateOwnerMock() {
        return new UpdateOwnerDTO(ownerMock().getId(), "John Wick", "21782378672", "email@email.com");
    }

    private Owner ownerMock() {
        Owner owner = new Owner(ownerRegisterMock());
        owner.setId(12l);
        return owner;
    }

    private OwnerRegisterDTO ownerRegisterMock() {
        return new OwnerRegisterDTO(
                "Some name",
                "11847384736",
                "someemail@email.com"
        );
    }
}