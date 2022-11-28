package ar.edu.unq.grupoh.criptop2p.test.model;

import ar.edu.unq.grupoh.criptop2p.exceptions.*;
import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.model.Transaction;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.Action;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.StatesTransaction;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import ar.edu.unq.grupoh.criptop2p.model.ApiBinance;
import ar.edu.unq.grupoh.criptop2p.service.response.BinanceResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames.ALICEUSDT;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    //Intention Sell
    @Test
    public void createNewTransactionAboutIntentionSell() throws UserException {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userBuyer).build();
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
    public void changeStateTransactionWaitingTransferMoneyOfIntentionSellWithActionTransferMoney() throws ExceedPriceDifference, UserException, TransactionStatusException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userBuyer).build();

        transaction.change(userBuyer, Action.TRANSFER_MONEY);

        assertEquals(StatesTransaction.WAITING_CONFIRM_TRANSFER_MONEY, transaction.getStateTransaction());
        assertEquals(TypeOperation.SELL, transaction.getIntention().getTypeOperation());

    }

    @Test
    public void  changeStateTransactionWaitingTransferMoneyOfIntentionSellWithActionTransferMoneyBySameUserOwnerIntention() throws UserException {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userBuyer).build();

        IlegalUserChangeStateTransaction exception = assertThrows(
                IlegalUserChangeStateTransaction.class,
                () -> transaction.change(userOwnerIntention, Action.TRANSFER_MONEY)
        );

        assertEquals("Ilegal user change state", exception.getMessage());
    }

    @Test
    public void  changeStateTransactionWaitingTransferMoneyOfIntentionSellWithIlegalAction() throws UserException {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userBuyer).build();

        IlegalActionOnStateTransaction exception = assertThrows(
                IlegalActionOnStateTransaction.class,
                () -> transaction.change(userOwnerIntention, Action.CONFIRM_CRYPTO)
        );

        assertEquals("Ilegal action on state transaction", exception.getMessage());
    }

    @Test
    public void changeStateTransactionWaitingConfirmTransferMoneyOfIntentionSellWithActionConfirmMoney() throws ExceedPriceDifference, UserException, TransactionStatusException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userBuyer).build();

        transaction.change(userBuyer, Action.TRANSFER_MONEY);
        transaction.change(userOwnerIntention, Action.CONFIRM_MONEY);

        assertEquals(StatesTransaction.WAITING_TRANSFER_CRYPTO, transaction.getStateTransaction());
        assertEquals(TypeOperation.SELL, transaction.getIntention().getTypeOperation());
    }

    @Test
    public void changeStateTransactionWaitingConfirmTransferMoneyOfIntentionSellWithActionConfirmMoneyByUserBuyer() throws UserException, IlegalUserChangeStateTransaction, ExceedPriceDifference, TransactionStatusException, IlegalActionOnStateTransaction {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userBuyer).build();
        transaction.change(userBuyer, Action.TRANSFER_MONEY);
        IlegalUserChangeStateTransaction exception = assertThrows(
                IlegalUserChangeStateTransaction.class,
                () -> {
                    transaction.change(userBuyer, Action.CONFIRM_MONEY);
                }
        );

        assertEquals("Ilegal user change state", exception.getMessage());
    }

    @Test
    public void changeStateTransactionWaitingConfirmTransferMoneyOfIntentionSellWithIlegalAction() throws UserException, IlegalUserChangeStateTransaction, ExceedPriceDifference, TransactionStatusException, IlegalActionOnStateTransaction {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userBuyer).build();
        transaction.change(userBuyer, Action.TRANSFER_MONEY);
        IlegalActionOnStateTransaction exception = assertThrows(
                IlegalActionOnStateTransaction.class,
                () -> {
                    transaction.change(userOwnerIntention, Action.TRANSFER_CRYPTO);
                }
        );

        assertEquals("Ilegal action on state transaction", exception.getMessage());
    }

    @Test
    public void changeStateTransactionWaitingTransferCryptoOfIntentionSellWithActionTransferCrypto() throws ExceedPriceDifference, UserException, TransactionStatusException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userBuyer).build();

        transaction.change(userBuyer, Action.TRANSFER_MONEY);
        transaction.change(userOwnerIntention, Action.CONFIRM_MONEY);
        transaction.change(userOwnerIntention, Action.TRANSFER_CRYPTO);

        assertEquals(StatesTransaction.WAITING_CONFIRM_TRANSFER_CRYPTO, transaction.getStateTransaction());
        assertEquals(TypeOperation.SELL, transaction.getIntention().getTypeOperation());
    }

    @Test
    public void changeStateTransactionWaitingTransferCryptoOfIntentionSellWithActionTransferCryptoByUserBuyer() throws UserException, IlegalUserChangeStateTransaction, ExceedPriceDifference, TransactionStatusException, IlegalActionOnStateTransaction {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userBuyer).build();
        transaction.change(userBuyer, Action.TRANSFER_MONEY);
        transaction.change(userOwnerIntention, Action.CONFIRM_MONEY);
        IlegalUserChangeStateTransaction exception = assertThrows(
                IlegalUserChangeStateTransaction.class,
                () -> {

                    transaction.change(userBuyer, Action.TRANSFER_CRYPTO);
                }
        );

        assertEquals("Ilegal user change state", exception.getMessage());
    }

    @Test
    public void changeStateTransactionWaitingTransferCryptoOfIntentionSellWithIlegalAction() throws UserException, IlegalUserChangeStateTransaction, ExceedPriceDifference, TransactionStatusException, IlegalActionOnStateTransaction {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userBuyer).build();
        transaction.change(userBuyer, Action.TRANSFER_MONEY);
        transaction.change(userOwnerIntention, Action.CONFIRM_MONEY);
        IlegalActionOnStateTransaction exception = assertThrows(
                IlegalActionOnStateTransaction.class,
                () -> {
                    transaction.change(userOwnerIntention, Action.TRANSFER_MONEY);
                }
        );

        assertEquals("Ilegal action on state transaction", exception.getMessage());
    }

    @Test
    public void changeStateTransactionWaitingConfirmTransferCryptoOfIntentionSellWithActionTransferCrypto() throws ExceedPriceDifference, UserException, TransactionStatusException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userBuyer).build();

        ApiBinance apiBinance = Mockito.mock(ApiBinance.class);
        Mockito.when(apiBinance.getBinanceResponse(CriptosNames.ALICEUSDT)).thenReturn(new BinanceResponse("ALICEUSDT", 1.5f));
        transaction.setApiBinance(apiBinance);

        transaction.change(userBuyer, Action.TRANSFER_MONEY);
        transaction.change(userOwnerIntention, Action.CONFIRM_MONEY);
        transaction.change(userOwnerIntention, Action.TRANSFER_CRYPTO);
        transaction.change(userBuyer, Action.CONFIRM_CRYPTO);

        assertEquals(StatesTransaction.COMPLETED, transaction.getStateTransaction());
        assertEquals(TypeOperation.SELL, transaction.getIntention().getTypeOperation());
        assertEquals(1, userOwnerIntention.getAmountOperations());
        assertEquals(1, userBuyer.getAmountOperations());
        assertEquals(10, userBuyer.getPoints());
        assertEquals(10, userOwnerIntention.getPoints());
    }

    @Test
    public void changeStateTransactionWaitingConfirmTransferCryptoOfIntentionSellWithActionTransferCryptoByUserOwnerIntention() throws UserException, IlegalUserChangeStateTransaction, ExceedPriceDifference, TransactionStatusException, IlegalActionOnStateTransaction {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userBuyer).build();
        transaction.change(userBuyer, Action.TRANSFER_MONEY);
        transaction.change(userOwnerIntention, Action.CONFIRM_MONEY);
        transaction.change(userOwnerIntention, Action.TRANSFER_CRYPTO);
        IlegalUserChangeStateTransaction exception = assertThrows(
                IlegalUserChangeStateTransaction.class,
                () -> {
                    transaction.change(userOwnerIntention, Action.CONFIRM_CRYPTO);
                }
        );

        assertEquals("Ilegal user change state", exception.getMessage());
    }

    @Test
    public void changeStateTransactionWaitingConfirmTransferCryptoOfIntentionSellWithIlegalAction() throws UserException, IlegalUserChangeStateTransaction, ExceedPriceDifference, TransactionStatusException, IlegalActionOnStateTransaction {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userBuyer).build();
        transaction.change(userBuyer, Action.TRANSFER_MONEY);
        transaction.change(userOwnerIntention, Action.CONFIRM_MONEY);
        transaction.change(userOwnerIntention, Action.TRANSFER_CRYPTO);
        IlegalActionOnStateTransaction exception = assertThrows(
                IlegalActionOnStateTransaction.class,
                () -> {
                    transaction.change(userOwnerIntention, Action.TRANSFER_MONEY);
                }
        );

        assertEquals("Ilegal action on state transaction", exception.getMessage());
    }

    @Test
    public void changeStateTransactionWaitingTransferMoneyOfIntentionSellWithActionCancel() throws ExceedPriceDifference, UserException, TransactionStatusException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userBuyer).build();

        transaction.change(userOwnerIntention, Action.CANCEL);

        assertEquals(StatesTransaction.CANCELED, transaction.getStateTransaction());
        assertEquals(TypeOperation.SELL, transaction.getIntention().getTypeOperation());
        assertEquals(-20, userOwnerIntention.getPoints());
    }

    @Test
    public void changeStateTransactionWaitingConfirmTransferMoneyOfIntentionSellWithActionCancel() throws ExceedPriceDifference, UserException, TransactionStatusException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userBuyer).build();

        transaction.change(userBuyer, Action.TRANSFER_MONEY);
        transaction.change(userOwnerIntention, Action.CANCEL);

        assertEquals(StatesTransaction.CANCELED, transaction.getStateTransaction());
        assertEquals(TypeOperation.SELL, transaction.getIntention().getTypeOperation());
        assertEquals(-20, userOwnerIntention.getPoints());
    }

    @Test
    public void changeStateTransactionWaitingTransferCryptoOfIntentionSellWithActionCancel() throws ExceedPriceDifference, UserException, TransactionStatusException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userBuyer).build();

        transaction.change(userBuyer, Action.TRANSFER_MONEY);
        transaction.change(userOwnerIntention, Action.CONFIRM_MONEY);
        transaction.change(userOwnerIntention, Action.CANCEL);

        assertEquals(StatesTransaction.CANCELED, transaction.getStateTransaction());
        assertEquals(TypeOperation.SELL, transaction.getIntention().getTypeOperation());
        assertEquals(-20, userOwnerIntention.getPoints());
    }

    @Test
    public void changeStateTransactionWaitingConfirmTransferCryptoOfIntentionSellWithActionCancel() throws ExceedPriceDifference, UserException, TransactionStatusException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userBuyer).build();

        transaction.change(userBuyer, Action.TRANSFER_MONEY);
        transaction.change(userOwnerIntention, Action.CONFIRM_MONEY);
        transaction.change(userOwnerIntention, Action.TRANSFER_CRYPTO);
        transaction.change(userOwnerIntention, Action.CANCEL);

        assertEquals(StatesTransaction.CANCELED, transaction.getStateTransaction());
        assertEquals(TypeOperation.SELL, transaction.getIntention().getTypeOperation());
        assertEquals(-20, userOwnerIntention.getPoints());
    }

    @Test
    public void changeStateTransactionCompletedOfIntentionSellWithActionCancel() throws UserException, IlegalUserChangeStateTransaction, ExceedPriceDifference, TransactionStatusException, IlegalActionOnStateTransaction {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userBuyer).build();

        ApiBinance apiBinance = Mockito.mock(ApiBinance.class);
        Mockito.when(apiBinance.getBinanceResponse(CriptosNames.ALICEUSDT)).thenReturn(new BinanceResponse("ALICEUSDT", 1.5f));
        transaction.setApiBinance(apiBinance);
        transaction.change(userBuyer, Action.TRANSFER_MONEY);
        transaction.change(userOwnerIntention, Action.CONFIRM_MONEY);
        transaction.change(userOwnerIntention, Action.TRANSFER_CRYPTO);
        transaction.change(userBuyer, Action.CONFIRM_CRYPTO);
        IlegalActionOnStateTransaction exception = assertThrows(
                IlegalActionOnStateTransaction.class,
                () -> {
                    transaction.change(userOwnerIntention, Action.CANCEL);

                }
        );

        assertEquals("Ilegal action on state transaction", exception.getMessage());
    }

    @Test
    public void changeStateTransactionCanceledOfIntentionSellWithActionCancel() throws UserException, IlegalUserChangeStateTransaction, ExceedPriceDifference, TransactionStatusException, IlegalActionOnStateTransaction {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userBuyer = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.SELL).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userBuyer).build();
        transaction.change(userBuyer, Action.TRANSFER_MONEY);
        transaction.change(userOwnerIntention, Action.CANCEL);
        IlegalActionOnStateTransaction exception = assertThrows(
                IlegalActionOnStateTransaction.class,
                () -> {
                    transaction.change(userOwnerIntention, Action.CANCEL);
                }
        );

        assertEquals("Ilegal action on state transaction", exception.getMessage());
    }

    //Intention Buy

    @Test
    public void changeStateTransactionWaitingTransferMoneyOfIntentionBuyWithActionTransferMoneyByUserOwner() throws UserException, TransactionStatusException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction, ExceedPriceDifference {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userSeller = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.BUY).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userSeller).build();

        transaction.change(userOwnerIntention, Action.TRANSFER_MONEY);

        assertEquals(StatesTransaction.WAITING_CONFIRM_TRANSFER_MONEY, transaction.getStateTransaction());
        assertEquals(TypeOperation.BUY, transaction.getIntention().getTypeOperation());
    }

    @Test
    public void changeStateTransactionWaitingTransferMoneyOfIntentionBuyWithActionTransferMoneyByUserSeller() throws UserException {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userSeller = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.BUY).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userSeller).build();

        IlegalUserChangeStateTransaction exception = assertThrows(
                IlegalUserChangeStateTransaction.class,
                () -> transaction.change(userSeller, Action.TRANSFER_MONEY)
        );

        assertEquals("Ilegal user change state", exception.getMessage());
    }

    @Test
    public void changeStateTransactionWaitingConfirmTransferMoneyOfIntentionBuyWithActionConfirmMoney() throws UserException, TransactionStatusException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction, ExceedPriceDifference {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userSeller = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.BUY).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userSeller).build();

        transaction.change(userOwnerIntention, Action.TRANSFER_MONEY);
        transaction.change(userSeller, Action.CONFIRM_MONEY);

        assertEquals(StatesTransaction.WAITING_TRANSFER_CRYPTO, transaction.getStateTransaction());
        assertEquals(TypeOperation.BUY, transaction.getIntention().getTypeOperation());
    }

    @Test
    public void changeStateTransactionWaitingConfirmTransferMoneyOfIntentionBuyWithActionConfirmMoneyByUserOwner() throws UserException, IlegalUserChangeStateTransaction, ExceedPriceDifference, TransactionStatusException, IlegalActionOnStateTransaction {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userSeller = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.BUY).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userSeller).build();
        transaction.change(userOwnerIntention, Action.TRANSFER_MONEY);
        IlegalUserChangeStateTransaction exception = assertThrows(
                IlegalUserChangeStateTransaction.class,
                () -> {
                    transaction.change(userOwnerIntention, Action.CONFIRM_MONEY);
                }
        );

        assertEquals("Ilegal user change state", exception.getMessage());
    }

    @Test
    public void changeStateTransactionWaitingTransferCryptoOfIntentionBuyWithActionTransferCrypto() throws UserException, TransactionStatusException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction, ExceedPriceDifference {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userSeller = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.BUY).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userSeller).build();

        transaction.change(userOwnerIntention, Action.TRANSFER_MONEY);
        transaction.change(userSeller, Action.CONFIRM_MONEY);
        transaction.change(userSeller, Action.TRANSFER_CRYPTO);

        assertEquals(StatesTransaction.WAITING_CONFIRM_TRANSFER_CRYPTO, transaction.getStateTransaction());
        assertEquals(TypeOperation.BUY, transaction.getIntention().getTypeOperation());
    }

    @Test
    public void changeStateTransactionWaitingTransferCryptoOfIntentionBuyWithActionTransferCryptoByUserOwner() throws UserException, IlegalUserChangeStateTransaction, ExceedPriceDifference, TransactionStatusException, IlegalActionOnStateTransaction {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userSeller = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.BUY).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userSeller).build();
        transaction.change(userOwnerIntention, Action.TRANSFER_MONEY);
        transaction.change(userSeller, Action.CONFIRM_MONEY);
        IlegalUserChangeStateTransaction exception = assertThrows(
                IlegalUserChangeStateTransaction.class,
                () -> {
                    transaction.change(userOwnerIntention, Action.TRANSFER_CRYPTO);

                }
        );

        assertEquals("Ilegal user change state", exception.getMessage());
    }

    @Test
    public void changeStateTransactionWaitingConfirmTransferCryptoOfIntentionBuyWithActionConfirmCrypto() throws UserException, TransactionStatusException, IlegalUserChangeStateTransaction, IlegalActionOnStateTransaction, ExceedPriceDifference {
        User userOwnerIntention = User.builder().withName("Owner").withCvu("1234567891234567891233").withId(1).build();
        User userSeller = User.builder().withName("Buyer").withWallet("12345678").withId(2).build();
        Intention intention = Intention.builder().withCryptoCurrency(ALICEUSDT).withUser(userOwnerIntention).withTypeOperation(TypeOperation.BUY).withQuantity(0.1).withPrice(1.5F).build();
        Transaction transaction = Transaction.builder().withIntention(intention).withSecondUser(userSeller).build();

        ApiBinance apiBinance = Mockito.mock(ApiBinance.class);
        Mockito.when(apiBinance.getBinanceResponse(CriptosNames.ALICEUSDT)).thenReturn(new BinanceResponse("ALICEUSDT", 1.5f));
        transaction.setApiBinance(apiBinance);

        transaction.change(userOwnerIntention, Action.TRANSFER_MONEY);
        transaction.change(userSeller, Action.CONFIRM_MONEY);
        transaction.change(userSeller, Action.TRANSFER_CRYPTO);
        transaction.change(userOwnerIntention, Action.CONFIRM_CRYPTO);

        assertEquals(StatesTransaction.COMPLETED, transaction.getStateTransaction());
        assertEquals(TypeOperation.BUY, transaction.getIntention().getTypeOperation());
        assertEquals(1, userOwnerIntention.getAmountOperations());
        assertEquals(1, userSeller.getAmountOperations());
        assertEquals(10, userSeller.getPoints());
        assertEquals(10, userOwnerIntention.getPoints());
    }

}
