package ar.edu.unq.grupoh.criptop2p;


import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames.ALICEUSDT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntentionTest {

    @Test
    void caseIntentionSell(){
        User user1 = User.build(0,"pe","argento","asdsadsa@gmail","asdsadsad","aAsadsadsad.","1234567891234567891233","12345678",0,0);
        Intention i1 = new Intention(ALICEUSDT,0.1,1.5,100.0,user1, TypeOperation.SELL);
        assertEquals(user1.getName(),i1.getUser().getName());
    }
}
