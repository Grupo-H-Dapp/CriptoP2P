package ar.edu.unq.grupoh.criptop2p.service;

import ar.edu.unq.grupoh.criptop2p.exceptions.TransactionException;
import ar.edu.unq.grupoh.criptop2p.exceptions.TransactionStatusException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserNotFoundException;
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

    public Transaction saveTransaction(Transaction transaction){
        return this.transactionRepository.save(transaction);
    }

    @Transactional
    public void processActionOperation(Action action, Integer usuario, Long transaction) throws UserNotFoundException, TransactionStatusException, TransactionException {
        User user = this.userService.getUserById(usuario);
        Transaction transaction1 = this.transactionRepository.findById(transaction).orElseThrow(() -> new TransactionException("The operation does not exist"));
        //Opcional hacerlo aca , se hace para obtener la ultima cotizacion de la crypto y validar la diff de precio
        Cryptocurrency cryptocurrency = this.cryptosService.getCryptoCurrency(transaction1.getCrypto());
        if (action == Action.CANCEL && (transaction1.getStateTransaction() != StatesTransaction.COMPLETED && transaction1.getStateTransaction() != StatesTransaction.CANCELED)){
            transaction1.setStateTransaction(StatesTransaction.CANCELED);
            user.substractPoints();
            transactionRepository.save(transaction1);
        }else {
            transaction1.getStateTransaction().onChange(user, transaction1, cryptocurrency, action);
            transactionRepository.save(transaction1);
        }
    }
}
