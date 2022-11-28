package ar.edu.unq.grupoh.criptop2p.dto.response;

import ar.edu.unq.grupoh.criptop2p.model.User;
import lombok.Getter;
import lombok.Setter;

public class UserAllResponse {
    @Getter@Setter
    private int id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String lastname;
    @Getter @Setter
    private int amountOperations; // La cantidad de concretaciones de intenciones
    @Getter @Setter
    private int points;
    private User wrapped;

    public UserAllResponse(User wrapped) {
        this.wrapped = wrapped;
        this.id = wrapped.getUserId();
        this.amountOperations = wrapped.getAmountOperations();
        this.name = wrapped.getName();
        this.lastname = wrapped.getLastname();
        this.amountOperations = wrapped.getAmountOperations();
        this.points = wrapped.getPoints();
    }

    public UserAllResponse() {
    }
}
