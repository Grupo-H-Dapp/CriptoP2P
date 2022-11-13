package ar.edu.unq.grupoh.criptop2p.service;

import ar.edu.unq.grupoh.criptop2p.exceptions.TransactionException;
import ar.edu.unq.grupoh.criptop2p.exceptions.TransactionStatusException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserNotFoundException;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.Action;
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
    private TransactionRepository transactionRepository;

    public Transaction saveTransaction(Transaction transaction){
        return this.transactionRepository.save(transaction);
    }

    @Transactional
    public void processActionOperation(Action action, Integer usuario, Long transaction) throws UserNotFoundException, TransactionStatusException, TransactionException {
        User user = this.userService.getUserById(usuario);
        Transaction transaction1 = this.transactionRepository.findById(transaction).orElseThrow(() -> new TransactionException("The operation does not exist"));;
        transaction1.changeState(action,user);

    }
}
