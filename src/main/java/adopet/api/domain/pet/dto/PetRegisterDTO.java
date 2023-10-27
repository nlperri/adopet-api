package adopet.api.domain.pet.dto;

import adopet.api.domain.pet.PetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PetRegisterDTO(
        @NotNull
        PetType type,

        @NotBlank
        String name,

        @NotBlank
        String breed,

        @NotNull
        Integer age,

        @NotBlank
        String color,

        @NotNull
        Float weight
) {
}
