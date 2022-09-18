package ar.edu.unq.grupoh.criptop2p.service;

import ar.edu.unq.grupoh.criptop2p.model.Intention;
import ar.edu.unq.grupoh.criptop2p.model.User;
import ar.edu.unq.grupoh.criptop2p.model.enums.CriptosNames;
import ar.edu.unq.grupoh.criptop2p.model.enums.TypeOperation;
import org.springframework.stereotype.Service;

@Service
public class IntentionService {

    public Intention crearIntention(CriptosNames crypto, Double quantity, Double price, Double amountArg, User user, TypeOperation typeOperation){
        Intention i1 = new Intention(crypto,quantity,price,amountArg,user,typeOperation);
        return i1;
    }

    public void eliminarIntention(){
    }


}
