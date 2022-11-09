package ar.edu.unq.grupoh.criptop2p;

import ar.edu.unq.grupoh.criptop2p.exceptions.UserException;
import ar.edu.unq.grupoh.criptop2p.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OperationTest {
    public User anUser() throws UserException {
        return User
                .builder()
                .withName("Pepe")
                .withLastname("argento")
                .withAddress("1234567891")
                .withEmail("asdsadsa@gmail")
                .withPassword("aAsadsadsad.")
                .withCvu("1234567891234567891233")
                .withWallet("12345678")
                .build();
    }
//    @Test
//    void caseWhereIntentionIsSell() throws UserException {
//        User pepe = User.builder().withCvu("1234567891234567891233").build();
//        User dardo = User.builder().withCvu("1234567891234567891255").build();
//        Intention i1 = new Intention(ALICEUSDT,0.1,1.5,100.0,pepe, TypeOperation.SELL);
//        Operation op1 = new Operation(i1,dardo);
//        assertEquals(pepe.getCvu(),op1.getDireccionEnvio());
//    }
//
//    @Test
//    void caseWhereIntentionIsSellAndFails() throws UserException {
//        User pepe = User.builder().withWallet("12345678").build();
//        User dardo = User.builder().withWallet("87654321").build();
//        Intention i1 = new Intention(ALICEUSDT,0.1,1.5,100.0,pepe, TypeOperation.SELL);
//        Operation op1 = new Operation(i1,dardo);
//        assertNotEquals(pepe.getAddressWallet(),op1.getDireccionEnvio());
//    }
//
//    @Test
//    void caseWhereIntentionIsBuy() throws UserException {
//        User pepe = User.builder().withWallet("12345678").build();
//        User dardo = User.builder().withWallet("87654321").build();
//        Intention i1 = new Intention(ALICEUSDT,0.1,1.5,100.0,pepe, TypeOperation.BUY);
//        Operation op1 = new Operation(i1,dardo);
//        assertEquals(pepe.getAddressWallet(),op1.getDireccionEnvio());
//    }


}
