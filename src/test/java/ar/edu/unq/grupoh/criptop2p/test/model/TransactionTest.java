package ar.edu.unq.grupoh.criptop2p.test.model;

import ar.edu.unq.grupoh.criptop2p.exceptions.UserException;
import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.StatesTransaction;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames.ALICEUSDT;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {
    @Test
    public void createNewTransactionAboutIntentionSell() throws UserException {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = new Transaction(intention,userBuyer,null);
        LocalDateTime now = LocalDateTime.now();

        assertEquals(StatesTransaction.WAITING_TRANSFER_MONEY, transaction.getStateTransaction());
        assertEquals(TypeOperation.SELL, transaction.getIntention().getTypeOperation());
        assertEquals(userBuyer.getName(),transaction.getSecondUser().getName());
        assertEquals(userOwnerIntention.getName(),transaction.getIntention().getUser().getName());
        assertEquals(now.getDayOfMonth(), transaction.getDateCreated().getDayOfMonth());
        assertEquals(now.getDayOfWeek(), transaction.getDateCreated().getDayOfWeek());
        assertEquals(now.getDayOfYear(), transaction.getDateCreated().getDayOfYear());
        assertEquals(intention.getPrice(), transaction.getPrice());
        assertEquals(intention.getCrypto(), transaction.getCrypto());
        assertEquals(userOwnerIntention.getCvu(), transaction.getAddressSendMoney());
        assertEquals(userBuyer.getAddressWallet(), transaction.getAddressSendCrypto());
        assertEquals(intention.getQuantity(), transaction.getQuantity());
    }

    @Test
    public void changeStateTransactionToWaitingConfirmTransferMoney() throws UserException {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = new Transaction(intention,userBuyer,null);
    }
}
