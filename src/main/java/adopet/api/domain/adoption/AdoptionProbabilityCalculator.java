package adopet.api.domain.adoption;

import adopet.api.domain.pet.entity.Pet;
import adopet.api.domain.pet.PetType;

public class AdoptionProbabilityCalculator {

    public AdoptionProbability calculate(Pet pet) {
        int note = calculateNote(pet);

        if (note >= 8) {
            return AdoptionProbability.HIGH;
        }

        if( note >= 5) {
            return AdoptionProbability.MEDIUM;
        }

        return AdoptionProbability.LOW;
    }

    private int calculateNote(Pet pet) {
        int weight = pet.getWeight().intValue();
        int age = pet.getAge();
        PetType type = pet.getType();

        int note = 10;

        if(type == PetType.DOG && weight > 15) {
            note -= 2;
        }

        if(type == PetType.CAT && weight > 10) {
            note -= 2;
        }

        if (age >= 15) {
           return note -= 5;
        }

        if (age >= 10) {
            return note -= 4;
        }

        return note;
    }
}
