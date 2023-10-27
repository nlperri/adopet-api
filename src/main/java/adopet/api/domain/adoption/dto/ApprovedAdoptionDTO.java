package adopet.api.domain.adoption.dto;

import jakarta.validation.constraints.NotNull;

public record ApprovedAdoptionDTO(
        @NotNull
        Long adoptionId
) {
}
