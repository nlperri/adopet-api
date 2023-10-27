package adopet.api.domain.adoption.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdoptionRequestDTO (
        @NotNull
        Long petId,

        @NotNull
        Long ownerId,

        @NotBlank
        String motive
){
}
