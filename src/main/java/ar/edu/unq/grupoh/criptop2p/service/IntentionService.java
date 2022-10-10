package ar.edu.unq.grupoh.criptop2p.service;

import ar.edu.unq.grupoh.criptop2p.exceptions.IntentionException;
import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.repositories.IntentionRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ar.edu.unq.grupoh.criptop2p.model.enums.IntentionStatus.ACTIVE;

@Service
public class IntentionService {

    @Autowired
    private IntentionRepository intentionRepository;

    public List<Intention> findAll() {
        return this.intentionRepository.findAll();
    }

    public Intention findById(Long id) throws IntentionException {
       Optional<Intention> intention = this.intentionRepository.findById(id);
       if(intention.isEmpty()){
           throw new IntentionException("Intencion no encontrada");
       }
       return intention.get();
    }

    public List<Intention> findAllActive(){
        List<Intention> transactions = intentionRepository.findAll();
        transactions = transactions.stream().filter(transaction -> transaction.getStatus() == ACTIVE).collect(Collectors.toList());
        return transactions;
    }

    public Intention saveTransaction(Intention transaction) {
        return this.intentionRepository.save(transaction);
    }
}
