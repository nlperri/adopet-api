package adopet.api.domain.owner.repository;

import adopet.api.domain.owner.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    boolean existsByPhoneOrEmail(String phone, String email);
}
