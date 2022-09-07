package ar.edu.unq.grupoh.criptop2p.model.stateTransaction;

import ar.edu.unq.grupoh.criptop2p.model.OperationConcrete;

public abstract class Action {

    public abstract void finalizedTransaction(OperationConcrete operationConcrete);
}
