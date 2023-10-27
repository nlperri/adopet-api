package adopet.api.domain.pet.dto;

import adopet.api.domain.pet.entity.Pet;
import adopet.api.domain.pet.PetType;

public record PetDTO(
        Long id,
        PetType type,
        String name,
        String breed,
        Integer age
) {
    public PetDTO(Pet pet) {
        this(pet.getId(), pet.getType(), pet.getName(), pet.getBreed(), pet.getAge());
    }
}
