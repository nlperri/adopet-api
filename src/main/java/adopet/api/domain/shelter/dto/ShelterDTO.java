package adopet.api.domain.shelter.dto;

import adopet.api.domain.shelter.entity.Shelter;

public record ShelterDTO(
        Long id,
        String name
) {
    public ShelterDTO(Shelter shelter) {
        this(shelter.getId(), shelter.getName());
    }
}
