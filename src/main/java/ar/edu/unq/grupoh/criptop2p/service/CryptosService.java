package ar.edu.unq.grupoh.criptop2p.service;

import ar.edu.unq.grupoh.criptop2p.exceptions.CryptoException;
import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.ApiBinance;
import ar.edu.unq.grupoh.criptop2p.model.ApiDolar;
import ar.edu.unq.grupoh.criptop2p.repositories.CryptoRepository;
import ar.edu.unq.grupoh.criptop2p.service.response.BinanceResponse;
import ar.edu.unq.grupoh.criptop2p.service.response.CotizationUSDToARS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class CryptosService {

    @Autowired
    private CryptoRepository cryptoCurrencyRepository;
    private final ApiBinance apiBinance = new ApiBinance();
    private final ApiDolar apiDolar = new ApiDolar();

    @Transactional
    public List<Cryptocurrency> findAll() {
        return cryptoCurrencyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Cryptocurrency findCryptoValueByName(CriptosNames cryptoName) {
        List<Cryptocurrency> cryptos = cryptoCurrencyRepository.findAll();
        return cryptos
                .stream()
                .filter(crypto -> crypto.getCrypto() == cryptoName)
                .collect(Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Cryptocurrency::getDate)), Optional::get));
    }

    @Transactional
    public List<Cryptocurrency> cryptoLast24hours(CriptosNames cryptoName) {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start2 = end.minusHours(24) ;//Para que sea cada 24hr
        List<Cryptocurrency> cryptos = findByCrypto(cryptoName)
                .stream()
                .filter(cryptoCurrency -> cryptoCurrency.getDate().isBefore(ChronoLocalDateTime.from(end)) && cryptoCurrency.getDate().isAfter(ChronoLocalDateTime.from(start2)))
                .collect(Collectors.toList());
        return cryptos;
    }

    @Transactional
    public List<Cryptocurrency> cryptoBetween(String cryptoName, LocalDateTime start, LocalDateTime end) {
        CriptosNames crypto = CriptosNames.valueOf(cryptoName);
        List<Cryptocurrency> cryptos = findByCrypto(crypto)
                .stream()
                .filter(cryptoCurrency -> cryptoCurrency.getDate().isBefore(ChronoLocalDateTime.from(end)) && cryptoCurrency.getDate().isAfter(ChronoLocalDateTime.from(start)))
                .collect(Collectors.toList());
        return cryptos;
    }

    @Transactional
    public Cryptocurrency getCryptoCurrency(CriptosNames cryptoName) {
        Float binancePrice = this.getBinanceResponse(cryptoName).getPrice();
//        Float usdPrice = this.getUSDResponse().getVenta();
//        Float price = binancePrice * usdPrice;
        return new Cryptocurrency(cryptoName, binancePrice);
    }

    @Transactional
    @Cacheable(value = "crypto")
    public List<Cryptocurrency> getLastCryptoCurrency() {
        List<CriptosNames> cryptoNames = Arrays.asList(CriptosNames.values());
        return cryptoNames
                .stream()
                .map (crypto -> Collections.max(findByCrypto(crypto), Comparator.comparing(Cryptocurrency::getDate)))
                .collect(Collectors.toList()) ;
    }


    @Transactional
    @Scheduled(cron = "0 0/10 * * * *") // cron = "0 0/10 * * * *" para que sea cada 10m
    public void updateAllCryptos() {
        BinanceResponse[] binanceCryptoDTOS = getAllCryptoPrice(List.of(CriptosNames.values()));
        Arrays.stream(binanceCryptoDTOS).forEach(binanceCrypto -> {
            try {
                Cryptocurrency crypto = binanceToModel(binanceCrypto);
                cryptoCurrencyRepository.save(crypto);
            } catch (CryptoException e) {

            }
        });
    }

    private BinanceResponse getBinanceResponse(CriptosNames cryptoName) {
            return this.apiBinance.getBinanceResponse(cryptoName);
    }
    private CotizationUSDToARS getUSDCotization() {
            return apiDolar.getUSDCotization();
    }

    private List<Cryptocurrency> findByCrypto(CriptosNames cryptoName){
        List<Cryptocurrency> cryptos = cryptoCurrencyRepository.findAll();
        return cryptos.stream().filter(cryptoCurrency -> cryptoCurrency.getCrypto() == cryptoName).collect(Collectors.toList());
    }

    private Cryptocurrency binanceToModel(BinanceResponse binanceCryptoDTO) throws CryptoException {
        Cryptocurrency cryptoCurrency = Cryptocurrency.builder()
                .withCryptoCurrency(binanceCryptoDTO.getSymbol())
                .withPrice(binanceCryptoDTO.getPrice())
                .withDate(LocalDateTime.now())
                .build();
        return cryptoCurrency;
    }

    private BinanceResponse[] getAllCryptoPrice(List<CriptosNames> cryptoNameList) {
        RestTemplate restTemplate = new RestTemplate();
        String cryptoSymbols = cryptoNameList.stream()
                .map(crypto -> '\"' + crypto.name() + '\"')
                .collect(Collectors.joining(",", "[", "]"));

        return restTemplate.getForObject("https://api1.binance.com/api/v3/ticker/price?symbols=" + cryptoSymbols, BinanceResponse[].class);
    }

}
