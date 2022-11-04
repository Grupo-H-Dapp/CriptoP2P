package ar.edu.unq.grupoh.criptop2p.test.service;
import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.service.CryptosService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CryptosServiceTest {

    @Autowired
    private CryptosService cryptosService;

    @BeforeAll
    void setUp() {
        this.cryptosService.updateAllCryptos();
    }
    @Test
    void getAllCryptos() {
        List<Cryptocurrency> cryptos = this.cryptosService.findAll();
        assertEquals(13, cryptos.size());
    }

    @Test
    void getCryptoALICEUSDT() {
        Cryptocurrency cryptoByName = this.cryptosService.findCryptoValueByName(CriptosNames.ALICEUSDT);
        assertEquals(CriptosNames.ALICEUSDT, cryptoByName.getCrypto());
    }

    @Test
    void getCryptoALICEUSDTLast24hours() {
        List<Cryptocurrency> cryptos = this.cryptosService.cryptoLast24hours(CriptosNames.ALICEUSDT);
        assertEquals(1, cryptos.size());
        assertEquals(CriptosNames.ALICEUSDT, cryptos.get(0).getCrypto());
    }

    @Test
    void getCryptoBetweenTwoDates() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime yesterday = today.minusHours(24);
        List<Cryptocurrency> cryptos = this.cryptosService.cryptoBetween(CriptosNames.ALICEUSDT.toString(), yesterday, today);
        assertEquals(1, cryptos.size());
        assertEquals(CriptosNames.ALICEUSDT, cryptos.get(0).getCrypto());
    }

}
