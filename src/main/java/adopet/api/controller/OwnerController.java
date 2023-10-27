package adopet.api.controller;

import adopet.api.domain.owner.dto.OwnerRegisterDTO;
import adopet.api.domain.owner.dto.UpdateOwnerDTO;
import adopet.api.domain.owner.service.OwnerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> register(@RequestBody @Valid OwnerRegisterDTO data) {
        ownerService.register(data);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> update(@RequestBody @Valid UpdateOwnerDTO data) {
        ownerService.update(data);
        return ResponseEntity.ok().build();
    }
}
