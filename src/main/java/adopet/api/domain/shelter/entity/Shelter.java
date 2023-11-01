package adopet.api.domain.shelter.entity;

import adopet.api.domain.shelter.dto.ShelterRegisterDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

@Entity
@Table(name = "shelters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    private String email;

    public Shelter(ShelterRegisterDTO data) {
        this.id = Optional.ofNullable(id).orElse(null);
        this.name = data.name();
        this.phone = data.phone();
        this.email = data.email();
    }
}
