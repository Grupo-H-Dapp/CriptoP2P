package ar.edu.unq.grupoh.criptop2p.webservice;

import ar.edu.unq.grupoh.criptop2p.dto.IntentionDTO;
import ar.edu.unq.grupoh.criptop2p.exceptions.IntentionException;
import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.service.IntentionService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/intentions")
public class IntentionController {

    @Autowired
    private IntentionService intentionService;

    @GetMapping
    public ResponseEntity<List<Intention>> getAll(){
        List<Intention> activeTransactions = this.intentionService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(activeTransactions);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<Intention> findById(@PathVariable Long id) throws IntentionException {
        Intention transaction = this.intentionService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(transaction);
    }

    @GetMapping("/actives")
    public ResponseEntity<List<Intention>> findActives(){
        List<Intention> activeTransactions = this.intentionService.findAllActive();
        return ResponseEntity.status(HttpStatus.OK).body(activeTransactions);
    }

    @PostMapping
    public ResponseEntity<?> postIntention(@Valid @RequestBody Intention intentionDTO){
        Intention transaction = intentionService.saveIntention(intentionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }
}
