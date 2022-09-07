package ar.edu.unq.grupoh.criptop2p.model;

import ar.edu.unq.grupoh.criptop2p.model.stateTransaction.Action;

public class ManagerTransaction {

    private OperationConcrete operationConcrete;
    private Action stateAction;

    public void finishTransaction(){
        this.stateAction.finalizedTransaction(operationConcrete);
    }
}
