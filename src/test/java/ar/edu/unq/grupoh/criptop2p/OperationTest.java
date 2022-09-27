package ar.edu.unq.grupoh.criptop2p;

import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.model.Operation;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames.ALICEUSDT;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OperationTest {
//caseActionTransferOperation
    @Test
    void caseWhereIntentionIsSell(){
        User pepe = User.build(0,"Pepe","argento","asdsadsa@gmail","asdsadsad","aAsadsadsad.","1234567891234567891233","12345678",0,0);
        User dardo = User.build(0,"Dardo","Fuseneco","asdsadsa@gmail","asdsadsad","aAsadsadsad.","1234567891234567891255","87654321",10,10);
        Intention i1 = new Intention(ALICEUSDT,0.1,1.5,100.0,pepe, TypeOperation.SELL);
        Operation op1 = new Operation(i1,dardo);
        assertEquals(pepe.getCvu(),op1.getDireccionEnvio());
    }

    @Test
    void caseWhereIntentionIsSellAndFails(){
        User pepe = User.build(0,"Pepe","argento","asdsadsa@gmail","asdsadsad","aAsadsadsad.","1234567891234567891233","12345678",0,0);
        User dardo = User.build(0,"Dardo","Fuseneco","asdsadsa@gmail","asdsadsad","aAsadsadsad.","1234567891234567891255","87654321",10,10);
        Intention i1 = new Intention(ALICEUSDT,0.1,1.5,100.0,pepe, TypeOperation.SELL);
        Operation op1 = new Operation(i1,dardo);
        assertNotEquals(pepe.getAddressWallet(),op1.getDireccionEnvio());
    }

    @Test
    void caseWhereIntentionIsBuy(){
        User pepe = User.build(0,"Pepe","argento","asdsadsa@gmail","asdsadsad","aAsadsadsad.","1234567891234567891233","12345678",0,0);
        User dardo = User.build(0,"Dardo","Fuseneco","asdsadsa@gmail","asdsadsad","aAsadsadsad.","1234567891234567891255","87654321",10,10);
        Intention i1 = new Intention(ALICEUSDT,0.1,1.5,100.0,pepe, TypeOperation.BUY);
        Operation op1 = new Operation(i1,dardo);
        assertEquals(pepe.getAddressWallet(),op1.getDireccionEnvio());
    }


}
