package ar.edu.unq.grupoh.criptop2p.webservice;

import ar.edu.unq.grupoh.criptop2p.dto.request.DateRangeRequest;
import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.service.CryptosService;
import ar.edu.unq.grupoh.criptop2p.webservice.aspects.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/crypto")
public class CryptosController {

    @Autowired
    private CryptosService cryptosService ;

    @GetMapping("/cryptocurrency")
    @LogExecutionTime
    public List<Cryptocurrency> allCriptos(){
        return cryptosService.findAll();
    }

    @GetMapping("/cryptocurrency/last")
    @LogExecutionTime
    public ResponseEntity<List<Cryptocurrency>> last(){
        try {
            List<Cryptocurrency> cryptoCurrencies = cryptosService.getLastCryptoCurrency();
            return ResponseEntity.status(HttpStatus.OK).body(cryptoCurrencies);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/cryptocurrency/{cryptoName}")
    @LogExecutionTime
    public ResponseEntity<Cryptocurrency> lastFor(@PathVariable("cryptoName") CriptosNames cryptoName){
        try {
            Cryptocurrency cryptoCurrency = cryptosService.findCryptoValueByName(cryptoName);
            return ResponseEntity.status(HttpStatus.OK).body(cryptoCurrency);
        }catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/cryptocurrency/{cryptoName}/24hs")
    @LogExecutionTime
    public ResponseEntity<List<Cryptocurrency>> cryptoLast24hours(@PathVariable("cryptoName") CriptosNames cryptoName){
        try {
            List<Cryptocurrency> cryptoCurrency = cryptosService.cryptoLast24hours(cryptoName);
            return ResponseEntity.status(HttpStatus.OK).body(cryptoCurrency);
        }catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/cryptocurrency/update")
    @LogExecutionTime
    public ResponseEntity<?> updateAllCryptos(){ //TODO este endpoint podria no ser necesario tenerlo
        try {
            cryptosService.updateAllCryptos();
            return ResponseEntity.status(HttpStatus.OK).body("Ok");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/cryptocurrency/{crypto}/between")
    @LogExecutionTime
    public ResponseEntity<List<Cryptocurrency>> cryptoCurrencyBetween(@PathVariable String crypto, @Valid @RequestBody DateRangeRequest dateRange){
        try {
            List<Cryptocurrency> cryptoCurrencies = cryptosService.cryptoBetween(crypto, dateRange.getStartDate(), dateRange.getEndDate());
            return ResponseEntity.status(HttpStatus.OK).body(cryptoCurrencies);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
