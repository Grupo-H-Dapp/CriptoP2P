package ar.edu.unq.grupoh.criptop2p.service;

import ar.edu.unq.grupoh.criptop2p.dto.request.TransactionActionRequest;
import ar.edu.unq.grupoh.criptop2p.dto.request.TransactionRequest;
import ar.edu.unq.grupoh.criptop2p.exceptions.*;
import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;
    @Autowired
    private CryptosService cryptosService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private IntentionService intentionService;

    @Transactional
    public Transaction saveTransaction(Transaction transaction){
        return this.transactionRepository.save(transaction);
    }

    @Transactional
    public List<Transaction> getAll(){
        return transactionRepository.findAll();
    }

    @Transactional
    public Transaction createTransaction(TransactionRequest transactionRequest) throws IntentionNotFoundException, UserNotFoundException {
        Intention intention = intentionService.findById(transactionRequest.getIntentionId());
        User user = userService.getUserById(transactionRequest.getUser());
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(user).build();
        return this.transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction processActionOperation(TransactionActionRequest transactionActionRequest) throws UserNotFoundException, TransactionStatusException, TransactionNotFoundException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction, ExceedPriceDifference {
        User user = this.userService.getUserById(transactionActionRequest.getUserId());
        Transaction transaction1 = this.transactionRepository.findById(transactionActionRequest.getTransactionId()).orElseThrow(() -> new TransactionNotFoundException());
        transaction1.change(user, transactionActionRequest.getAction());
        return transactionRepository.save(transaction1);
    }

    @Transactional
    public void deleteAll() {
        this.transactionRepository.deleteAll();
    }
}
