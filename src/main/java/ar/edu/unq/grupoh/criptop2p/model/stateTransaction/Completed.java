package ar.edu.unq.grupoh.criptop2p.model.stateTransaction;

import ar.edu.unq.grupoh.criptop2p.model.OperationConcrete;
import ar.edu.unq.grupoh.criptop2p.model.User;

import java.time.Duration;
import java.time.LocalDateTime;

public class Completed extends Action {

    @Override
    public void finalizedTransaction(OperationConcrete operationConcrete) {
        User userIntention = operationConcrete.getIntention().getUser();
        User secondUser = operationConcrete.getSecondUser();
        LocalDateTime now = LocalDateTime.now();
        long timePassed = Duration.between(operationConcrete.getDateTime(), now).toMinutes();
        int points = timePassed <= 30 ? 10 : 5;
        userIntention.addPoint(points);
        userIntention.addOperation();
        secondUser.addPoint(points);
        secondUser.addOperation();
    }
}
