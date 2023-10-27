package adopet.api.domain.owner.entity;

import adopet.api.domain.adoption.entity.Adoption;
import adopet.api.domain.owner.dto.OwnerRegisterDTO;
import adopet.api.domain.owner.dto.UpdateOwnerDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
@NoArgsConstructor
@AllArgsConstructor
@Getter
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
