package ar.edu.unq.grupoh.criptop2p.webservice;

import ar.edu.unq.grupoh.criptop2p.dto.request.DateRangeRequest;
import ar.edu.unq.grupoh.criptop2p.dto.request.TransactionActionRequest;
import ar.edu.unq.grupoh.criptop2p.dto.request.TransactionRequest;
import ar.edu.unq.grupoh.criptop2p.dto.response.CryptoVolumn;
import ar.edu.unq.grupoh.criptop2p.dto.response.TransactionResponse;
import ar.edu.unq.grupoh.criptop2p.exceptions.*;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.service.TransactionService;
import ar.edu.unq.grupoh.criptop2p.webservice.aspects.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    @LogExecutionTime
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest transactionRequest){
        try {
            Transaction transaction = transactionService.createTransaction(transactionRequest);
            TransactionResponse response = new TransactionResponse(transaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    @LogExecutionTime
    public ResponseEntity<List<TransactionResponse>> getAll(){
        try{
            List<Transaction> result = transactionService.getAll();
            List<TransactionResponse> mapped = result.stream().map(TransactionResponse::new).toList();
            return ResponseEntity.status(HttpStatus.CREATED).body(mapped);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("volumen/{userId}")
    @LogExecutionTime
    public ResponseEntity<List<CryptoVolumn>> volumnOperatedCryptoBetween(@Valid @RequestBody DateRangeRequest dates, @PathVariable Integer userId){
        return new ResponseEntity<>(transactionService.volumnOperatedCryptoBetween(dates,userId),HttpStatus.OK);
    }

    @PutMapping
    @LogExecutionTime
    public ResponseEntity<String> doActionTransaction(@Valid @RequestBody TransactionActionRequest transactionActionRequest) throws  UserNotFoundException, TransactionStatusException, TransactionNotFoundException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction, ExceedPriceDifference {
            transactionService.processActionOperation(transactionActionRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Action Executed Successful");
    }


}
