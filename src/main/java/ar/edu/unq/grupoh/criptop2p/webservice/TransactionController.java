package ar.edu.unq.grupoh.criptop2p.webservice;

import ar.edu.unq.grupoh.criptop2p.dto.request.TransactionActionRequest;
import ar.edu.unq.grupoh.criptop2p.exceptions.*;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.service.TransactionService;
import ar.edu.unq.grupoh.criptop2p.webservice.aspects.LogExecutionTime;
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
    @LogExecutionTime
    public ResponseEntity<Transaction> createOperation(@Valid @RequestBody Transaction transactionDto, @RequestHeader (name="Authorization") String token){
        try {
            System.out.println(token);
            Transaction transaction = transactionService.saveTransaction(transactionDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(transactionDto);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    @LogExecutionTime
    public ResponseEntity<?> doActionTransaction(@Valid @RequestBody TransactionActionRequest dto) throws ExceedPriceDifference, UserNotFoundException, TransactionException, TransactionStatusException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction {
            transactionService.processActionOperation(dto.getAction(), dto.getUserId(),dto.getIntentionId());
            return ResponseEntity.status(HttpStatus.OK).build();
    }
}
