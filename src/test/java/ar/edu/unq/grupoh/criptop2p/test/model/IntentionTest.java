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
        Intention i1 = new Intention(LocalDateTime.now(),ALICEUSDT,0.1, 1.5F,100.0,user1, TypeOperation.SELL);
        assertEquals(user1.getName(),i1.getUser().getName());
    }
}
