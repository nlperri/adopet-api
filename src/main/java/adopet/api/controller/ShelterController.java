package adopet.api.controller;

import adopet.api.domain._exception.ValidatorException;
import adopet.api.domain.pet.dto.PetDTO;
import adopet.api.domain.pet.dto.PetRegisterDTO;
import adopet.api.domain.pet.service.PetService;
import adopet.api.domain.shelter.dto.ShelterDTO;
import adopet.api.domain.shelter.dto.ShelterRegisterDTO;
import adopet.api.domain.shelter.entity.Shelter;
import adopet.api.domain.shelter.service.ShelterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shelters")
public class ShelterController {

    @Autowired
    private ShelterService shelterService;

    @Autowired
    private PetService petService;

    @GetMapping
    public ResponseEntity<List<ShelterDTO>> fetch() {
        List<ShelterDTO> shelters = shelterService.list();
        return ResponseEntity.ok(shelters);
    }

    @GetMapping("/{idOrName}/pets")
    public ResponseEntity<List<PetDTO>> fetchPets(@PathVariable String idOrName) {
        List<PetDTO> shelterPets = shelterService.listShelterPets(idOrName);
        return ResponseEntity.ok(shelterPets);
     }

    @PostMapping
    @Transactional
    public ResponseEntity<String> register(@RequestBody @Valid ShelterRegisterDTO data) {
        shelterService.register(data);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idOrName}/pets")
    @Transactional
    public ResponseEntity<String> registerPet(@PathVariable String idOrName, @RequestBody @Valid PetRegisterDTO data){
        Shelter shelter = shelterService.findShelter(idOrName);
        petService.register(shelter, data);
        return ResponseEntity.ok().build();
    }
}
