package ar.edu.unq.grupoh.criptop2p.service;

import ar.edu.unq.grupoh.criptop2p.exceptions.*;
import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.Action;
import ar.edu.unq.grupoh.criptop2p.model.enums.StatesTransaction;
import ar.edu.unq.grupoh.criptop2p.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;
    @Autowired
    private CryptosService cryptosService;
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public Transaction saveTransaction(Transaction transaction){
        return this.transactionRepository.save(transaction);
    }

    @Transactional
    public void processActionOperation(Action action, Integer usuario, Long transaction) throws UserNotFoundException, TransactionStatusException, TransactionException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction, ExceedPriceDifference {
        User user = this.userService.getUserById(usuario);
        Transaction transaction1 = this.transactionRepository.findById(transaction).orElseThrow(() -> new TransactionException("The operation does not exist"));
        if(transaction1.getStateTransaction() == StatesTransaction.COMPLETED || transaction1.getStateTransaction() == StatesTransaction.CANCELED ){
            String message = String.format("La Transaccion esta en en el estado: %s",transaction1.getStateTransaction());
            throw new TransactionStatusException(message);
        }
        try {
            transaction1.change(user, action);
        } finally {
            transactionRepository.save(transaction1);
        }
    }
}
