package adopet.api.domain.adoption.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DisapprovedAdoptionDTO(
        @NotNull
        Long adoptionId,

        @NotBlank
        String justification
) {
}
