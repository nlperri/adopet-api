package adopet.api.domain.adoption.entity;

import adopet.api.domain.adoption.StatusAdoption;
import adopet.api.domain.owner.entity.Owner;
import adopet.api.domain.pet.entity.Pet;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "adoptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    private Owner owner;

    @OneToOne(fetch = FetchType.LAZY)
    private Pet pet;

    private String motive;

    @Enumerated(EnumType.STRING)
    private StatusAdoption status;

    private String statusJustification;

    public Adoption(Owner owner, Pet pet, String motive) {
        this.id = Optional.ofNullable(id).orElse(null);
        this.owner = owner;
        this.pet = pet;
        this.motive = motive;
        this.status = StatusAdoption.WAITING_EVALUATION;
        this.date = LocalDateTime.now();
    }

    public void markAsApproved() {
        this.status = StatusAdoption.APPROVED;
    }

    public void markAsDisapproved(String justification) {
        this.status = StatusAdoption.FAILED;
        this.statusJustification = justification;
    }


}
