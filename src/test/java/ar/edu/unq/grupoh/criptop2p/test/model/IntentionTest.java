package ar.edu.unq.grupoh.criptop2p.test.model;


import ar.edu.unq.grupoh.criptop2p.exceptions.UserException;
import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

import static ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames.ALICEUSDT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntentionTest {

    @Test
    void caseIntentionSell() throws UserException {
        User user1 = User.builder().withName("Pepe").build();
        Intention i1 = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(user1).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        assertEquals(user1.getName(),i1.getUser().getName());
    }
}
