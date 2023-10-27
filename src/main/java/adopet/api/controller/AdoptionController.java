package adopet.api.controller;

import adopet.api.domain.adoption.dto.AdoptionRequestDTO;
import adopet.api.domain.adoption.dto.ApprovedAdoptionDTO;
import adopet.api.domain.adoption.dto.DisapprovedAdoptionDTO;
import adopet.api.domain.adoption.service.AdoptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adoptions")
public class AdoptionController {

    @Autowired
    private AdoptionService adoptionService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> request(@RequestBody @Valid AdoptionRequestDTO data) {
        this.adoptionService.request(data);
        return ResponseEntity.ok("Adoção solicitada com sucesso.");
    }

    @PutMapping("/approval")
    @Transactional
    public ResponseEntity<String> approval(@RequestBody @Valid ApprovedAdoptionDTO data) {
        this.adoptionService.approve(data);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/disapproval")
    @Transactional
    public ResponseEntity<String> disapproval(@RequestBody @Valid DisapprovedAdoptionDTO data) {
        adoptionService.disapprove(data);
        return ResponseEntity.ok().build();
    }
}
