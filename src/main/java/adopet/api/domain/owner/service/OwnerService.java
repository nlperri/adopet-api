package adopet.api.domain.owner.service;

import adopet.api.domain._exception.ValidatorException;
import adopet.api.domain.owner.dto.OwnerRegisterDTO;
import adopet.api.domain.owner.dto.UpdateOwnerDTO;
import adopet.api.domain.owner.entity.Owner;
import adopet.api.domain.owner.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public void register(OwnerRegisterDTO data) {
        boolean isRegistered = ownerRepository.existsByPhoneOrEmail(data.phone(), data.email());

        if(isRegistered) {
            throw new ValidatorException("Dados já cadastrados para outro tutor.");
        }

        ownerRepository.save(new Owner(data));
    }

    public void update(UpdateOwnerDTO data) {
        Owner owner = ownerRepository.getReferenceById(data.id());
        owner.update(data);
    }
}
