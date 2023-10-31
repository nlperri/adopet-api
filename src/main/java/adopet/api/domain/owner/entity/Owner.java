package adopet.api.domain.owner.entity;

import adopet.api.domain.adoption.entity.Adoption;
import adopet.api.domain.owner.dto.OwnerRegisterDTO;
import adopet.api.domain.owner.dto.UpdateOwnerDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "owners")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    private String email;

    @OneToMany(mappedBy = "owner")
    private List<Adoption> adoptions = new ArrayList<>();

    public Owner(OwnerRegisterDTO data) {
        this.id = Optional.ofNullable(id).orElse(null);
        this.name = data.name();
        this.phone = data.phone();
        this.email = data.email();
    }

    public void update(UpdateOwnerDTO data) {
        this.name = data.name();
        this.phone = data.phone();
        this.email = data.email();
    }


}
