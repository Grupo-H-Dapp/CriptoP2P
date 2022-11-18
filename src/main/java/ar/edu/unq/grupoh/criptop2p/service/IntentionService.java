package ar.edu.unq.grupoh.criptop2p.service;

import ar.edu.unq.grupoh.criptop2p.dto.request.IntentionRequest;
import ar.edu.unq.grupoh.criptop2p.dto.response.IntentionResponse;
import ar.edu.unq.grupoh.criptop2p.exceptions.IntentionException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserNotFoundException;
import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.repositories.IntentionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ar.edu.unq.grupoh.criptop2p.model.enums.IntentionStatus.ACTIVE;

@Service
public class IntentionService {

    private final IntentionRepository intentionRepository;
    private final CryptosService cryptosService;
    private final UserService userRepository;

    @Autowired
    public IntentionService(IntentionRepository intentionRepository, CryptosService cryptosService, UserService userRepository) {
        this.intentionRepository = intentionRepository;
        this.cryptosService = cryptosService;
        this.userRepository = userRepository;
    }

    public List<Intention> findAll() {
        return this.intentionRepository.findAll();
    }

    public IntentionResponse findById(Long id) throws IntentionException {
       Optional<Intention> intention = this.intentionRepository.findById(id);
       if(intention.isEmpty()){
           throw new IntentionException("Intencion no encontrada");
       }
       return IntentionResponse.FromModel(intention.get());
    }

    public List<Intention> findAllActive(){
        List<Intention> transactions = intentionRepository.findAll();
        transactions = transactions.stream().filter(transaction -> transaction.getStatus() == ACTIVE).collect(Collectors.toList());
        return transactions;
    }

    public IntentionResponse saveIntention(IntentionRequest intentionRequest) throws IntentionException, UserNotFoundException {
        Cryptocurrency cryptocurrency = cryptosService.getCryptoCurrency(intentionRequest.getCrypto());
        User user = userRepository.getUserById(intentionRequest.getUser());
        Float min = cryptocurrency.getPrice() * 0.95F ;
        Float max = cryptocurrency.getPrice() * 1.05F ;
        if (intentionRequest.getPrice() > min && intentionRequest.getPrice() < max) {
            Float cotizationUSDToARS = this.cryptosService.getUSDCotization().getVenta();
            Intention intention = Intention.builder() //usar el intentionRequest.getPrice() * cotizationUSDToARS.doubleValue() para el valor en pesos
                                    .withPrice(intentionRequest.getPrice())
                                    .withUser(user)
                                    .withQuantity(intentionRequest.getAmount())
                                    .withTypeOperation(intentionRequest.getTypeIntention())
                                    .withCryptoCurrency(intentionRequest.getCrypto())
                                    .build();
            return IntentionResponse.FromModel(this.intentionRepository.save(intention));
        }
        throw new IntentionException("Price exceed difference 5%");
    }

    public IntentionResponse saveIntentionModel(Intention intentionRequest) throws IntentionException, UserNotFoundException {
        Cryptocurrency cryptocurrency = cryptosService.getCryptoCurrency(intentionRequest.getCrypto());
        User user = userRepository.getUserById(intentionRequest.getUser().getUserId());
        Float min = cryptocurrency.getPrice() * 0.95F ;
        Float max = cryptocurrency.getPrice() * 1.05F ;
        if (intentionRequest.getPrice() > min && intentionRequest.getPrice() < max) {
            return IntentionResponse.FromModel(this.intentionRepository.save(intentionRequest));
        }
        throw new IntentionException("Price exceed difference 5%");
    }
}
