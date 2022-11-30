package ar.edu.unq.grupoh.criptop2p.service;

import ar.edu.unq.grupoh.criptop2p.dto.request.DateRangeRequest;
import ar.edu.unq.grupoh.criptop2p.dto.request.IntentionRequest;
import ar.edu.unq.grupoh.criptop2p.dto.response.IntentionResponse;
import ar.edu.unq.grupoh.criptop2p.exceptions.IntentionExceedPriceDifferenceException;
import ar.edu.unq.grupoh.criptop2p.exceptions.IntentionNotFoundException;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserNotFoundException;
import ar.edu.unq.grupoh.criptop2p.model.ApiDolar;
import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.repositories.IntentionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ar.edu.unq.grupoh.criptop2p.model.enums.IntentionStatus.ACTIVE;
import static ar.edu.unq.grupoh.criptop2p.model.enums.IntentionStatus.ENDED;

@Service
public class IntentionService {

    @Autowired
    private  IntentionRepository intentionRepository;
    @Autowired
    private  CryptosService cryptosService;
    @Autowired
    private  UserService userRepository;


    @Transactional
    public List<IntentionResponse> findAll() {
        return this.intentionRepository.findAll().stream().map(intention -> IntentionResponse.FromModel(intention)).toList();
    }

    @Transactional
    public Intention findById(Long id) throws IntentionNotFoundException {
       Optional<Intention> intention = this.intentionRepository.findById(id);
       if(intention.isEmpty()){
           throw new IntentionNotFoundException(id.intValue());
       }
       return intention.get();
    }

    @Transactional
    public List<IntentionResponse> findAllActive(){
        List<IntentionResponse> intentions;
        intentions = intentionRepository.findAll().stream().filter(intention -> intention.getStatus() == ACTIVE).map(intention -> IntentionResponse.FromModel(intention)).toList();
        return intentions;
    }

    @Transactional
    public List<IntentionResponse> volumnOperatedCryptoBetween(DateRangeRequest dates,Integer userId){
        List<IntentionResponse> intentions;
        intentions = intentionRepository.findAll().stream().filter(intention -> intention.getStatus() == ENDED && intention.getUser().getUserId() == userId).map(IntentionResponse::FromModel).toList();
        return intentions;
    }

    @Transactional
    public IntentionResponse saveIntention(IntentionRequest intentionRequest) throws IntentionExceedPriceDifferenceException, UserNotFoundException {
        Cryptocurrency cryptocurrency = cryptosService.getCryptoCurrency(intentionRequest.getCrypto());
        User user = userRepository.getUserById(intentionRequest.getUser());
        Float min = cryptocurrency.getPrice() * 0.95F ;
        Float max = cryptocurrency.getPrice() * 1.05F ;
        if (intentionRequest.getPrice() > min && intentionRequest.getPrice() < max) {
            Double priceARS = new BigDecimal(new ApiDolar().getUSDCotization().getVenta().doubleValue()).setScale(2, RoundingMode.HALF_UP).doubleValue();
            Intention intention = Intention.builder()
                                    .withPrice(intentionRequest.getPrice())
                                    .withPriceARS(priceARS * intentionRequest.getPrice())
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
    public IntentionResponse saveIntentionModel(Intention intention) throws IntentionExceedPriceDifferenceException, UserNotFoundException {
        Cryptocurrency cryptocurrency = cryptosService.getCryptoCurrency(intention.getCrypto());
        userRepository.getUserById(intention.getUser().getUserId());
        Float min = cryptocurrency.getPrice() * 0.95F ;
        Float max = cryptocurrency.getPrice() * 1.05F ;
        if (intention.getPrice() > min && intention.getPrice() < max) {
            Double valueArs = new BigDecimal(new ApiDolar().getUSDCotization().getVenta().doubleValue()).setScale(2, RoundingMode.HALF_UP).doubleValue();
            intention.setPriceARS(intention.getPrice() * valueArs);
            return IntentionResponse.FromModel(this.intentionRepository.save(intention));
        }
        throw new IntentionExceedPriceDifferenceException();
    }
    @Transactional
    public void deleteAllIntentions() {
        this.intentionRepository.deleteAll();
    }
}
