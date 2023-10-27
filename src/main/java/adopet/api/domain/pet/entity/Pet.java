package adopet.api.domain.pet.entity;

import adopet.api.domain.adoption.entity.Adoption;
import adopet.api.domain.pet.PetType;
import adopet.api.domain.pet.dto.PetRegisterDTO;
import adopet.api.domain.shelter.entity.Shelter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pets")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PetType type;

    private String name;

    private String breed;

    private Integer age;

    private String color;

    private Float weight;

    private Boolean isAdopted;

    @ManyToOne(fetch = FetchType.LAZY)
    private Shelter shelter;

    @OneToOne(mappedBy = "pet", fetch = FetchType.LAZY)
    private Adoption adoption;

    public Pet(PetRegisterDTO data, Shelter shelter) {
        this.type = data.type();
        this.name = data.name();
        this.breed = data.breed();
        this.age = data.age();
        this.color = data.color();
        this.weight = data.weight();
        this.shelter = shelter;
        this.isAdopted = false;
    }
}
