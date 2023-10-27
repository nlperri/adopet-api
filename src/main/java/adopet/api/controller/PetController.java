package adopet.api.controller;

import adopet.api.domain.pet.dto.PetDTO;
import adopet.api.domain.pet.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping
    public ResponseEntity<List<PetDTO>> fetchAllAvailable() {
        List<PetDTO> pets = petService.searchAvailable();
        return ResponseEntity.ok(pets);
    }
}
