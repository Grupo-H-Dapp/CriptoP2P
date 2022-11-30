package ar.edu.unq.grupoh.criptop2p.service;

import ar.edu.unq.grupoh.criptop2p.dto.request.DateRangeRequest;
import ar.edu.unq.grupoh.criptop2p.dto.request.TransactionActionRequest;
import ar.edu.unq.grupoh.criptop2p.dto.request.TransactionRequest;
import ar.edu.unq.grupoh.criptop2p.dto.response.CryptoVolumn;
import ar.edu.unq.grupoh.criptop2p.dto.response.IntentionResponse;
import ar.edu.unq.grupoh.criptop2p.exceptions.*;
import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.StatesTransaction;
import ar.edu.unq.grupoh.criptop2p.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ar.edu.unq.grupoh.criptop2p.model.enums.IntentionStatus.ENDED;

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
    public Transaction processActionOperation(TransactionActionRequest transactionActionRequest) throws UserNotFoundException, TransactionNotFoundException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction, ExceedPriceDifference {
        User user = this.userService.getUserById(transactionActionRequest.getUserId());
        Transaction transaction1 = this.transactionRepository.findById(transactionActionRequest.getTransactionId()).orElseThrow(() -> new TransactionNotFoundException());
        transaction1.change(user, transactionActionRequest.getAction());
        return transactionRepository.save(transaction1);
    }

    @Transactional
    public List<CryptoVolumn> volumnOperatedCryptoBetween(DateRangeRequest dates, Integer userId){
        List<Intention> intentions;
        intentions = transactionRepository.findAll().stream().filter(transaction -> conditionMethod(transaction,userId,dates)).map(Transaction::getIntention).toList();
        return groupByCrypto(intentions);
    }

    private List<CryptoVolumn> groupByCrypto(List<Intention> intentions){
        HashMap<CriptosNames,CryptoVolumn> result = new HashMap<>();
        for(Intention i : intentions){
            if(! result.containsKey(i.getCrypto())){
                CryptoVolumn cv = new CryptoVolumn(i.getCrypto(),i.getPrice().doubleValue(),i.getQuantity(),i.getPriceARS());
                result.put(i.getCrypto(),cv);
            }else{
                CryptoVolumn value = result.get(i.getCrypto());
                value.setPriceTotalUSD(value.getPriceTotalUSD() + i.getPrice());
                value.setAmmount(value.getAmmount() + i.getQuantity());
                value.setPriceTotalARS(value.getPriceTotalARS() + i.getPriceARS());
                result.put(i.getCrypto(),value);
            }
        }
        return result.values().stream().toList();
    }

    private boolean conditionMethod(Transaction transaction , Integer userId , DateRangeRequest dates){
        if(transaction.getDateEnded() == null ){
            return false;
        }
        boolean betweenDates = dates.getStartDate().compareTo(transaction.getDateEnded()) <= 0 && dates.getEndDate().compareTo(transaction.getDateEnded()) >= 0;
        return transaction.getStateTransaction() == StatesTransaction.COMPLETED && transaction.getIntention().getUser().getUserId() == userId && betweenDates;
    }

    @Transactional
    public void deleteAll() {
        this.transactionRepository.deleteAll();
    }
}
