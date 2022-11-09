package ar.edu.unq.grupoh.criptop2p.service;

import ar.edu.unq.grupoh.criptop2p.exceptions.UserNotFoundException;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.Action;
import ar.edu.unq.grupoh.criptop2p.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;
    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * TRANSFER_MONEY,CONFIRM_MONEY,TRANSFER_CRYPTO,CONFIRM_CRYPTO,CANCEL
     {
        operation:TRANFER,
        user: 1,
        transaction: 1
     }
     */
    public void procesarActionOperation(Action action, Integer usuario, Long transaction) throws UserNotFoundException {

        User user = this.userService.getUserById(usuario);
        Optional<Transaction> transaction1 = this.transactionRepository.findById(transaction);

        transaction1.get().changeState(action,user);

    }
}
