package ar.edu.unq.grupoh.criptop2p.webservice;

import ar.edu.unq.grupoh.criptop2p.dto.request.IntentionRequest;
import ar.edu.unq.grupoh.criptop2p.dto.response.IntentionResponse;
import ar.edu.unq.grupoh.criptop2p.exceptions.IntentionException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserNotFoundException;
import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.service.IntentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/intentions")
public class IntentionController {

    @Autowired
    private IntentionService intentionService;

    //ModelMapper modelMapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<Intention>> getAll(){
        List<Intention> activeTransactions = this.intentionService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(activeTransactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IntentionResponse> findById(@PathVariable Long id) throws IntentionException {
        return ResponseEntity.status(HttpStatus.OK).body(this.intentionService.findById(id));
    }

    @GetMapping("actives")
    public ResponseEntity<List<Intention>> findActives(){
        List<Intention> activeTransactions = this.intentionService.findAllActive();
        return ResponseEntity.status(HttpStatus.OK).body(activeTransactions);
    }

    @PostMapping
    public ResponseEntity<?> postIntention(@Valid @RequestBody IntentionRequest intentionRequest) throws IntentionException, UserNotFoundException {
        IntentionResponse transaction = intentionService.saveIntention(intentionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }
}
