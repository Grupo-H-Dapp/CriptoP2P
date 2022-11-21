package ar.edu.unq.grupoh.criptop2p.service;

import ar.edu.unq.grupoh.criptop2p.dto.request.IntentionRequest;
import ar.edu.unq.grupoh.criptop2p.dto.response.IntentionResponse;
import ar.edu.unq.grupoh.criptop2p.exceptions.IntentionExceedPriceDifferenceException;
import ar.edu.unq.grupoh.criptop2p.exceptions.IntentionNotFoundException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserNotFoundException;
import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.repositories.IntentionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ar.edu.unq.grupoh.criptop2p.model.enums.IntentionStatus.ACTIVE;

@Service
public class IntentionService {

    @Autowired
    private  IntentionRepository intentionRepository;
    @Autowired
    private  CryptosService cryptosService;
    @Autowired
    private  UserService userRepository;


    @Transactional
    public List<Intention> findAll() {
        return this.intentionRepository.findAll();
    }

    @Transactional
    public IntentionResponse findById(Long id) throws IntentionNotFoundException {
       Optional<Intention> intention = this.intentionRepository.findById(id);
       if(intention.isEmpty()){
           throw new IntentionNotFoundException(id.intValue());
       }
       return IntentionResponse.FromModel(intention.get());
    }

    @Transactional
    public List<IntentionResponse> findAllActive(){
        List<IntentionResponse> intentions;
        intentions = intentionRepository.findAll().stream().filter(intention -> intention.getStatus() == ACTIVE).map(intention -> IntentionResponse.FromModel(intention)).collect(Collectors.toList());
        return intentions;
    }
    @Transactional
    public IntentionResponse saveIntention(IntentionRequest intentionRequest) throws IntentionExceedPriceDifferenceException, UserNotFoundException {
        Cryptocurrency cryptocurrency = cryptosService.getCryptoCurrency(intentionRequest.getCrypto());
        User user = userRepository.getUserById(intentionRequest.getUser());
        Float min = cryptocurrency.getPrice() * 0.95F ;
        Float max = cryptocurrency.getPrice() * 1.05F ;
        if (intentionRequest.getPrice() > min && intentionRequest.getPrice() < max) {
//            Float cotizationUSDToARS = this.cryptosService.getUSDCotization().getVenta();
            Intention intention = Intention.builder() //usar el intentionRequest.getPrice() * cotizationUSDToARS.doubleValue() para el valor en pesos
                                    .withPrice(intentionRequest.getPrice())
                                    .withUser(user)
                                    .withQuantity(intentionRequest.getAmount())
                                    .withTypeOperation(intentionRequest.getTypeIntention())
                                    .withCryptoCurrency(intentionRequest.getCrypto())
                                    .build();
            return IntentionResponse.FromModel(this.intentionRepository.save(intention));
        }
        throw new IntentionExceedPriceDifferenceException();
    }
    @Transactional
    public IntentionResponse saveIntentionModel(Intention intentionRequest) throws IntentionExceedPriceDifferenceException, UserNotFoundException {
        Cryptocurrency cryptocurrency = cryptosService.getCryptoCurrency(intentionRequest.getCrypto());
        User user = userRepository.getUserById(intentionRequest.getUser().getUserId());
        Float min = cryptocurrency.getPrice() * 0.95F ;
        Float max = cryptocurrency.getPrice() * 1.05F ;
        if (intentionRequest.getPrice() > min && intentionRequest.getPrice() < max) {
            return IntentionResponse.FromModel(this.intentionRepository.save(intentionRequest));
        }
        throw new IntentionExceedPriceDifferenceException();
    }

    public void deleteAllIntentions() {
        this.intentionRepository.deleteAll();
    }
}
