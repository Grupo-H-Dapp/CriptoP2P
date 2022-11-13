package ar.edu.unq.grupoh.criptop2p.webservice;

import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.enums.Action;
import ar.edu.unq.grupoh.criptop2p.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createOperation(@Valid @RequestBody Transaction transactionDto){
        try {
            Transaction transaction = transactionService.saveTransaction(transactionDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(transactionDto);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<?> doActionTransaction(@Valid @RequestBody TransactionActionRequestDto dto) {
        try {
            transactionService.processActionOperation(dto.getAction(), dto.getUserId(),dto.getIntentionId());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()  ;
        }
    }
}
