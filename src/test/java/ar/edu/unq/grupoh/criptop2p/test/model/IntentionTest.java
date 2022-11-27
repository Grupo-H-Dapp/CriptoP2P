package ar.edu.unq.grupoh.criptop2p.test.model;


import ar.edu.unq.grupoh.criptop2p.exceptions.UserException;
import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.IntentionStatus;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

import static ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames.ALICEUSDT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntentionTest {

    @Test
    void createIntentionSell() throws UserException {
        User user = User.builder().withName("Prueba").build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(user).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        LocalDateTime now = LocalDateTime.now();
        assertEquals(user.getName(),intention.getUser().getName());
        assertEquals(ALICEUSDT,intention.getCrypto());
        assertEquals(TypeOperation.SELL,intention.getTypeOperation());
        assertEquals(0.1,intention.getQuantity());
        assertEquals(1.5f,intention.getPrice());
        assertEquals(now.getDayOfMonth(),intention.getDateCreated().getDayOfMonth());
        assertEquals(now.getDayOfWeek(),intention.getDateCreated().getDayOfWeek());
        assertEquals(now.getDayOfYear(),intention.getDateCreated().getDayOfYear());
        assertEquals(IntentionStatus.ACTIVE,intention.getStatus());
    }

    @Test
    void createIntentionBuy() throws UserException {
        User user = User.builder().withName("Prueba").build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(user).withTypeOperation(TypeOperation.BUY).withQuantity(0.1).withPrice(1.5F).build();
        LocalDateTime now = LocalDateTime.now();
        assertEquals(user.getName(),intention.getUser().getName());
        assertEquals(ALICEUSDT,intention.getCrypto());
        assertEquals(TypeOperation.BUY,intention.getTypeOperation());
        assertEquals(0.1,intention.getQuantity());
        assertEquals(1.5f,intention.getPrice());
        assertEquals(now.getDayOfMonth(),intention.getDateCreated().getDayOfMonth());
        assertEquals(now.getDayOfWeek(),intention.getDateCreated().getDayOfWeek());
        assertEquals(now.getDayOfYear(),intention.getDateCreated().getDayOfYear());
        assertEquals(IntentionStatus.ACTIVE,intention.getStatus());
    }

    @Test
    void createIntentionEnded()  throws UserException {
        User user = User.builder().withName("Prueba").build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(user).withTypeOperation(TypeOperation.BUY).withQuantity(0.1).withPrice(1.5F).build();
        intention.completeIntention();
        assertEquals(IntentionStatus.ENDED,intention.getStatus());
    }
}
