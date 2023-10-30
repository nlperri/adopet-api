package adopet.api.domain.adoption;

import adopet.api.domain.pet.PetType;
import adopet.api.domain.pet.dto.PetRegisterDTO;
import adopet.api.domain.pet.entity.Pet;
import adopet.api.domain.shelter.dto.ShelterRegisterDTO;
import adopet.api.domain.shelter.entity.Shelter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdoptionProbabilityCalculatorTest {

    @Test
    @DisplayName("It should return high probability when pet has low age and weight")
    void highProbability() {

        Shelter shelter = new Shelter(new ShelterRegisterDTO(
                "Happy Shelter",
                "9423782378",
                "happyshelter@email.com"
        ));

        Pet pet = new Pet(new PetRegisterDTO(
                PetType.CAT,
                "Lua",
                "Siames",
                4,
                "Preto",
                4.0f
        ), shelter);

        AdoptionProbabilityCalculator calculator = new AdoptionProbabilityCalculator();
        AdoptionProbability probability = calculator.calculate(pet);

        Assertions.assertEquals(AdoptionProbability.HIGH, probability);
    }

    @Test
    @DisplayName("It should return medium probability when pet has low weight and high age")
    void mediumProbability() {
        Shelter shelter = new Shelter(new ShelterRegisterDTO(
                "Happy Shelter",
                "9423782378",
                "happyshelter@email.com"
        ));

        Pet pet = new Pet(new PetRegisterDTO(
                PetType.CAT,
                "Lua",
                "Siames",
                15,
                "Preto",
                8.0f
        ), shelter);

        AdoptionProbabilityCalculator calculator = new AdoptionProbabilityCalculator();
        AdoptionProbability probability = calculator.calculate(pet);

        Assertions.assertEquals(AdoptionProbability.MEDIUM, probability);
    }



}